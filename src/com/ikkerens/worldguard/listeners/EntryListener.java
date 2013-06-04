package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.FlagOption;
import com.ikkerens.worldguard.model.MatchedRegion;

import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.PlayerMoveEvent;

public class EntryListener extends AbstractListener< WorldGuardPlugin > {
    private static final String NO_ENTRY = "You are not allowed to go here.";

    public EntryListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onMove( final PlayerMoveEvent event ) {
        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getTargetLocation() );
        final String flag = rg.getFlag( Flag.ENTRY );

        if ( FlagOption.DENY.equals( flag ) ) {
            event.getPlayer().sendMessage( NO_ENTRY );
            event.setCancelled( true );
            return;
        }

        if ( FlagOption.OWNERS.equals( flag ) && !rg.isOwner( event.getPlayer().getName() ) ) {
            event.getPlayer().sendMessage( NO_ENTRY );
            event.setCancelled( true );
            return;
        }

        if ( FlagOption.MEMBERS.equals( flag ) && !rg.isMember( event.getPlayer().getName() ) ) {
            event.getPlayer().sendMessage( NO_ENTRY );
            event.setCancelled( true );
            return;
        }
    }

}
