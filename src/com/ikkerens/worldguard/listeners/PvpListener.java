package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flags;
import com.ikkerens.worldguard.model.MatchedRegion;
import com.ikkerens.worldguard.model.flagtypes.StateFlag.State;

import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.PlayerPvpEvent;

public class PvpListener extends AbstractListener< WorldGuardPlugin > {

    public PvpListener( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onPvp( final PlayerPvpEvent event ) {
        final MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getLocation() );
        final State pvp = rg.getFlagValue( Flags.PVP );

        if ( pvp == State.DENY ) {
            event.getAttacker().sendMessage( "This is a no-pvp zone." );
            event.setCancelled( true );
        }
    }

}
