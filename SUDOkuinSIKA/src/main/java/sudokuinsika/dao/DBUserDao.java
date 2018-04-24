package sudokuinsika.dao;

import java.sql.*;
import sudokuinsika.domain.User;

public class DBUserDao implements UserDao {

    private Database db;

    public DBUserDao(Database db) {
        this.db = db;
    }

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
