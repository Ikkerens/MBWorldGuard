package com.ikkerens.worldguard.commands;

import com.ikkerens.worldedit.handlers.AbstractCommand;
import com.ikkerens.worldedit.model.Selection;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Region;
import com.mbserver.api.game.Player;

public class DefineCommand extends AbstractCommand< WorldGuardPlugin > {

    public DefineCommand( WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @Override
    protected void execute( String label, Player player, String[] args ) {
        if ( args.length != 1 ) {
            player.sendMessage( "Usage: /" + label + " <name>" );
        }

        Selection sel = this.getSession( player ).getSelection();
        if ( sel.isValid() ) {
            Region region = new Region( args[ 0 ], sel.getMinimumPosition(), sel.getMaximumPosition() );
            this.getPlugin().getStorage().saveRegion( region );
        } else
            player.sendMessage( NEED_SELECTION );
    }

}
