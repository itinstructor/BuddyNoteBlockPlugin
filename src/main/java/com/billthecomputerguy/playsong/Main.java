package com.billthecomputerguy.playsong;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // This method is called when the plugin is enabled.

        // Get the name of the data folder for the plugin
        // The data folder is the name of the plugin, PlaySong
        // Check if the data folder for the plugin exists
        if(this.getDataFolder().exists()){

            // If the folder doesn't exist, create it
            this.getDataFolder().mkdir();
        }
        // Create a command handler for the "playsong" command
        // and associate it with the PlaySongCommand class
        getCommand("playsong").setExecutor((new PlaySongCommand(this)));
    }
}
