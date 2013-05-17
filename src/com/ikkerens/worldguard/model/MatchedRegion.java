package com.ikkerens.worldguard.model;

import java.util.EnumMap;

import com.ikkerens.worldguard.storage.StorageHandler;
import com.mbserver.api.game.Location;

public class MatchedRegion {
    private EnumMap< Flag, String > flags;

    public MatchedRegion( StorageHandler storage, Location location ) {
        Region[] regions = storage.getRegion( location );

        this.flags = new EnumMap< Flag, String >( Flag.class );
        for ( Flag flag : Flag.values() ) {
            int priority = 0;
            String value = null;

            for ( Region region : regions )
                if ( region.getPriority() >= priority ) {
                    String flValue = region.getFlagValue( flag );
                    if ( value == null || !flValue.equals( flag.getDefault() ) )
                        value = flValue;
                }

            flags.put( flag, value == null ? flag.getDefault() : value );
        }

    }

    public String getFlag( Flag flag ) {
        return this.flags.get( flag );
    }
}
