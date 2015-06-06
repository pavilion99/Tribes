package co.valdeon.Tribes.util;

import co.valdeon.Tribes.commands.TribesCmd;
import co.valdeon.Tribes.util.command.CommandLoader;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.HashMap;

public class Message {

    private static final HashMap<Class, String> invalidArgsMappings = new HashMap<>();

    public static void message(Player s, String... params) {
        String message = "";
        for(String string : params) {
            String g = ChatColor.translateAlternateColorCodes('&', string);
            message += g;
        }
        s.sendMessage(message);
    }

    public static void message(CommandSender s, String... params) {
        message((Player)s, params);
    }

    public static void messageInvalidArgs(CommandSender s, Class t) {
        for(Class g : CommandLoader.executors.keySet()) {
            if(g.equals(t)) {
                Message.message((Player)s, Config.invalidArgs);
                Message.message((Player)s, invalidArgsMappings.get(t));
            }
        }
    }

    public static String format(String s, String... args) {
        String temp = MessageFormat.format(s, args);
        temp = ChatColor.translateAlternateColorCodes('&', temp);
        return temp;
    }

    public static void init() {
        invalidArgsMappings.put(TribesCmd.class, "&cCorrect usage: /t");
    }

}
