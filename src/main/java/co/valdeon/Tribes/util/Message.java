package co.valdeon.Tribes.util;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.util.command.CommandLoader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class Message {

    public static HashMap<Class, String> invalidArgsMappings;

    public static void message(CommandSender s, String... params) {
        String message = "";
        for(String string : params) {
            String g = ChatColor.translateAlternateColorCodes('&', string);
            message += g;
        }
        s.sendMessage(message);
    }

    public static void messageInvalidArgs(CommandSender s, Class t) {
        for(Class g : CommandLoader.executors.keySet()) {
            if(g.equals(t)) {
                Message.message(s, Config.invalidArgs);
                Message.message(s, invalidArgsMappings.get(t));
            }
        }
    }

    public static void init() {
        invalidArgsMappings.put(TribesCmd.class, "&cCorrect usage: /t");
    }

}
