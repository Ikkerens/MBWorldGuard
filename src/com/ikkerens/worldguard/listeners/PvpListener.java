package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.FlagOption;
import com.ikkerens.worldguard.model.MatchedRegion;
import com.mbserver.api.events.EventHandler;
import com.mbserver.api.events.PlayerPvpEvent;

public class PvpListener extends AbstractListener< WorldGuardPlugin > {

    public PvpListener( WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onPvp( PlayerPvpEvent event ) {
        MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getLocation() );
        String flag = rg.getFlag( Flag.PVP );

        if ( FlagOption.DENY.equals( flag ) )
            event.setCancelled( true );
    }

}
