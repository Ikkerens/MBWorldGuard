package com.ikkerens.worldguard.commands;

import com.ikkerens.worldedit.handlers.AbstractCommand;
import com.ikkerens.worldguard.WorldGuardPlugin;
import com.ikkerens.worldguard.model.Region;

import com.mbserver.api.game.Player;

public class RemoveFromCommand extends AbstractCommand< WorldGuardPlugin > {

    public RemoveFromCommand( WorldGuardPlugin plugin ) {
        super( plugin );
    }

    @Override
    protected void execute( String label, Player player, String[] args ) {
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
            if ( !region.isOwner( args[ 1 ] ) ) {
                player.sendMessage( String.format( "%s is not a owner of %s.", args[ 1 ], region.getName() ) );
                return;
            }

            region.removeOwner( args[ 1 ] );
            player.sendMessage( String.format( "Removed %s as owner from %s.", args[ 1 ], region.getName() ) );
        } else { // Member
            if ( !region.isMember( args[ 1 ] ) ) {
                player.sendMessage( String.format( "%s is not a member of %s.", args[ 1 ], region.getName() ) );
                return;
            }

            region.removeMember( args[ 1 ] );
            player.sendMessage( String.format( "Removed %s as member from %s.", args[ 1 ], region.getName() ) );
        }
    }

}
