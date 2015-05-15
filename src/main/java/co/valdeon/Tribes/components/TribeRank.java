package co.valdeon.Tribes.components;

public enum TribeRank {

    MEMBER(1),
    OFFICER(2),
    CHIEF(3);

    int power;

    TribeRank(int power) {
        this.power = power;
    }

    public static TribeRank getRank(String s) {
        switch(s) {
            case "MEMBER":
                return MEMBER;
            case "OFFICER":
                return OFFICER;
            case "CHIEF":
                return CHIEF;
            default:
                return null;
        }
    }

}
