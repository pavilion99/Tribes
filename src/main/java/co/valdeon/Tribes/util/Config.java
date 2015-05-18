package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;

public class Config {

    private final Tribes tribes;

    public static String dbName;
    public static String world;

    public static int tierOneChunks;
    public static int tierTwoChunks;
    public static int tierThreeChunks;
    public static int tierFourChunks;
    public static int tierFiveChunks;

    public static int kickPower;

    public static String colorOne;
    public static String colorTwo;
    public static String errorColor;

    public static String earnCoinsMessage;
    public static String consoleExecutionFailure;
    public static String invalidArgs;
    public static String invalidSubargs;
    public static String join;
    public static String create;
    public static String invite;
    public static String coins;
    public static String destroy;
    public static String inviteSender;
    public static String noExist;
    public static String inExistingTribe;
    public static String notInTribe;
    public static String notChief;
    public static String noPlayer;
    public static String noPower;
    public static String alreadyOwned;
    public static String ownedByOtherTribe;
    public static String needMorePower;
    public static String playerNotInTribe;
    public static String playerHigherRank;
    public static String kicked;
    public static String kickedKickee;

    public static boolean chatFeatures;

    public Config(Tribes tribes) {
        this.tribes = tribes;
        consoleExecutionFailure = (String)get(String.class, "consoleExecutionFailure");
        invalidArgs = (String)get(String.class, "invalidArgs");
        invalidSubargs = (String)get(String.class, "invalidSubargs");
        dbName = (String)get(String.class, "db");
        world = (String)get(String.class, "worldName");
        tierOneChunks = (int)get(int.class, "tierOneChunks");
        tierTwoChunks = (int)get(int.class, "tierTwoChunks");
        tierThreeChunks = (int)get(int.class, "tierThreeChunks");
        tierFourChunks = (int)get(int.class, "tierFourChunks");
        tierFiveChunks = (int)get(int.class, "tierFiveChunks");
        earnCoinsMessage = (String)get(String.class, "earnCoinsMessage");
        colorOne = "&" + get(String.class, "colorOne");
        colorTwo = "&" + get(String.class, "colorTwo");
        errorColor = (String)get(String.class, "errorColor");
        join = (String)get(String.class, "join");
        create = (String)get(String.class, "create");
        invite = (String)get(String.class, "invite");
        coins = (String)get(String.class, "coins");
        inviteSender = (String)get(String.class, "inviteSender");
        noExist = (String)get(String.class, "noExist");
        inExistingTribe = (String)get(String.class, "inExistingTribe");
        notInTribe = (String)get(String.class, "notInTribe");
        notChief = (String)get(String.class, "notChief");
        destroy = (String)get(String.class, "destroy");
        noPlayer = (String)get(String.class, "noPlayer");
        noPower = (String)get(String.class, "noPower");
        alreadyOwned = (String)get(String.class, "alreadyOwned");
        ownedByOtherTribe = (String)get(String.class, "ownedByOtherTribe");
        kickPower = (int)get(int.class, "kickPower");
        needMorePower = (String)get(String.class, "needMorePower");
        playerNotInTribe = (String)get(String.class, "playerNotInTribe");
        playerHigherRank = (String)get(String.class, "playerHigherRank");
        kicked = (String)get(String.class, "kicked");
        kickedKickee = (String)get(String.class, "kickedKickee");
        chatFeatures = (boolean)get(boolean.class, "chatFeatures");
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
