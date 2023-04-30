package com.billthecomputerguy.noteblockplugin;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.File;
public class PlaySongCommand implements CommandExecutor {

    // Create reference variable to Main
    private Main main;
    public PlaySongCommand(Main main){
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof  Player){
            Player player = (Player) sender;

            // Create RadioSongPlayer object with path from Main and file name
            RadioSongPlayer rsp = new RadioSongPlayer((NBSDecoder.parse(new File(main.getDataFolder(), "AllStar.nbs"))));
            rsp.addPlayer(player);
            rsp.setPlaying(true);

            // Stop the song
            // rsp.setPlaying(false);
            // Destroy the object
            // rsp.destroy();
        }
        return false;
    }
}
