package co.valdeon.Tribes.components;

public enum TribeRank {

    MEMBER(1, "MEMBER"),
    OFFICER(2, "OFFICER"),
    CHIEF(3, "CHIEF");

    int power;
    String name;

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

}
