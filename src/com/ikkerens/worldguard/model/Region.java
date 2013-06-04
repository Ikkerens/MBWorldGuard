package com.ikkerens.worldguard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.mbserver.api.Constructors;
import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.game.Location;

public class Region {
    private static final int              DEFAULT_PRIORITY = 1;

    private String                        name;
    private String                        world;
    private int[]                         min, max;
    private int                           priority;
    private final HashMap< Flag, String > flags;
    private final ArrayList< String >     owners, members;

    private transient Location            minL, maxL;

    private Region() {
        this.priority = DEFAULT_PRIORITY;
        this.flags = new HashMap< Flag, String >();
        this.owners = new ArrayList< String >();
        this.members = new ArrayList< String >();
    }

    public Region( final String name, final Location min, final Location max ) {
        this();
        this.name = name;
        this.redefine( min, max );
    }

    public void init( final MBServerPlugin plugin ) {
        this.minL = Constructors.newLocation( plugin.getServer().getWorld( this.world ), this.min[ 0 ], this.min[ 1 ], this.min[ 2 ] );
        this.maxL = Constructors.newLocation( plugin.getServer().getWorld( this.world ), this.max[ 0 ], this.max[ 1 ], this.max[ 2 ] );

        for ( final Entry< Flag, String > flagMatch : this.flags.entrySet() )
            if ( !flagMatch.getKey().validate( flagMatch.getValue() ) )
                this.flags.put( flagMatch.getKey(), flagMatch.getKey().getDefault() );
    }

    public void redefine( final Location min, final Location max ) {
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

    public void setPriority( final int priority ) {
        this.priority = priority;
    }

    public boolean hasFlag( final Flag flag ) {
        return this.flags.containsKey( flag );
    }

    public String getFlagValue( final Flag flag ) {
        return this.flags.containsKey( flag ) ? this.flags.get( flag ) : flag.getDefault();
    }

    public void setFlag( final Flag flag, final String value ) {
        this.flags.put( flag, value.toLowerCase() );
    }

    public void clearFlag( final Flag flag ) {
        this.flags.remove( flag );
    }

    public boolean isOwner( final String name ) {
        return this.owners.contains( name.toLowerCase() );
    }

    public boolean isMember( final String name ) {
        return this.isOwner( name ) || this.members.contains( name.toLowerCase() );
    }

    public void addMember( String name ) {
        name = name.toLowerCase();

        if ( this.owners.contains( name ) )
            throw new IllegalArgumentException( String.format( "%s is already an owner.", name ) );

        if ( this.members.contains( name ) )
            return;

        this.members.add( name );
    }

    public void addOwner( String name ) {
        name = name.toLowerCase();

        if ( this.members.contains( name ) )
            this.members.remove( name );

        if ( this.owners.contains( name ) )
            return;

        this.owners.add( name );
    }
}
