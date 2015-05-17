package co.valdeon.Tribes.components;

import co.valdeon.Tribes.util.Config;

public enum TribeTier {

    TIER_ONE(1, Config.tierOneChunks, "ONE"),
    TIER_TWO(2, Config.tierTwoChunks, "TWO"),
    TIER_THREE(3, Config.tierThreeChunks, "THREE"),
    TIER_FOUR(4, Config.tierFourChunks, "FOUR"),
    TIER_FIVE(5, Config.tierFiveChunks, "FIVE");

    public int tier, chunks;
    public String tierString;

    TribeTier(int tier, int chunks, String tierString) {
        this.tier = tier;
        this.chunks = chunks;
        this.tierString = tierString;
    }

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

}