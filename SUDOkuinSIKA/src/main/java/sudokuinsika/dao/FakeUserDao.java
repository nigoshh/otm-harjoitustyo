package sudokuinsika.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import sudokuinsika.domain.User;

public class FakeUserDao implements UserDao {

    public Map<String, User> users;

    public FakeUserDao() {
        users = new HashMap<>();
    }

    @Override
    public User findOne(String username) throws SQLException {
        return users.getOrDefault(username, null);
    }

    @Override
    public boolean save(User user) throws SQLException {
        return users.putIfAbsent(user.getUsername(), user) == null;
    }

    @Override
    public boolean changeUsername(String oldUsername, String newUsername) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changePasswordData(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeEmail(String username, String newEmail) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int userId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
