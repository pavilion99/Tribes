package tech.spencercolton.tribes.Commands;

import tech.spencercolton.tribes.Components.Tribe;
import tech.spencercolton.tribes.Util.Message;
import tech.spencercolton.tribes.Util.TribeLoader;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("UnusedParameters")
class TribesMapCmd {

    char currentColor;

    public void execute(CommandSender sender, String[] args) {
        this.currentColor = 0x0;

        Location l = ((Player)sender).getLocation();

        Chunk c = l.getWorld().getChunkAt(l);

        int initx = c.getX();
        int initz = c.getZ();

        String fin[] = new String[9];

        Arrays.fill(fin, "");

        HashMap<Tribe, ChatColor> colorMap = new HashMap<>();

        for(int z = -4; z <= 4; z++) {
            for(int x = -26; x <= 26; x++) {

                Chunk k = l.getWorld().getChunkAt(initx + x, initz + z);

                Tribe t = TribeLoader.getChunkOwner(k);

                if(t == null)
                    fin[z+4] += ChatColor.GRAY;
                else {
                    if(!colorMap.containsKey(t))
                        colorMap.put(t, getNextColor());

                    fin[z+4] += colorMap.get(t);
                }

                fin[z+4] += "#";
            }
        }

        String fin2 = ChatColor.GRAY + "";

        for(Tribe t : colorMap.keySet()) {
            fin2 += colorMap.get(t);
            fin2 += "#";
            fin2 += ChatColor.GRAY;
            fin2 += ":" + t.getName();
            fin2 += "    ";
        }

        Message.message(sender, fin2);

        for(int g = 8; g >= 0; g--) {
            Message.message(sender, fin[g]);
        }
    }

    private ChatColor getNextColor() {
        if(this.currentColor == 0xF)
            this.currentColor = 0x0;

        this.currentColor += 0x1;

        return ChatColor.getByChar(Integer.toHexString(this.currentColor - 1));
    }

}
