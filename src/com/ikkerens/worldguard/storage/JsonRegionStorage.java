package com.ikkerens.worldguard.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ikkerens.worldguard.model.Region;
import com.ikkerens.worldguard.util.RangeMap;
import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.game.Location;
import com.mbserver.api.game.World;

public class JsonRegionStorage implements StorageHandler {
    private HashMap< String, Region >                                                                       regions;
    private transient HashMap< World, RangeMap< Integer, RangeMap< Integer, RangeMap< Integer, Region >>> > regions_tree;

    public JsonRegionStorage() {
        this.regions = new HashMap< String, Region >();
    }

    @Override
    public int init( MBServerPlugin plugin ) {
        this.regions_tree = new HashMap< World, RangeMap< Integer, RangeMap< Integer, RangeMap< Integer, Region >>> >();

        int loaded = 0;
        for ( Region region : this.regions.values() ) {
            region.init( plugin );
            this.indexRegion( region );
            loaded++;
        }

        return loaded;
    }

    @Override
    public Region getRegion( String name ) {
        return this.regions.get( name );
    }

    @Override
    public Region[] getRegion( Location location ) {
        Collection< Region > regionsC = this.getRegions( location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ() );
        Region[] regions = new Region[ regionsC.size() ];
        regionsC.toArray( regions );
        return regions;
    }

    @Override
    public void saveRegion( Region region ) {
        this.regions.put( region.getName(), region );
        this.indexRegion( region );
    }

    @Override
    public void deleteRegion( Region region ) {

    }

    private void indexRegion( Region region ) {
        this.addRegion( region );
    }

    private Collection< Region > getRegions( World world, int x, int y, int z ) {
        Collection< Region > regions = new ArrayList< Region >();
        for ( RangeMap< Integer, RangeMap< Integer, Region >> subMap : this.regions_tree.get( world ).getInRange( x ) )
            for ( RangeMap< Integer, Region > subSubMap : subMap.getInRange( y ) )
                regions.addAll( subSubMap.getInRange( z ) );
        return regions;
    }

    private void addRegion( Region region ) {
        Location min = region.getMinimumLocation();
        Location max = region.getMaximumLocation();

        RangeMap< Integer, RangeMap< Integer, RangeMap< Integer, Region >>> regions = this.regions_tree.get( min.getWorld() );

        if ( regions == null ) {
            regions = new RangeMap< Integer, RangeMap< Integer, RangeMap< Integer, Region >>>();
            this.regions_tree.put( min.getWorld(), regions );
        }

        RangeMap< Integer, RangeMap< Integer, Region >> subRegions = regions.getExact( min.getBlockX(), max.getBlockX() );

        if ( subRegions == null ) {
            subRegions = new RangeMap< Integer, RangeMap< Integer, Region >>();
            regions.put( min.getBlockX(), max.getBlockX(), subRegions );
        }

        RangeMap< Integer, Region > subSubRegions = subRegions.getExact( min.getBlockY(), max.getBlockY() );

        if ( subSubRegions == null ) {
            subSubRegions = new RangeMap< Integer, Region >();
            subRegions.put( min.getBlockY(), max.getBlockY(), subSubRegions );
        }

        subSubRegions.put( min.getBlockZ(), max.getBlockZ(), region );
    }
}
