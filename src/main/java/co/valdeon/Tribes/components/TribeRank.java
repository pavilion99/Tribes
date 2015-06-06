package co.valdeon.Tribes.components;

public enum TribeRank {

    MEMBER(1, "MEMBER"),
    OFFICER(2, "OFFICER"),
    CHIEF(3, "CHIEF");

    final int power;
    final String name;

    TribeRank(int power, String name) {
        this.power = power;
        this.name = name;
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

    public String getName() {
        return this.name;
    }

    public int getPower() {
        return this.power;
    }

    public static TribeRank getRankFromPower(int i) {
        switch(i) {
            case 1:
                return MEMBER;
            case 2:
                return OFFICER;
            case 3:
                return CHIEF;
            default:
                return MEMBER;
        }
    }

}
