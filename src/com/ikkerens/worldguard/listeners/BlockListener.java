package com.ikkerens.worldguard.listeners;

import com.ikkerens.worldedit.handlers.AbstractListener;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.FlagOption;
import com.ikkerens.worldguard.model.MatchedRegion;
import com.mbserver.api.events.BlockEvent;
import com.mbserver.api.events.EventHandler;

public class BlockListener extends AbstractListener< WorldGuardPlugin > {

    public BlockListener( WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @EventHandler
    public void onBlockEvent( BlockEvent event ) {
        MatchedRegion rg = new MatchedRegion( this.getPlugin().getStorage(), event.getLocation() );
        String flag = rg.getFlag( Flag.BUILD );

        if ( FlagOption.DENY.equals( flag ) )
            event.setCancelled( true );
    }

}
