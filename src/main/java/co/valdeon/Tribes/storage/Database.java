package co.valdeon.Tribes.storage;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Database {

    public List<Table> tables = new ArrayList<>();

    private Tribes tribes;

    private Connection con;

    public Database(Tribes tribes) {
        this.tribes = tribes;
        this.con = getConnection();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + Config.dbName + ".db");
        }catch(SQLException e) {
            return null;
        }
    }

    public static boolean verifyDBConnection() {
        try {
            DriverManager.getConnection("jdbc:sqlite:Tribes.db");
        }catch(SQLException e) {
            Tribes.log(Level.INFO, "Failed to connect to database!");
            return false;
        }

        return true;
    }

    public static Table getTable(String s) {
        // TODO: Figure out how JDBC works so that I can actually give a table back
        return null;
    }

}
