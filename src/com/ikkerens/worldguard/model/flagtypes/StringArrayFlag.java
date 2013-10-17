package com.ikkerens.worldguard.model.flagtypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ikkerens.worldguard.exceptions.InvalidInputException;
import com.ikkerens.worldguard.model.Flag;

public class StringArrayFlag extends Flag< List< String > > {
    private static final List< String > EMPTY = Collections.unmodifiableList( new ArrayList< String >( 0 ) );

    public StringArrayFlag( final String name ) {
        super( name, EMPTY );
    }

    @Override
    public List< String > parseInput( final String input ) throws InvalidInputException {
        final String[] parts = input.split( "," );
        final ArrayList< String > returnValue = new ArrayList< String >();

        for ( String part : parts ) {
            part = part.trim();
            if ( part.startsWith( "/" ) )
                part = part.substring( 1, part.length() - 1 );
            if ( !part.equals( "" ) )
                returnValue.add( part );
        }

        return returnValue;
    }

    @Override
    public String valueToString( final List< String > input ) {
        return join( input, "," );
    }

    private static String join( final List< String > strings, final String glue ) {
        if ( strings.size() == 0 )
            return "";

        String returnValue = strings.get( 0 );
        for ( int i = 1; i < strings.size(); i++ )
            returnValue += glue + strings.get( i );

        return returnValue;
    }
}
