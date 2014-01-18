package com.ikkerens.worldguard;

import com.ikkerens.worldguard.commands.AddToCommand;
import com.ikkerens.worldguard.commands.DefineCommand;
import com.ikkerens.worldguard.commands.FlagCommand;
import com.ikkerens.worldguard.commands.RemoveFromCommand;
import com.ikkerens.worldguard.listeners.BlockListener;
import com.ikkerens.worldguard.listeners.ChatListener;
import com.ikkerens.worldguard.listeners.MoveListener;
import com.ikkerens.worldguard.listeners.PvpListener;
import com.ikkerens.worldguard.storage.StorageHandler;

import com.mbserver.api.MBServerPlugin;
import com.mbserver.api.Manifest;
import com.mbserver.api.PluginManager;

@Manifest( name = "MBWorldGuard", authors = "Ikkerens", dependencies = "MBWorldEdit", config = Config.class )
public class WorldGuardPlugin extends MBServerPlugin {
    private StorageHandler storage;

    @Override
    public void onEnable() {
        this.getLogger().info( "Loading regions..." );

        final Config config = this.getConfig();
        this.storage = config.getStorageType().getStorage( this );
        final int loaded = this.storage.init( this );
        this.save();
        
        this.getLogger().info( String.format( "Finished loading %d regions.", loaded ) );

        final PluginManager pm = this.getPluginManager();

        pm.registerCommand( "/claim", new DefineCommand( this ) );
        pm.registerCommand( "/define", new DefineCommand( this ) );
        pm.registerCommand( "/flag", new FlagCommand( this ) );
        pm.registerCommand( "/addowner", new String[] { "/addmember" }, new AddToCommand( this ) );
        pm.registerCommand( "/removeowner", new String[] { "/removemember" }, new RemoveFromCommand( this ) );

        pm.registerEventHandler( new BlockListener( this ) );
        pm.registerEventHandler( new PvpListener( this ) );
        pm.registerEventHandler( new ChatListener( this ) );

        if ( config.isUsingMove() )
            pm.registerEventHandler( new MoveListener( this ) );
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
