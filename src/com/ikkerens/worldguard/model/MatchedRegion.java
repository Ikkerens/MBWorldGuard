package com.ikkerens.worldguard.model;

import java.util.EnumMap;

import com.ikkerens.worldguard.storage.StorageHandler;

import com.mbserver.api.game.Location;

public class MatchedRegion {
    private final EnumMap< Flag, String > flags;

    public MatchedRegion( final StorageHandler storage, final Location location ) {
        final Region[] regions = storage.getRegion( location );

        this.flags = new EnumMap< Flag, String >( Flag.class );
        for ( final Flag flag : Flag.values() ) {
            final int priority = 0;
            String value = null;

            for ( final Region region : regions )
                if ( region.getPriority() >= priority ) {
                    final String flValue = region.getFlagValue( flag );
                    if ( ( value == null ) || !flValue.equals( flag.getDefault() ) )
                        value = flValue;
                }

            this.flags.put( flag, value == null ? flag.getDefault() : value );
        }

    }

    public String getFlag( final Flag flag ) {
        return this.flags.get( flag );
    }
}
