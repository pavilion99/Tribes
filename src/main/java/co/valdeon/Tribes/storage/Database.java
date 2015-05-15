package co.valdeon.Tribes.storage;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Database {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" +Config.dbName + ".db");
        }catch(SQLException e) {
            return null;
        }
    }

    public static boolean verifyDBConnection() {
        try {
            DriverManager.getConnection("jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" + Config.dbName + ".db");
            Tribes.log(Level.INFO, "Connected to database at jdbc:sqlite:" + Tribes.getDataDir().toString() + "\\" + Config.dbName + ".db");
        }catch(SQLException e) {
            Tribes.log(Level.SEVERE, "Failed to connect to database!");
            return false;
        }

        return true;
    }

}
