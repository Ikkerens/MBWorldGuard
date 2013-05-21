package com.ikkerens.worldguard.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class RangeMap< K extends Comparable< K >, V > {
    private final TreeMap< K, TreeMap< K, V >> backingMap;

    public RangeMap() {
        this.backingMap = new TreeMap< K, TreeMap< K, V >>();
    }

    public void put( final K begin, final K end, final V value ) {
        if ( end.compareTo( begin ) < 0 )
            throw new IllegalArgumentException( "End cannot be lower than begin!" );

        if ( !this.backingMap.containsKey( begin ) )
            this.backingMap.put( begin, new TreeMap< K, V >() );

        this.backingMap.get( begin ).put( end, value );
    }

    public V getExact( final K begin, final K end ) {
        final TreeMap< K, V > subTree = this.backingMap.get( begin );
        return subTree == null ? null : subTree.get( end );
    }

    public Collection< V > getInRange( final K match ) {
        final Collection< V > values = new ArrayList< V >();

        for ( final TreeMap< K, V > subTree : this.backingMap.headMap( match, true ).values() )
            values.addAll( subTree.tailMap( match ).values() );

        return values;
    }
}