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

}
