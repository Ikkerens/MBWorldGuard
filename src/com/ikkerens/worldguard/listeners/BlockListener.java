package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flags;
import com.ikkerens.worldguard.model.MatchedRegion;
import com.ikkerens.worldguard.model.flagtypes.GroupStateFlag.GroupState;

import com.mbserver.api.events.BlockEvent;
import com.mbserver.api.events.EventHandler;

public class BlockListener extends AbstractListener< WorldGuardPlugin > {

    public BlockListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onBlockEvent( final BlockEvent event ) {
        if ( event.getPlayer().hasPermission( "ikkerens.worldguard.admin" ) )
            return;

        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getLocation() );
        final GroupState build = rg.getFlagValue( Flags.BUILD );

        boolean deny = false;

        if ( build == GroupState.DENY )
            deny = true;

        else if ( ( build == GroupState.MEMBERS ) && !rg.isMember( event.getPlayer().getName() ) )
            deny = !rg.isMember( event.getPlayer().getName() );

        else if ( ( build == GroupState.OWNERS ) && !rg.isOwner( event.getPlayer().getName() ) )
            deny = !rg.isOwner( event.getPlayer().getName() );

        if ( deny )
            event.setCancelled( true );
    }

}
