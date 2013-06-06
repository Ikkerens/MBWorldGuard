package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.FlagOption;
import com.ikkerens.worldguard.model.MatchedRegion;

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
        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getTargetLocation() );
        final String entryFlag = rg.getFlag( Flag.ENTRY );
        final String leaveFlag = rg.getFlag( Flag.LEAVE );
        final Player player = event.getPlayer();

        if ( !FlagOption.ALLOW.equals( entryFlag ) ) {
            boolean deny = false;

            if ( FlagOption.ALLOW.equals( entryFlag ) )
                deny = true;

            else if ( FlagOption.MEMBERS.equals( entryFlag ) )
                deny = !rg.isMember( player.getName() );

            else if ( FlagOption.OWNERS.equals( entryFlag ) )
                deny = !rg.isOwner( player.getName() );

            if ( deny ) {
                player.sendMessage( NO_ENTRY );
                event.setCancelled( true );
                return;
            }
        }

        if ( !FlagOption.ALLOW.equals( leaveFlag ) ) {
            boolean capture = false;

            if ( FlagOption.ALLOW.equals( leaveFlag ) )
                capture = true;

            else if ( FlagOption.MEMBERS.equals( leaveFlag ) )
                capture = !rg.isMember( player.getName() );

            else if ( FlagOption.OWNERS.equals( leaveFlag ) )
                capture = !rg.isOwner( player.getName() );

            if ( capture )
                player.setMetaData( ENTRY_KEY, true );
        }

        if ( player.getMetaData( ENTRY_KEY, false ) && !FlagOption.ALLOW.equals( leaveFlag ) ) {
            player.sendMessage( NO_LEAVE );
            event.setCancelled( true );
        }
    }
}
