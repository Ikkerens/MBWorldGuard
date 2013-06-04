package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.FlagOption;
import com.ikkerens.worldguard.model.MatchedRegion;

import com.mbserver.api.events.BlockEvent;
import com.mbserver.api.events.EventHandler;

public class BlockListener extends AbstractListener< WorldGuardPlugin > {

    public BlockListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onBlockEvent( final BlockEvent event ) {
        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getLocation() );
        final String flag = rg.getFlag( Flag.BUILD );

        if ( FlagOption.DENY.equals( flag ) ) {
            event.setCancelled( true );
            return;
        }

        if ( FlagOption.OWNERS.equals( flag ) && !rg.isOwner( event.getPlayer().getName() ) ) {
            event.setCancelled( true );
            return;
        }

        if ( FlagOption.MEMBERS.equals( flag ) && !rg.isMember( event.getPlayer().getName() ) ) {
            event.setCancelled( true );
            return;
        }
    }

}
