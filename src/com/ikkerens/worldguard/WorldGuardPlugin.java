package com.ikkerens.worldguard;

import com.ikkerens.worldguard.commands.DefineCommand;
import com.ikkerens.worldguard.listeners.BlockListener;
import com.ikkerens.worldguard.storage.StorageHandler;

import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.Manifest;
import com.mbserver.api.PluginManager;

@Manifest( name = "MBWorldGuard", authors = "Ikkerens", dependencies = "MBWorldEdit", config = Config.class )
public class WorldGuardPlugin extends MBServerPlugin {
    private StorageHandler storage;

    @Override
    public void onEnable() {
        final Config config = this.getConfig();
        this.storage = config.getStorageType().getStorage( this );
        this.storage.init( this );
        this.save();

        final PluginManager pm = this.getPluginManager();

        pm.registerCommand( "/define", new DefineCommand( this ) );

        pm.registerEventHandler( new BlockListener( this ) );
    }

    public StorageHandler getStorage() {
        return this.storage;
    }

    private void save() {
        this.saveConfig();
        this.getServer().getConfigurationManager().save( this, this.storage );
    }

    @Override
    public void onDisable() {
        this.save();
    }
}
