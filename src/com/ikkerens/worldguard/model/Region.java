package com.ikkerens.worldguard.model;

import java.util.HashMap;
import java.util.Map.Entry;

import com.mbserver.api.Constructors;
import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.game.Location;

public class Region {
    private String                  name;
    private String                  world;
    private int[]                   min, max;
    private int                     priority;
    private HashMap< Flag, String > flags;

    private transient Location      minL, maxL;

    private Region() {
        this.priority = 1;
        this.flags = new HashMap< Flag, String >();
    }

    public Region( String name, Location min, Location max ) {
        this();
        this.name = name;
        this.redefine( min, max );
    }

    public void init( MBServerPlugin plugin ) {
        this.minL = Constructors.newLocation( plugin.getServer().getWorld( this.world ), this.min[ 0 ], this.min[ 1 ], this.min[ 2 ] );
        this.maxL = Constructors.newLocation( plugin.getServer().getWorld( this.world ), this.max[ 0 ], this.max[ 1 ], this.max[ 2 ] );

        for ( Entry< Flag, String > flagMatch : this.flags.entrySet() )
            if ( !flagMatch.getKey().validate( flagMatch.getValue() ) )
                this.flags.put( flagMatch.getKey(), flagMatch.getKey().getDefault() );
    }

    public void redefine( Location min, Location max ) {
        this.world = min.getWorld().getWorldName();
        this.minL = min;
        this.maxL = max;
        this.min = new int[] { min.getBlockX(), min.getBlockY(), min.getBlockZ() };
        this.max = new int[] { max.getBlockX(), max.getBlockY(), max.getBlockZ() };
    }

    public String getName() {
        return this.name;
    }

    public Location getMinimumLocation() {
        return this.minL;
    }

    public Location getMaximumLocation() {
        return this.maxL;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority( int priority ) {
        this.priority = priority;
    }

    public String getFlagValue( Flag flag ) {
        return this.flags.containsKey( flag ) ? this.flags.get( flag ) : flag.getDefault();
    }

    public void setFlag( Flag flag, String value ) {
        this.flags.put( flag, value );
    }

    public void clearFlag( Flag flag ) {
        this.flags.remove( flag );
    }
}
