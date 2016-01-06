package tech.spencercolton.tribes.Storage.SQLite;

public class Set {

    private final String column;
    private final String value;

    public Set(String s, String t) {
        this.column = s;
        this.value = t;
    }

    public String getString() {
        return "`" + this.column + "`" + "=" + this.value;
    }

}
