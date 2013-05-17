package com.ikkerens.worldguard;

public class Config {
    private StorageType storage;

    public Config() {
        this.storage = StorageType.JSON;
    }

    public StorageType getStorageType() {
        return this.storage;
    }
}
