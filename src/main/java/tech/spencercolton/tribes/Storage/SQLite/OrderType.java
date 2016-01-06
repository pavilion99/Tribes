package tech.spencercolton.tribes.Storage.SQLite;

@SuppressWarnings("unused")
public enum OrderType {

    ASC("ASC"),
    DESC("DESC");

    final String val;

    OrderType(String val) {
        this.val = val;
    }

}
