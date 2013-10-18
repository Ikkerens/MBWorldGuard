package com.ikkerens.worldguard.model;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.ikkerens.worldguard.storage.StorageHandler;

import com.mbserver.api.game.Location;

public class MatchedRegion {
    private NavigableMap< Integer, ArrayList< Region >> regions;

    public MatchedRegion( final StorageHandler storage, final Location location ) {
        this.regions = new TreeMap< Integer, ArrayList< Region >>();
        final Region[] regions = storage.getRegions( location );

        for ( final Region region : regions ) {
            if ( !this.regions.containsKey( region.getPriority() ) )
                this.regions.put( region.getPriority(), new ArrayList< Region >( 1 ) );
            this.regions.get( region.getPriority() ).add( region );
        }

        this.regions = this.regions.descendingMap();
    }

    public < F extends Flag< V >, V > V getFlagValue( final F flag ) {
        for ( final ArrayList< Region > regions : this.regions.values() )
            for ( final Region region : regions )
                if ( region.hasFlag( flag ) )
                    return region.getFlagValue( flag );

        return flag.getDefaultValue();
    }

    public boolean isOwner( final String name ) {
        for ( final ArrayList< Region > regions : this.regions.values() )
            for ( final Region region : regions )
                if ( region.isOwner( name ) )
                    return true;

        return false;
    }

    public boolean isMember( final String name ) {
        for ( final ArrayList< Region > regions : this.regions.values() )
            for ( final Region region : regions )
                if ( region.isMember( name ) )
                    return true;

        return false;
    }
}
