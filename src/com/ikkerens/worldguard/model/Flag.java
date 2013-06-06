package com.ikkerens.worldguard.model;

import static com.ikkerens.worldguard.model.FlagOption.ALLOW;
import static com.ikkerens.worldguard.model.FlagOption.DENY;
import static com.ikkerens.worldguard.model.FlagOption.MEMBERS;
import static com.ikkerens.worldguard.model.FlagOption.OWNERS;

import java.util.Arrays;
import java.util.List;

import com.ikkerens.worldguard.Config;

import com.mbserver.api.game.Player;

public enum Flag {
    PVP ( ALLOW, DENY ),
    BUILD ( MEMBERS, OWNERS, ALLOW, DENY ),
    ENTRY ( ALLOW, DENY, OWNERS, MEMBERS ) {
        @Override
        public boolean canUse( final Config config, final Player player ) {
            if ( !config.isUsingMove() ) {
                player.sendMessage( String.format( "Move events are disabled in the configuration." ) );
                return false;
            }

            return true;
        }
    },
    LEAVE ( ALLOW, DENY, OWNERS, MEMBERS ) {
        @Override
        public boolean canUse( final Config config, final Player player ) {
            if ( !config.isUsingMove() ) {
                player.sendMessage( String.format( "Move events are disabled in the configuration." ) );
                return false;
            }

            return true;
        }
    };

    private List< String > possibilities;

    private Flag( final String... possibilities ) {
        this.possibilities = Arrays.asList( possibilities );
    }

    public boolean canUse( final Config config, final Player player ) {
        return true;
    }

    public boolean validate( final String input ) {
        return ( this.possibilities.size() == 0 ) || this.possibilities.contains( input.toLowerCase() );
    }

    public String getDefault() {
        return this.possibilities.size() == 0 ? "" : this.possibilities.get( 0 );
    }
}
