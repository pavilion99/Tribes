package co.valdeon.Tribes.storage;

import co.valdeon.Tribes.Tribes;
import co.valdeon.Tribes.util.Config;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class Query implements Closeable {

    private String query;
    private Database db;
    private QueryType q;
    private Connection con;
    private ResultSet result;
    private Statement s;

    public Query (QueryType q, String... s) {
        this.q = q;
        this.db = Tribes.getDB();
        this.con = this.db.getConnection();
        switch(q) {
            case SELECT:
                this.query = q.val;
                this.query += " " + s[0];
                this.query += " FROM ";
                this.query += s[1];
                break;
            case INSERTINTO:
                this.query = q.val;
                this.query += " " + s[0];
                break;
            case UPDATE:
                this.query = q.val;
                this.query += " " + s[0];
                break;
            case DELETE:
                this.query = q.val;
                this.query += " FROM ";
                this.query += s[0];
                break;
            default:
                this.query = null;
                break;
        }
    }

    public Query (QueryType q, String s, Database d) {
        this.db = d;
        this.query = s;
        this.q = q;
    }

    public String getQuery() {
        return this.query;
    }

    public ResultSet query() {
        if(Config.debugQueries) {
            Tribes.log(Level.INFO, this.query);
        }

        if(this.query == null)
            return null;

        if (this.q == QueryType.UPDATE || this.q == QueryType.INSERT || this.q == QueryType.INSERTINTO || this.q == QueryType.DELETE) {
            try {
                this.con.createStatement().executeUpdate(this.query);
            }catch(SQLException e) {
                Tribes.log(Level.INFO, this.query);
                e.printStackTrace();
                return null;
            }
            return null;
        }

        try {
            this.result = this.con.createStatement().executeQuery(this.query);
            return this.result;
        }catch(SQLException e) {
            Tribes.log(Level.INFO, this.query);
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet query(boolean returnKeys) {
        if(Config.debugQueries) {
            Tribes.log(Level.INFO, this.query);
        }

        if(!(this.q == QueryType.UPDATE || this.q == QueryType.INSERT || this.q == QueryType.INSERTINTO || this.q == QueryType.DELETE))
            return null;

        if(this.query == null)
            return null;

        try {
            s = this.con.createStatement();
            s.executeUpdate(this.query);
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            ResultSet h = s.executeQuery("SELECT last_insert_rowid()");
            return returnKeys ? h : this.result;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Query where(String col, WhereType w, String val) {
        this.query += " WHERE " + col + w.val + val;
        return this;
    }

    public Query limit(int i) {
        this.query += " LIMIT " + Integer.toString(i);
        return this;
    }

    public Query order(Order... o) {
        this.query += " ORDER BY ";
        for(Order oi : o) {
            this.query += oi.getLimitedString();
        }
        return this;
    }

    public Query set(Set... s) {
        this.query += " SET ";
        for(int i = 0; i < s.length; i++) {
            this.query += s[i].getString();
            if(!((i + 1) >= s.length))
                this.query += ", ";
        }
        return this;
    }

    public Query columns(String... s) {
        this.query += " (";
        for(int i = 0; i < s.length; i++) {
            this.query += "`" + s[i] + "`";
            if(!((i + 1) >= s.length))
                this.query += ", ";
            else
                this.query += ")";
        }
        return this;
    }

    public Query values(String... s) {
        this.query += " VALUES (";
        for(int i = 0; i < s.length; i++) {
            this.query += s[i];
            if(!((i + 1) >= s.length))
                this.query += ", ";
            else
                this.query += ")";
        }
        return this;
    }

    public void close() {
        try {
            if(this.con != null)
                this.con.close();
            if(this.result != null)
                this.result.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
