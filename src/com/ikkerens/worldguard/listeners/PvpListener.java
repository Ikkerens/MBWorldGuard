package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.FlagOption;
import com.ikkerens.worldguard.model.MatchedRegion;

import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.PlayerPvpEvent;

public class PvpListener extends AbstractListener< WorldGuardPlugin > {

    public PvpListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onPvp( final PlayerPvpEvent event ) {
        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getLocation() );
        final String flag = rg.getFlag( Flag.PVP );

        if ( FlagOption.DENY.equals( flag ) ) {
            event.getAttacker().sendMessage( "This is a no-pvp zone." );
            event.setCancelled( true );
        }
    }

}
