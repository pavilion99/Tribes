package co.valdeon.Tribes.components;

public enum AbilityType {

    SPEED("SPEED");

    String text;

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
            default:
                return null;
        }
    }

}
