package co.valdeon.Tribes.storage;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.util.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Database {

    private Connection con;

    public Database() {
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
            DriverManager.getConnection("jdbc:sqlite:" + Config.dbName + ".db");
        }catch(SQLException e) {
            Tribes.log(Level.SEVERE, "Failed to connect to database!");
            return false;
        }

        return true;
    }

}
