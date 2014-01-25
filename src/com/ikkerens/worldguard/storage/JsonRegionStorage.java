package com.ikkerens.worldguard.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import com.ikkerens.worldguard.model.Region;

import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.game.Location;

public class JsonRegionStorage implements StorageHandler {
    private final HashMap< String, Region > regions;

    public JsonRegionStorage() {
        this.regions = new HashMap< String, Region >();
    }

    @Override
    public int init( final MBServerPlugin plugin ) {
        for ( final Entry< String, Region > entry : this.regions.entrySet() )
            entry.getValue().setName( entry.getKey() );
        return this.regions.size();
    }

    @Override
    public Region getRegion( final String name ) {
        return this.regions.get( name );
    }

    @Override
    public Collection< Region > getRegions( final Location loc ) {
        final ArrayList< Region > regions = new ArrayList< Region >();

        for ( final Region region : this.regions.values() ) {
            final Location min = region.getMinimumLocation();
            final Location max = region.getMaximumLocation();

            if ( ( loc.getX() >= min.getX() ) && ( loc.getX() <= max.getX() ) &&
                ( loc.getY() >= min.getY() ) && ( loc.getY() <= max.getY() ) &&
                ( loc.getZ() >= min.getZ() ) && ( loc.getZ() <= max.getY() ) )
                regions.add( region );
        }

        return regions;
    }

    @Override
    public void saveRegion( final Region region ) {
        this.regions.put( region.getName(), region );
    }

    @Override
    public void deleteRegion( final Region region ) {
        this.regions.remove( region.getName() );
    }
}
