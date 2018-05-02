package sudokuinsika.dao;

import java.sql.*;
import sudokuinsika.domain.User;

/**
 * Contains methods to get User data from userdata.db
 */
public class DBUserDao implements UserDao {

    private Database db;

    public DBUserDao(Database db) {
        this.db = db;
    }

    /**
     * Finds a user from a SQL database file using the attribute username.
     * If there isn't a user with that username, it returns null.
     *
     * @param username the user's username
     * @return a User or null
     * @throws SQLException
     */
    @Override
    public User findOne(String username) throws SQLException {
        User user = new User(username);
        Connection conn = db.getConnection();
        String query = "SELECT * FROM user WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            rs.close();
            conn.close();
            return null;
        }
        setUserData(user, rs);
        rs.close();
        stmt.close();
        conn.close();
        return user;
    }

    /**
     * Saves a User's data to a SQL database file and returns true,
     * if no existing user has the same username; otherwise it doesn't save
     * anything to the database file and returns false.
     *
     * @param user the User to save into the database file
     * @return success of the saving operation
     * @throws SQLException
     */
    @Override
    public boolean save(User user) throws SQLException {
        Connection conn = db.getConnection();
        if (checkIfAlreadyExists(user, conn)) {
            conn.close();
            return false;
        }
        String query = "INSERT INTO user (username, pwhash, pwsalt, pwiterations,"
                + " pwkeylength, email) VALUES (?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.getUsername());
        stmt.setBytes(2, user.getPwHash());
        stmt.setBytes(3, user.getPwSalt());
        stmt.setInt(4, user.getPwIterations());
        stmt.setInt(5, user.getPwKeyLength());
        stmt.setString(6, user.getEmail());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return true;
    }

    private boolean checkIfAlreadyExists(User user, Connection conn)
            throws SQLException {
        String query = "SELECT id FROM user WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.getUsername());

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        rs.close();
        stmt.close();
        return hasOne;
    }

    private void setUserData(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setPwHash(rs.getBytes("pwhash"));
        user.setPwSalt(rs.getBytes("pwsalt"));
        user.setPwIterations(rs.getInt("pwiterations"));
        user.setPwKeyLength(rs.getInt("pwkeylength"));
        user.setEmail(rs.getString("email"));
    }
}
