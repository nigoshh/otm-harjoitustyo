package sudokuinsika.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import sudokuinsika.domain.User;

public class FakeUserDao implements UserDao {

    private final Map<String, User> users;

    public FakeUserDao() {
        users = new HashMap<>();
    }

    @Override
    public User findOne(String username) throws SQLException {
        return users.getOrDefault(username, null);
    }

    @Override
    public boolean save(User user) throws SQLException {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }

    @Override
    public boolean changeUsername(String oldUsername, String newUsername) throws SQLException {
        if (users.containsKey(newUsername)) {
            return false;
        }
        users.put(newUsername, users.remove(oldUsername));
        return true;
    }

    @Override
    public void changePasswordData(User user) throws SQLException {
        User toUpdate = users.get(user.getUsername());
        toUpdate.setPwHash(user.getPwHash());
        toUpdate.setPwSalt(user.getPwSalt());
        toUpdate.setPwIterations(user.getPwIterations());
        toUpdate.setPwKeyLength(user.getPwKeyLength());
    }

    @Override
    public void changeEmail(String username, String newEmail) throws SQLException {
        users.get(username).setEmail(newEmail);
    }

    @Override
    public void delete(User user) throws SQLException {
        users.remove(user.getUsername());
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
