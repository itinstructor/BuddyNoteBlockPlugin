package com.billthecomputerguy.buddynoteblockplugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // The data folder is the name of the plugin, BuddyNoteBlockPlugin
        if(this.getDataFolder().exists()){
            // If the folder doesn't exist, create it
            this.getDataFolder().mkdir();
        }
        // Create NoteBlock buddysong command object
        getCommand("buddysong").setExecutor((new PlaySongCommand(this)));
    }
}
