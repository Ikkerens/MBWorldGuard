package com.ikkerens.worldguard.commands;

import com.ikkerens.worldedit.handlers.AbstractCommand;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Region;

import com.mbserver.api.game.Player;

public class AddToCommand extends AbstractCommand< WorldGuardPlugin > {

    public AddToCommand( final WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @Override
    protected void execute( final String label, final Player player, final String[] args ) {
        if ( ( args.length != 2 ) && ( args.length != 3 ) ) {
            player.sendMessage( "Usage: /" + label + " <region> <player>" );
            return;
        }

        final Region region = this.getPlugin().getStorage().getRegion( args[ 0 ] );
        if ( region == null ) {
            player.sendMessage( String.format( "Region %s does not exist.", args[ 0 ] ) );
            return;
        }

        if ( label.equalsIgnoreCase( "addowner" ) ) {
            if ( region.isOwner( args[ 1 ] ) ) {
                player.sendMessage( String.format( "%s is already a owner of %s.", args[ 1 ], region.getName() ) );
                return;
            }

            region.addOwner( args[ 1 ] );
            player.sendMessage( String.format( "Added %s as owner to %s.", args[ 1 ], region.getName() ) );
        } else { // Member
            if ( region.isMember( args[ 1 ] ) ) {
                if ( region.isOwner( args[ 1 ] ) )
                    player.sendMessage( String.format( "%s is already a owner of %s.", args[ 1 ], region.getName() ) );
                else
                    player.sendMessage( String.format( "%s is already a member of %s.", args[ 1 ], region.getName() ) );
                return;
            }

            region.addMember( args[ 1 ] );
            player.sendMessage( String.format( "Added %s as member to %s.", args[ 1 ], region.getName() ) );

        }
    }

}
