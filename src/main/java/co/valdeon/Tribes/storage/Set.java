package co.valdeon.Tribes.storage;

public class Set {

    private String column;
    private String value;

    public Set(String s, String t) {
        this.column = s;
        this.value = t;
    }

    public String getString() {
        return "`" + this.column + "`" + "=" + this.value;
    }

}
