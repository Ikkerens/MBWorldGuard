package com.ikkerens.worldguard.model.flagtypes;

import com.ikkerens.worldguard.exceptions.InvalidInputException;
import com.ikkerens.worldguard.model.Flag;
import com.ikkerens.worldguard.model.flagtypes.StateFlag.State;

public class StateFlag extends Flag< State > {
    public static enum State {
        ALLOW,
        DENY;
    }

    public StateFlag( final String name, final State defaultValue ) {
        super( name, defaultValue );
    }

    @Override
    public State parseInput( final String input ) throws InvalidInputException {
        try {
            return State.valueOf( input.toUpperCase() );
        } catch ( final IllegalArgumentException e ) {
            throw new InvalidInputException( "You can only enter either Allow or Deny." );
        }
    }
}
