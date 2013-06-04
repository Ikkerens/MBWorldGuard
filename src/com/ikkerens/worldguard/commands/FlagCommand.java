package com.ikkerens.worldguard.commands;

import com.ikkerens.worldedit.handlers.AbstractCommand;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.Region;

import com.mbserver.api.game.Player;

public class FlagCommand extends AbstractCommand< WorldGuardPlugin > {

    public FlagCommand( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @Override
    protected void execute( final String label, final Player player, final String[] args ) {
        if ( ( args.length != 2 ) && ( args.length != 3 ) ) {
            player.sendMessage( "Usage: /" + label + " <region> <flag> [value]" );
            return;
        }

        final Region region = this.getPlugin().getStorage().getRegion( args[ 0 ] );
        if ( region == null ) {
            player.sendMessage( String.format( "Region %s does not exist.", args[ 0 ] ) );
            return;
        }

        if ( !region.isOwner( player.getName() ) ) {
            player.sendMessage( "You are not a owner of that region." );
            return;
        }

        Flag flag;
        try {
            flag = Flag.valueOf( args[ 1 ].toUpperCase() );
        } catch ( final IllegalArgumentException e ) {
            player.sendMessage( String.format( "Flag %s does not exist.", args[ 1 ] ) );
            return;
        }

        if ( args.length == 2 ) {
            // Set to default
            region.clearFlag( flag );
            player.sendMessage( String.format( "Cleared flag %s of %s.", flag.name().toLowerCase(), region.getName() ) );
        } else {
            if ( !flag.validate( args[ 2 ] ) ) {
                player.sendMessage( String.format( "%s is not a valid value for flag %s.", args[ 2 ], flag.name().toLowerCase() ) );
                return;
            }

            region.setFlag( flag, args[ 2 ] );
            player.sendMessage( String.format( "Set flag %s of %s to %s.", flag.name().toLowerCase(), region.getName(), args[ 2 ] ) );
        }
    }

}
