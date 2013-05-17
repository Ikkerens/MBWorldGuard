package com.ikkerens.worldguard;

import com.ikkerens.worldguard.storage.JsonRegionStorage;
import com.ikkerens.worldguard.storage.StorageHandler;
import com.mbserver.api.MBServerPlugin;

public enum StorageType {
    JSON ( JsonRegionStorage.class );

    private Class< ? extends StorageHandler > clazz;

    private StorageType( Class< ? extends StorageHandler > clazz ) {
        this.clazz = clazz;
    }

    @SuppressWarnings( "unchecked" )
    public < T extends StorageHandler > T getStorage( MBServerPlugin plugin ) {
        return (T) plugin.getServer().getConfigurationManager().load( plugin, this.clazz );
    }
}
