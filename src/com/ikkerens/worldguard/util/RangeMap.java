package com.ikkerens.worldguard.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class RangeMap< K extends Comparable< K >, V > {
    private TreeMap< K, TreeMap< K, V >> backingMap;

    public RangeMap() {
        this.backingMap = new TreeMap< K, TreeMap< K, V >>();
    }

    public void put( K begin, K end, V value ) {
        if ( end.compareTo( begin ) < 0 )
            throw new IllegalArgumentException( "End cannot be lower than begin!" );

        if ( !this.backingMap.containsKey( begin ) )
            this.backingMap.put( begin, new TreeMap< K, V >() );

        this.backingMap.get( begin ).put( end, value );
    }

    public V getExact( K begin, K end ) {
        TreeMap< K, V > subTree = this.backingMap.get( begin );
        return subTree == null ? null : subTree.get( end );
    }

    public Collection< V > getInRange( K match ) {
        Collection< V > values = new ArrayList< V >();

        for ( TreeMap< K, V > subTree : this.backingMap.headMap( match, true ).values() )
            values.addAll( subTree.tailMap( match ).values() );

        return values;
    }
}