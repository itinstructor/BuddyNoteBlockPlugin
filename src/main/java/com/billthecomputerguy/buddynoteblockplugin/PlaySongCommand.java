package com.billthecomputerguy.buddynoteblockplugin;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class PlaySongCommand implements CommandExecutor {

    // Create reference variable to Main
    private Main main;
    private boolean isPlaying = false;
    // Create RadioSongPlayer reference variable
    private RadioSongPlayer rsp;

    // Constructor with parameter of Main to access Main from this class
    public PlaySongCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Is the command sender a Player
        if (sender instanceof Player) {
            // Create player instance of current player, cast to sender (the source of the command)
            Player player = (Player) sender;
            if (isPlaying == false) {
                // Create RadioSongPlayer object with path from Main and file name
                rsp = new RadioSongPlayer((NBSDecoder.parse(new File(main.getDataFolder(), "AllStar.nbs"))));
                rsp.addPlayer(player);
                rsp.setPlaying(true);
                isPlaying = true;
                player.sendMessage(ChatColor.BLUE + "Song started (type buddysong to stop)");
            } else {
                // Stop the song
                rsp.setPlaying(false);
                // Destroy the object
                rsp.destroy();
                isPlaying = false;
                player.sendMessage(ChatColor.BLUE + "Song stopped");
            }
        }
        return false;
    }
}
