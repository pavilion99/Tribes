package co.valdeon.Tribes.components;

public enum TribeRank {

    MEMBER(1),
    OFFICER(2),
    CHIEF(3);

    int power;

    TribeRank(int power) {
        this.power = power;
    }

}
