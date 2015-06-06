package co.valdeon.Tribes.util;

public enum ReloadType {

    NORMAL("normal"),
    DISABLE_AUTOSAVE("disableAutosave"),
    LOAD_DATABASE("loadDatabase");

    ReloadType(String s) {
        this.s = s;
    }

    private final String s;

    public static ReloadType getTypeFromString(String s) {
        String string = s.toUpperCase();

        switch(string) {
            case "DISABLEAUTOSAVE":
                return DISABLE_AUTOSAVE;
            case "LOADDATABASE":
                return LOAD_DATABASE;
            case "NORMAL":
                return NORMAL;
            default:
                return null;
        }
    }

    public String getText() {
        return this.s;
    }

}
