package tech.spencercolton.tribes.Storage.SQLite;

public enum WhereType {

    EQUALS("="),
    LESS("<"),
    LESS_EQUALS("<="),
    GREATER(">"),
    GREATER_EQUALS(">="),
    NOT_EQUALS("!=");

    final String val;

    WhereType(String val) {
        this.val = val;
    }

}
