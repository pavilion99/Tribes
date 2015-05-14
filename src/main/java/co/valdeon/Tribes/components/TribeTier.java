package co.valdeon.Tribes.components;

public enum TribeTier {

    TIER_ONE(1, 25),
    TIER_TWO(2, 50),
    TIER_THREE(3, 75),
    TIER_FOUR(4, 100),
    TIER_FIVE(5, 125);

    int tier, chunks;

    TribeTier(int tier, int chunks) {
        this.tier = tier;
        this.chunks = chunks;
    }

}
