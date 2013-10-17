package com.ikkerens.worldguard.model.flagtypes;

import com.ikkerens.worldguard.exceptions.InvalidInputException;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.flagtypes.GroupStateFlag.GroupState;

public class GroupStateFlag extends Flag< GroupState > {
    public static enum GroupState {
        ALLOW,
        DENY,
        OWNERS,
        MEMBERS;
    }

    public GroupStateFlag( final String name, final GroupState defaultValue ) {
        super( name, defaultValue );
    }

    @Override
    public GroupState parseInput( final String input ) throws InvalidInputException {
        try {
            return GroupState.valueOf( input.toUpperCase() );
        } catch ( final IllegalArgumentException e ) {
            throw new InvalidInputException( "You can only enter either Allow, Deny, Owners or Members." );
        }
    }
}
