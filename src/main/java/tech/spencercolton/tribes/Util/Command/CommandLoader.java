package tech.spencercolton.tribes.Util.Command;

import tech.spencercolton.tribes.Tribes;
import tech.spencercolton.tribes.Commands.TribesCmd;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandLoader {

    public static final HashMap<Class, String> executors = new HashMap<>();

    public static final List<PluginCommand> cmds = new ArrayList<>();

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
