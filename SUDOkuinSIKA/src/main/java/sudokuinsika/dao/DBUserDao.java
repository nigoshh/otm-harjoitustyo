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
        String query = "SELECT pwhash, email FROM user WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        user.setPwHash(rs.getString("pwhash"));
        user.setEmail(rs.getString("email"));

        rs.close();
        stmt.close();
        conn.close();

        return user;
    }

    @Override
    public void save(User user) throws SQLException {
        Connection conn = db.getConnection();
        String comm = "INSERT INTO user (username, pwhash, email) "
                + "VALUES (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(comm);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPwHash());
        stmt.setString(3, user.getEmail());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

}
