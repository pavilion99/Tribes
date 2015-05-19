package co.valdeon.Tribes.components;

public enum AbilityType {

    SPEED("SPEED"),
    JUMP("JUMP"),
    REGEN("REGEN"),
    HASTE("HASTE"),
    RESISTANCE("RESISTANCE"),
    STRENGTH("STRENGTH"),
    FIRERESISTANCE("FIRERESISTANCE"),
    HEALTHBOOST("HEALTHBOOST"),
    INVISIBILITY("INVISIBILITY"),
    NIGHTVISION("NIGHTVISION"),
    SATURATION("SATURATION"),
    WATERBREATHING("WATERBREATHING");

    String text;
    int multiplier = 0;

    AbilityType(String s) {
        this.text = s;
    }

    public String getText() {
        return this.text;
    }

    public static AbilityType getAbilityTypeFromString(String s) {
        switch(s) {
            case "SPEED":
                return SPEED;
            case "JUMP":
                return JUMP;
            case "REGEN":
                return REGEN;
            case "HASTE":
                return HASTE;
            case "RESISTANCE":
                return RESISTANCE;
            case "STRENGTH":
                return STRENGTH;
            case "FIRERESISTANCE":
                return FIRERESISTANCE;
            case "WATERBREATHING":
                return WATERBREATHING;
            case "SATURATION":
                return SATURATION;
            case "NIGHTVISION":
                return NIGHTVISION;
            case "INVISIBILITY":
                return INVISIBILITY;
            case "HEALTHBOOST":
                return HEALTHBOOST;
            default:
                return null;
        }
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

}
