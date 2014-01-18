package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.MatchedRegion;

import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.PreCommandEvent;
import com.mbserver.api.game.Player;

public class CommandListener extends AbstractListener< WorldGuardPlugin > {

    public CommandListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onCommand( final PreCommandEvent event ) {
        if ( event.getSender().hasPermission( "ikkerens.worldguard.admin" ) )
            return;

        final Player player = (Player) event.getSender();
        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), player.getLocation() );
    }
}
