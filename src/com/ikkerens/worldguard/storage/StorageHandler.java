package com.ikkerens.worldguard.storage;

import java.util.Collection;

import com.ikkerens.worldguard.model.Region;

import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.game.Location;

public interface StorageHandler {
    public int init( MBServerPlugin plugin );

    public Region getRegion( String name );

    public Collection< Region > getRegions( Location location );

    public void saveRegion( Region region );

    public void deleteRegion( Region region );
}
