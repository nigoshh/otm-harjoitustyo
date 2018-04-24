package sudokuinsika.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String address;
    private final String createTableUser
            = "CREATE TABLE user "
            + "(id integer primary key, "
            + "username varchar(230), "
            + "pwhash varchar(1000), "
            + "pwsalt blob, "
            + "pwiterations integer, "
            + "pwkeylength integer, "
            + "email varchar(230))";
    private final String createTableScore
            = "CREATE TABLE score "
            + "(user_id integer, "
            + "score integer, "
            + "time timestamp, "
            + "FOREIGN KEY (user_id) REFERENCES user(id))";

    public Database(String address) throws ClassNotFoundException {
        this.address = address;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }

    public void init() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(createTableUser);
        stmt.executeUpdate(createTableScore);
        stmt.close();
        conn.close();
    }
}
