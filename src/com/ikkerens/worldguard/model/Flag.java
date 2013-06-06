package com.ikkerens.worldguard.model;

import static com.ikkerens.worldguard.model.FlagOption.ALLOW;
import static com.ikkerens.worldguard.model.FlagOption.DENY;
import static com.ikkerens.worldguard.model.FlagOption.MEMBERS;
import static com.ikkerens.worldguard.model.FlagOption.OWNERS;

import java.util.Arrays;
import java.util.List;

public enum Flag {
    PVP ( ALLOW, DENY ),
    BUILD ( MEMBERS, OWNERS, ALLOW, DENY ),
    ENTRY ( ALLOW, DENY, OWNERS, MEMBERS ),
    LEAVE ( ALLOW, DENY, OWNERS, MEMBERS );

    private List< String > possibilities;

    private Flag( final String... possibilities ) {
        this.possibilities = Arrays.asList( possibilities );
    }

    public boolean validate( final String input ) {
        return ( this.possibilities.size() == 0 ) || this.possibilities.contains( input.toLowerCase() );
    }

    public String getDefault() {
        return this.possibilities.size() == 0 ? "" : this.possibilities.get( 0 );
    }
}
