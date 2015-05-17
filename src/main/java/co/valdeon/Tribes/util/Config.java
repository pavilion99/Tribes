package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;

public class Config {

    private final Tribes tribes;

    public static String consoleExecutionFailure;
    public static String invalidArgs;
    public static String dbName;
    public static String world;
    public static String earnCoinsMessage;

    public static int tierOneChunks;
    public static int tierTwoChunks;
    public static int tierThreeChunks;
    public static int tierFourChunks;
    public static int tierFiveChunks;


    public Config(Tribes tribes) {
        this.tribes = tribes;
        consoleExecutionFailure = (String)get(String.class, "consoleExecutionFailure");
        invalidArgs = (String)get(String.class, "invalidArgs");
        dbName = (String)get(String.class, "db");
        world = (String)get(String.class, "worldName");
        tierOneChunks = (int)get(int.class, "tierOneChunks");
        tierTwoChunks = (int)get(int.class, "tierTwoChunks");
        tierThreeChunks = (int)get(int.class, "tierThreeChunks");
        tierFourChunks = (int)get(int.class, "tierFourChunks");
        tierFiveChunks = (int)get(int.class, "tierFiveChunks");
        earnCoinsMessage = (String)get(String.class, "earnCoinsMessage");
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
