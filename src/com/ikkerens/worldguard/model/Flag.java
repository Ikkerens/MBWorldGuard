package com.ikkerens.worldguard.model;

import com.ikkerens.worldguard.Config;
import com.ikkerens.worldguard.exceptions.InvalidInputException;

public abstract class Flag< T > {
    private final String name;
    private final T      defaultValue;

    public Flag( final String name, final T defaultValue ) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public final String getName() {
        return this.name;
    }

    public final T getDefaultValue() {
        return this.defaultValue;
    }

    public abstract T parseInput( String input ) throws InvalidInputException;

    public String valueToString( final T input ) {
        return input.toString();
    }

    public boolean canUse( final Config config ) {
        return true;
    }
}
