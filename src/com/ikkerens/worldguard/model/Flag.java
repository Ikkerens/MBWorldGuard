package com.ikkerens.worldguard.model;

import static com.ikkerens.worldguard.model.FlagOption.ALLOW;
import static com.ikkerens.worldguard.model.FlagOption.DEFAULT;
import static com.ikkerens.worldguard.model.FlagOption.DENY;

import java.util.Arrays;
import java.util.List;

public enum Flag {
    PVP ( ALLOW, DENY ),
    BUILD ( DEFAULT, ALLOW, DENY ),
    ENTRY ( ALLOW, DENY, "owners", "members" );

    private List< String > possibilities;

    private Flag( final String... possibilities ) {
        this.possibilities = Arrays.asList( possibilities );
    }

    public boolean validate( final String input ) {
        return this.possibilities.contains( input.toLowerCase() );
    }

    public String getDefault() {
        return this.possibilities.get( 0 );
    }
}
