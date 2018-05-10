package sudokuinsika.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Contains methods to get a connection to an SQL database and to initialize it
 * with CREATE TABLE statements.
 */
public class Database {

    private final String address;
    private final String createTableUser
            = "CREATE TABLE user "
            + "(id integer PRIMARY KEY, "
            + "username varchar(230) UNIQUE, "
            + "pwhash varchar(1000), "
            + "pwsalt blob, "
            + "pwiterations integer, "
            + "pwkeylength integer, "
            + "email varchar(230))";
    private final String createTableScore
            = "CREATE TABLE score "
            + "(user_id integer, "
            + "level integer, "
            + "help boolean, "
            + "score integer, "
            + "time timestamp, "
            + "FOREIGN KEY (user_id) REFERENCES user(id))";

    /**
     * Sole constructor.
     *
     * @param address an SQL database file path
     * @throws ClassNotFoundException if there's no such file at the given path
     */
    public Database(String address) throws ClassNotFoundException {
        this.address = address;
    }

    /**
     * Returns a connection to the SQL database specified in the constructor's parameter.
     *
     * @return a Connection to an SQL database
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address);
    }

    /**
     * Initializes a newly created SQL database using some CREATE TABLE statements.
     *
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public void init() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(createTableUser);
        stmt.executeUpdate(createTableScore);
        stmt.close();
        conn.close();
    }
}
