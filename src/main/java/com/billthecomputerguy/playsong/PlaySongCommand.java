package com.billthecomputerguy.playsong;

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
        // Check if the command sender is a Player
        if (sender instanceof Player) {
            // Create player instance of current player, cast to sender (the source of the command)
            Player player = (Player) sender;

            if (isPlaying == false) {
                // Create a RadioSongPlayer object and load a song
                // from the 'AllStar.nbs' file located in the plugin's data folder
                rsp = new RadioSongPlayer((NBSDecoder.parse(new File(main.getDataFolder(), "AllStar.nbs"))));

                // Add the player to the list of players for the song player
                rsp.addPlayer(player);

                // Start playing the song
                rsp.setPlaying(true);

                // Update the 'isPlaying' status
                isPlaying = true;

                // Inform the player that the song has started
                player.sendMessage(ChatColor.BLUE + "Song started (type /playsong to stop)");

            } else {
                // If a song is already playing, stop it

                // Stop the song
                rsp.setPlaying(false);

                // Destroy the song player object to free up resources
                rsp.destroy();

                // Update the 'isPlaying' status
                isPlaying = false;

                // Inform the player that the song has stopped
                player.sendMessage(ChatColor.BLUE + "Song stopped");
            }
        }
        return false;
    }
}
