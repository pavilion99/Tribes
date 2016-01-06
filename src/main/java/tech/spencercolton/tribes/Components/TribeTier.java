package tech.spencercolton.tribes.Components;

import tech.spencercolton.tribes.Util.Config;

public enum TribeTier {

    TIER_ONE(1, Config.tierOneChunks, "ONE"),
    TIER_TWO(2, Config.tierTwoChunks, "TWO"),
    TIER_THREE(3, Config.tierThreeChunks, "THREE"),
    TIER_FOUR(4, Config.tierFourChunks, "FOUR"),
    TIER_FIVE(5, Config.tierFiveChunks, "FIVE");

    private final int tier;
    private final int chunks;
    public final String tierString;

    TribeTier(int tier, int chunks, String tierString) {
        this.tier = tier;
        this.chunks = chunks;
        this.tierString = tierString;
    }

    public int getValue() {
        return this.tier;
    }

    @SuppressWarnings("unused")
    public static TribeTier getTier(int i) {
        switch(i) {
            case 1:
                return TIER_ONE;
            case 2:
                return TIER_TWO;
            case 3:
                return TIER_THREE;
            case 4:
                return TIER_FOUR;
            case 5:
                return TIER_FIVE;
            default:
                return null;
        }
    }

    public static TribeTier getTier(String s) {
        if(s == null)
            return null;

        switch(s) {
            case "1":
                return TIER_ONE;
            case "2":
                return TIER_TWO;
            case "3":
                return TIER_THREE;
            case "4":
                return TIER_FOUR;
            case "5":
                return TIER_FIVE;
            case "ONE":
                return TIER_ONE;
            case "TWO":
                return TIER_TWO;
            case "THREE":
                return TIER_THREE;
            case "FOUR":
                return TIER_FOUR;
            case "FIVE":
                return TIER_FIVE;
            default:
                return null;
        }
    }

    @SuppressWarnings("unused")
    public static TribeTier getTierFromCoins(int coins) {
        if(coins >= 0 && coins <= 2)
            return TIER_ONE;
        else if (coins > 2 && coins <= 4)
            return TIER_TWO;
        else if(coins > 4 && coins <= 6)
            return TIER_THREE;
        else if(coins > 6 && coins <= 8)
            return TIER_FOUR;
        else if(coins > 8)
            return TIER_FIVE;
        else
            return TIER_ONE;
    }

    public int getChunks() {
        return this.chunks;
    }

}
