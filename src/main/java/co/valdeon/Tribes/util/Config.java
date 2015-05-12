package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;

public class Config {

    private Tribes tribes;

    public static String consoleExecutionFailure;
    public static String invalidArgs;

    public Config(Tribes tribes) {
        this.tribes = tribes;
        consoleExecutionFailure = (String)get(String.class, "consoleExecutionFailure");
        invalidArgs = (String)get(String.class, "invalidArgs");
    }

    public Object get(Class c, String path) {
        if(c.equals(String.class)) {
            return tribes.getBukkitCfg().getString(path);
        }else if(c.equals(int.class)) {
            return tribes.getBukkitCfg().getInt(path);
        }else if(c.equals(boolean.class)) {
            return tribes.getBukkitCfg().getBoolean(path);
        }else if(c.equals(String[].class)) {
            return tribes.getBukkitCfg().getStringList(path);
        }else{
            return null;
        }
    }

}
