package tech.spencercolton.tribes.Util;

import lombok.Getter;

@SuppressWarnings("unused")
public enum ReloadType {

    NORMAL("normal"),
    DISABLE_AUTOSAVE("disableAutosave"),
    LOAD_DATABASE("loadDatabase");

    ReloadType(String s) {
        this.text = s;
    }

    @Getter
    private final String text;

    @SuppressWarnings("unused")
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

}
