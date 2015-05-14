package co.valdeon.Tribes.storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private String query;
    private Database db;
    private QueryType q;
    private Connection con;
    private ResultSet result;

    public Query() { }

    public Query (QueryType q, Database d, String... s) {
        this.q = q;
        this.db = d;
        this.con = d.getConnection();
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
        if(this.query == null)
            return null;

        if (this.q == QueryType.UPDATE) {
            try {
                this.con.createStatement().executeQuery(this.query);
            }catch(SQLException e) {
                return null;
            }
            return null;
        }

        try {
            this.result = this.con.createStatement().executeQuery(this.query);
        }catch(SQLException e) {
            return null;
        }

        return this.result;
    }

    public Query where(String col, WhereType w, String val) {
        this.query += " " + col + w.val + val;
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
            this.query += s[i].getString() + " ";
            if(!((i + 1) >= s.length))
                this.query += ", ";
        }
        return this;
    }

    public Query columns(String... s) {
        this.query += " (";
        for(int i = 0; i < s.length; i++) {
            this.query += s;
            if(!((i + 1) >= s.length))
                this.query += ", ";
            else
                this.query += ") ";
        }
        return this;
    }

    public Query values(String... s) {
        this.query += " VALUES (";
        for(int i = 0; i < s.length; i++) {
            this.query += s;
            if(!((i + 1) >= s.length))
                this.query += ", ";
            else
                this.query += ") ";
        }
        return this;
    }

}
