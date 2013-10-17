package com.ikkerens.worldguard.model.flagtypes;

import com.ikkerens.worldguard.exceptions.InvalidInputException;
import com.ikkerens.worldguard.model.Flag;

import com.mbserver.api.game.GameMode;

public class GameModeFlag extends Flag< GameMode > {

    public GameModeFlag( final String name, final GameMode defaultValue ) {
        super( name, defaultValue );
    }

    @Override
    public GameMode parseInput( final String input ) throws InvalidInputException {
        try {
            return GameMode.valueOf( input.toUpperCase() );
        } catch ( final IllegalArgumentException e ) {
            throw new InvalidInputException( "That is not a valid game mode!" );
        }
    }

}
