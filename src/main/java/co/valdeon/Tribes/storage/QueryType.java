package co.valdeon.Tribes.storage;

public enum QueryType {

    SELECT("SELECT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    DROP("DROP"),
    INSERTINTO("INSERT INTO"),
    INSERT("INSERT");

    String val;

    QueryType(String s) {
        this.val = s;
    }

}
