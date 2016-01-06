package tech.spencercolton.tribes.Storage.SQLite;

@SuppressWarnings("unused")
public enum QueryType {

    SELECT("SELECT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    DROP("DROP"),
    INSERTINTO("INSERT INTO"),
    INSERT("INSERT");

    final String val;

    QueryType(String s) {
        this.val = s;
    }

}
