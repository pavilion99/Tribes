package co.valdeon.Tribes.util;

import co.valdeon.Tribes.Tribes;

import java.util.HashMap;

public class Config {

    private static Tribes tribes;

    public static String dbName;
    public static String world;

    public static int tierOneChunks;
    public static int tierTwoChunks;
    public static int tierThreeChunks;
    public static int tierFourChunks;
    public static int tierFiveChunks;

    public static int kickPower;

    public static int coinPrice;

    public static int saveFrequency;

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
    public static String listTribes;
    public static String tooManyPages;
    public static String nonZero;
    public static String moreCoins;
    public static String noMoreLand;
    public static String fullyUpgraded;
    public static String teleportHome;
    public static String setHome;
    public static String header;
    public static String footer;
    public static String upgraded;
    public static String oneRequired;
    public static String lowBalance;
    public static String transactionError;
    public static String buyCoins;
    public static String chiefLeave;
    public static String leave;
    public static String listMembers;
    public static String promoter;
    public static String promotee;
    public static String oneChief;
    public static String buyAbility;

    public static boolean chatFeatures;

    public Config(Tribes tribess) {
        tribes = tribess;
    }

    public void init() {
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
        listTribes = (String)get(String.class, "listTribes");
        tooManyPages = (String)get(String.class, "tooManyPages");
        nonZero = (String)get(String.class, "nonZero");
        moreCoins = (String)get(String.class, "moreCoins");
        noMoreLand = (String)get(String.class, "noMoreLand");
        fullyUpgraded = (String)get(String.class, "fullyUpgraded");
        teleportHome = (String)get(String.class, "teleportHome");
        setHome = (String)get(String.class, "setHome");
        header = (String)get(String.class, "header");
        footer = (String)get(String.class, "footer");
        upgraded = (String)get(String.class, "upgraded");
        coinPrice = (int)get(int.class, "coinPrice");
        oneRequired = (String)get(String.class, "oneRequired");
        lowBalance = (String)get(String.class, "lowBalance");
        transactionError = (String)get(String.class, "transactionError");
        buyCoins = (String)get(String.class, "buyCoins");
        chiefLeave = (String)get(String.class, "chiefLeave");
        leave = (String)get(String.class, "leave");
        listMembers = (String)get(String.class, "listMembers");
        promoter = (String)get(String.class, "promoter");
        promotee = (String)get(String.class, "promotee");
        oneChief = (String)get(String.class, "oneChief");
        saveFrequency = (int)get(int.class, "saveFrequency");
        buyAbility = (String)get(String.class, "buyAbility");
    }

    public static Object get(Class c, String path) {
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

    public static class Prices {

        public static int fireResistance, haste, healthBoost, invisibility, jump, nightVision, regen, resistance, saturation, speed, strength, waterBreathing;

        private static HashMap<String, Integer> priceMap = new HashMap<>();

        public void init() {
            fireResistance = (int)get(int.class, "fireResistance");
            haste = (int)get(int.class, "haste");
            healthBoost = (int)get(int.class, "healthBoost");
            invisibility = (int)get(int.class, "invisibility");
            jump = (int)get(int.class, "jump");
            nightVision = (int)get(int.class, "nightVision");
            regen = (int)get(int.class, "regen");
            resistance = (int)get(int.class, "resistance");
            saturation = (int)get(int.class, "saturation");
            speed = (int)get(int.class, "speed");
            strength = (int)get(int.class, "strength");
            waterBreathing = (int)get(int.class, "waterBreathing");

            priceMap.put("fireresistance", fireResistance);
            priceMap.put("haste", haste);
            priceMap.put("healthboost", healthBoost);
            priceMap.put("invisibility", invisibility);
            priceMap.put("jump", jump);
            priceMap.put("nightvision", nightVision);
            priceMap.put("regen", regen);
            priceMap.put("resistance", resistance);
            priceMap.put("saturation", saturation);
            priceMap.put("speed", speed);
            priceMap.put("strength", strength);
            priceMap.put("waterbreathing", waterBreathing);
        }

        public static HashMap<String, Integer> getPriceMap() {
            return priceMap;
        }

    }

}
