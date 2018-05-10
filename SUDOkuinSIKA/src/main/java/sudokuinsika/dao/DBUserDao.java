package sudokuinsika.dao;

import java.sql.*;
import sudokuinsika.domain.User;

/**
 * Contains methods to get User data from an SQL database.
 */
public class DBUserDao implements UserDao {

    private final Database db;

    /**
     * Sole constructor.
     *
     * @param db the database we connect to
     */
    public DBUserDao(Database db) {
        this.db = db;
    }

    /**
     * Finds a user from an SQL database using the attribute username.
     * If there isn't a user with this username, it returns null.
     *
     * @param username the user's username
     * @return a User or null
     * @throws SQLException if an error occurs while accessing an SQL database
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
     * Saves a user's data to an SQL database and returns true, if no existing
     * user has the same username; otherwise it doesn't save anything to the
     * database and returns false.
     *
     * @param user the user we're saving to a database
     * @return success of the save operation
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public boolean save(User user) throws SQLException {
        Connection conn = db.getConnection();
        if (checkIfAlreadyExists(user.getUsername(), conn)) {
            conn.close();
            return false;
        }
        String query = "INSERT INTO user (username, pwhash, pwsalt, pwiterations, pwkeylength, email)"
                + " VALUES (?,?,?,?,?,?)";
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

    /**
     * Updates a user's username in an SQL database entry and returns true.
     * If the new username is already taken, it updates nothing and returns false.
     *
     * @param currentUsername the user's current username
     * @param newUsername the new username
     * @return success of the update operation (false if new username is taken)
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public boolean changeUsername(String currentUsername, String newUsername) throws SQLException {
        Connection conn = db.getConnection();
        if (checkIfAlreadyExists(newUsername, conn)) {
            conn.close();
            return false;
        }
        String query = "UPDATE user SET username = ? WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newUsername);
        stmt.setString(2, currentUsername);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return true;
    }

    /**
     * Updates a user's password data in an SQL database entry. Password data
     * means all the information we need for the PBKDF2 key derivation function
     * (hash, salt, iterations and key length).
     *
     * @param user the user whose password data we're updating
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public void changePasswordData(User user) throws SQLException {
        Connection conn = db.getConnection();
        String query = "UPDATE user SET pwhash = ?, pwsalt = ?, pwiterations = ?, pwkeylength = ?"
                + " WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setBytes(1, user.getPwHash());
        stmt.setBytes(2, user.getPwSalt());
        stmt.setInt(3, user.getPwIterations());
        stmt.setInt(4, user.getPwKeyLength());
        stmt.setString(5, user.getUsername());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Updates a user's email in an SQL database entry.
     *
     * @param username the user's username
     * @param newEmail the new email
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public void changeEmail(String username, String newEmail) throws SQLException {
        Connection conn = db.getConnection();
        String query = "UPDATE user SET email = ? WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newEmail);
        stmt.setString(2, username);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    /**
     * Deletes a user entry from an SQL database.
     *
     * @param user the user we are deleting
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public void delete(User user) throws SQLException {
        Connection conn = db.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM user WHERE id = ?");
        PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM score WHERE user_id = ?");
        stmt1.setInt(1, user.getId());
        stmt2.setInt(1, user.getId());
        int rowAffected = stmt1.executeUpdate();
        if (rowAffected != 1) {
            conn.rollback();
        }
        stmt2.executeUpdate();
        conn.commit();
        stmt1.close();
        stmt2.close();
        conn.close();
    }

    private boolean checkIfAlreadyExists(String username, Connection conn) throws SQLException {
        String query = "SELECT id FROM user WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
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
