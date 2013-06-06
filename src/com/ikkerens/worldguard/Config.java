package com.ikkerens.worldguard;

public class Config {
    private final StorageType storage;
    private final boolean     use_move_events;

    public Config() {
        this.storage = StorageType.JSON;
        this.use_move_events = false;
    }

    public StorageType getStorageType() {
        return this.storage;
    }

    public boolean isUsingMove() {
        return this.use_move_events;
    }
}
