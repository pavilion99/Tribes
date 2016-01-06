package tech.spencercolton.tribes.Util;

import tech.spencercolton.tribes.Commands.TribesCmd;
import tech.spencercolton.tribes.Util.Command.CommandLoader;
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
        CommandLoader.executors.keySet().stream().filter(g -> g.equals(t)).forEach(g -> {
            Message.message((Player) s, Config.invalidArgs);
            Message.message((Player) s, invalidArgsMappings.get(t));
        });
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
