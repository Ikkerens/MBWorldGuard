package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flags;
import com.ikkerens.worldguard.model.MatchedRegion;
import com.ikkerens.worldguard.model.flagtypes.GroupStateFlag.GroupState;

import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.PlayerMoveEvent;
import com.mbserver.api.game.Player;

public class MoveListener extends AbstractListener< WorldGuardPlugin > {
    private static final String ENTRY_KEY = "region_capture";

    private static final String NO_ENTRY  = "You are not allowed to go here.";
    private static final String NO_LEAVE  = "You are not allowed to leave this place.";

    public MoveListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onMove( final PlayerMoveEvent event ) {
        if ( event.getPlayer().hasPermission( "ikkerens.worldguard.admin" ) )
            return;

        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getTargetLocation() );
        final GroupState entry = rg.getFlagValue( Flags.ENTRY );
        final GroupState leave = rg.getFlagValue( Flags.LEAVE );
        final Player player = event.getPlayer();

        if ( entry != GroupState.ALLOW ) {
            boolean deny = false;

            if ( entry == GroupState.DENY )
                deny = true;

            else if ( entry == GroupState.MEMBERS )
                deny = !rg.isMember( player.getName() );

            else if ( entry == GroupState.OWNERS )
                deny = !rg.isOwner( player.getName() );

            if ( deny ) {
                player.sendMessage( NO_ENTRY );
                event.setCancelled( true );
                return;
            }
        }

        if ( leave != GroupState.ALLOW ) {
            boolean capture = false;

            if ( leave == GroupState.DENY )
                capture = true;

            else if ( leave == GroupState.MEMBERS )
                capture = !rg.isMember( player.getName() );

            else if ( leave == GroupState.OWNERS )
                capture = !rg.isOwner( player.getName() );

            if ( capture )
                player.setMetaData( ENTRY_KEY, true );
        }

        if ( player.getMetaData( ENTRY_KEY, false ) && ( leave != GroupState.ALLOW ) ) {
            player.sendMessage( NO_LEAVE );
            event.setCancelled( true );
        }
    }
}
