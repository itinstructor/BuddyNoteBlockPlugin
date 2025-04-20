package com.billthecomputerguy.playsong;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {
    // Store a reference to the command executor
    private PlaySongCommand playCommand;

    @Override
    public void onEnable() {
        // This method is called when the plugin is enabled.

        // Check if the data folder for the plugin exists
        if(!this.getDataFolder().exists()){
            // If the folder doesn't exist, create it
            boolean created = this.getDataFolder().mkdir();
            if(!created) {
                getLogger().warning("Failed to create data folder!");
            }
        }

        // Create command handler and store the reference
        playCommand = new PlaySongCommand(this);

        // Register the command
        Objects.requireNonNull(getCommand("playsong")).setExecutor(playCommand);
    }

    @Override
    public void onDisable() {
        // Clean up any playing songs when the plugin is disabled
        if (playCommand != null) {
            playCommand.cleanup();
        }
    }
}