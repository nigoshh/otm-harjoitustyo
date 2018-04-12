package sudokuinsika.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String address;

    public Database(String address) throws ClassNotFoundException {
        this.address = address;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }

    public void init() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        String user = "CREATE TABLE user "
                + "(id integer primary key, "
                + "username varchar(230), "
                + "pwhash varchar(230), "
                + "email varchar(230))";
        stmt.executeUpdate(user);
        String score = "CREATE TABLE score "
                + "(id integer primary key, "
                + "user_id integer, "
                + "score integer, "
                + "time timestamp)";
        stmt.executeUpdate(score);
        stmt.close();
        conn.close();
    }
}
