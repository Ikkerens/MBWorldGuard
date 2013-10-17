package com.ikkerens.worldguard.model.flagtypes;

import com.ikkerens.worldguard.exceptions.InvalidInputException;
import com.ikkerens.worldguard.model.Flag;

public class StringFlag extends Flag< String > {

    public StringFlag( final String name, final String defaultValue ) {
        super( name, defaultValue );
    }

    @Override
    public String parseInput( final String input ) throws InvalidInputException {
        return input;
    }

}
