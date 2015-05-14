package co.valdeon.Tribes.storage;

/**
 * Created by oh17colts on 5/14/15.
 */
public enum WhereType {

    EQUALS("="),
    LESS("<"),
    LESS_EQUALS("<="),
    GREATER(">"),
    GREATER_EQUALS(">="),
    NOT_EQUALS("!=");


    String val;

    WhereType(String val) {
        this.val = val;
    }


    }
