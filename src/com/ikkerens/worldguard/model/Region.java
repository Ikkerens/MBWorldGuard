package com.ikkerens.worldguard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.ikkerens.worldguard.exceptions.InvalidInputException;

import com.mbserver.api.Constructors;
import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.game.Location;

public class Region {
    private static final int                         DEFAULT_PRIORITY = 1;

    private String                                   name;
    private String                                   world;
    private int[]                                    min, max;
    private int                                      priority;
    private final Map< String, String >              flags;
    private final ArrayList< String >                owners, members;

    private final transient Map< Flag< ? >, Object > flagValues;
    private transient Location                       minL, maxL;

    private Region() {
        this.priority = DEFAULT_PRIORITY;
        this.flags = new HashMap< String, String >();
        this.flagValues = new HashMap< Flag< ? >, Object >();
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

        for ( final Entry< String, String > flagMatch : this.flags.entrySet() )
            try {
                final Flag< ? > flag = Flags.flags.get( flagMatch.getKey().toLowerCase() );
                if ( flag == null ) {
                    Logger.getLogger( "Minebuilder" ).warning( String.format( "Flag '%s' does not exist!", flagMatch.getKey() ) );
                    continue;
                }

                this.flagValues.put( flag, flag.parseInput( flagMatch.getValue() ) );
            } catch ( final InvalidInputException e ) {
                Logger.getLogger( "Minebuilder" ).warning( String.format( "%s: %s", flagMatch.getKey(), e.getMessage() ) );
            }
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

    public boolean hasFlag( final Flag< ? > flag ) {
        return this.flags.containsKey( flag );
    }

    @SuppressWarnings( "unchecked" )
    public < F extends Flag< V >, V > V getFlagValue( final F flag ) {
        return (V) this.flagValues.get( flag );
    }

    public < F extends Flag< V >, V > void setFlag( final F flag, final V value ) {
        this.flagValues.put( flag, value );
        this.flags.put( flag.getName(), flag.valueToString( value ) );
    }

    public void clearFlag( final Flag< ? > flag ) {
        this.flagValues.remove( flag );
        this.flags.remove( flag.getName() );
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

        if ( !this.members.contains( name ) )
            this.members.add( name );
    }

    public void addOwner( String name ) {
        name = name.toLowerCase();

        if ( this.members.contains( name ) )
            this.members.remove( name );

        if ( !this.owners.contains( name ) )
            this.owners.add( name );
    }

    public void removeMember( String name ) {
        name = name.toLowerCase();

        if ( this.members.contains( name ) )
            this.members.remove( name );
    }

    public void removeOwner( String name ) {
        name = name.toLowerCase();

        if ( this.owners.contains( name ) )
            this.owners.remove( name );
    }
}
