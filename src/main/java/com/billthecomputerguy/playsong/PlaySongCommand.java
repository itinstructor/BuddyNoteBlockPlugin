// This defines the package (folder structure) that this class belongs to
package com.billthecomputerguy.playsong;

// These are import statements that bring in external classes we need in our code
// This imports the RadioSongPlayer class for playing music in Minecraft
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
// This imports the NBSDecoder which converts .nbs files to a format the RadioSongPlayer can use
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
// This imports the ChatColor class for sending colored messages to players
import net.md_5.bungee.api.ChatColor;
// These Bukkit imports are for Minecraft server plugin functionality
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// Java utilities for working with files, data structures, and unique identifiers
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
// Import for proper logging levels
import java.util.logging.Level;

// This class defines the behavior for the "/playsong" command
// It implements CommandExecutor which is Bukkit's interface for handling commands
public class PlaySongCommand implements CommandExecutor {

    // This variable holds a reference to our main plugin class
    // The "final" keyword means this reference cannot be changed after it's set
    private final Main main;

    // This HashMap stores which players have songs playing
    // It maps a player's unique ID to their song player
    // Using HashMap allows us to quickly look up a player's song
    private final Map<UUID, RadioSongPlayer> playerSongs = new HashMap<>();

    // This is the constructor - it runs when a new PlaySongCommand object is created
    // It takes our main plugin class as a parameter so we can access plugin resources
    public PlaySongCommand(Main main) {
        // Store the reference to our main plugin class
        this.main = main;
    }

    // This method is called whenever a player uses the "/playsong" command
    // It overrides the default method from CommandExecutor interface
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the command sender is a Player (not console or command block)
        if (!(sender instanceof Player)) {
            // If not a player, send an error message
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            // Return true to indicate we've handled the command (even though we rejected it)
            return true;
        }

        // Cast the sender to a Player type so we can access player-specific methods
        Player player = (Player) sender;
        // Get the player's unique ID which we'll use as a key in our HashMap
        UUID playerUUID = player.getUniqueId();

        // Check if the player has permission to use this command
        if (!player.hasPermission("playsong.use")) {
            // If they don't have permission, send an error message
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            // Return true to indicate we've handled the command
            return true;
        }

        // Check if the player already has a song playing by seeing if they exist in our HashMap
        if (playerSongs.containsKey(playerUUID)) {
            // If they do have a song playing, get their RadioSongPlayer object
            RadioSongPlayer rsp = playerSongs.get(playerUUID);
            // Stop the song from playing
            rsp.setPlaying(false);
            // Clean up resources used by the song player
            rsp.destroy();
            // Remove the player from our HashMap since they no longer have a song playing
            playerSongs.remove(playerUUID);
            // Send a message to the player letting them know the song stopped
            player.sendMessage(ChatColor.BLUE + "Song stopped");
            // Return true to indicate we've handled the command
            return true;
        } else {
            // If they don't have a song playing, create a new File object pointing to our song file
            File songFile = new File(main.getDataFolder(), "AllStar.nbs");

            // Check if the song file actually exists on disk
            if (!songFile.exists()) {
                // If it doesn't exist, tell the player
                player.sendMessage(ChatColor.RED + "Song file not found! Please contact an administrator.");
                // Log a warning to the server console so admins know there's a problem
                main.getLogger().warning("Missing song file: " + songFile.getAbsolutePath());
                // Return true to indicate we've handled the command
                return true;
            }

            // Use a try-catch block to handle any errors that might occur when playing the song
            try {
                // Create a new RadioSongPlayer with our song file (after parsing it with NBSDecoder)
                RadioSongPlayer rsp = new RadioSongPlayer(NBSDecoder.parse(songFile));
                // Add the player to the list of players who can hear this song
                rsp.addPlayer(player);
                // Start playing the song
                rsp.setPlaying(true);
                // Store the RadioSongPlayer in our HashMap so we can access it later
                playerSongs.put(playerUUID, rsp);
                // Send a message to the player letting them know the song started
                player.sendMessage(ChatColor.BLUE + "Song started (type /playsong to stop)");
                // Return true to indicate we've handled the command
                return true;
            } catch (Exception e) {
                // If any error occurs, tell the player something went wrong
                player.sendMessage(ChatColor.RED + "Error playing song: " + e.getMessage());
                // Log the full error details to the server console for troubleshooting
                // This is better than printStackTrace() because it uses the plugin's logger
                // and includes the full exception plus context about which player had the error
                main.getLogger().log(Level.SEVERE, "Error playing song for player " + player.getName(), e);
                // Return true to indicate we've handled the command (even though it failed)
                return true;
            }
        }
    }

    // This method is called when the plugin is disabled (server shutdown or reload)
    // It ensures we properly clean up all song players to prevent memory leaks
    public void cleanup() {
        // Loop through all RadioSongPlayer objects in our HashMap
        for (RadioSongPlayer rsp : playerSongs.values()) {
            // Stop each song from playing
            rsp.setPlaying(false);
            // Clean up resources used by each song player
            rsp.destroy();
        }
        // Clear our HashMap to remove all references
        playerSongs.clear();
    }
}