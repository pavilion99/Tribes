package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.commands.TribesCmd;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandLoader {

    public static BiMap<Class, String> executors;

    public static List<PluginCommand> cmds = new ArrayList<>();

    public static void init(Tribes tribes) {
        String[] listCmds = {"tribes"};

        for(String s : listCmds) {
            cmds.add(tribes.getCommand(s));
        }

        initExecutors();
    }

    private static void initExecutors() {
        executors.put(TribesCmd.class, "tribes");
    }

}
