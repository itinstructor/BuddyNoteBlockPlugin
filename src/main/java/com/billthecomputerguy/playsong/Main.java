package com.billthecomputerguy.playsong;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // The data folder is the name of the plugin, PlaySong
        if(this.getDataFolder().exists()){
            // If the folder doesn't exist, create it
            this.getDataFolder().mkdir();
        }
        // Create NoteBlock playsong command object
        getCommand("playsong").setExecutor((new PlaySongCommand(this)));
    }
}
