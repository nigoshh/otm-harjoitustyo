package sudokuinsika.dao;

import java.sql.SQLException;
import sudokuinsika.domain.User;

public class FakeUserDao implements UserDao {
    private User user;

    public FakeUserDao() {
        user = new User("test", "test", "test");
    }

    @Override
    public User findOne(String username) throws SQLException {
        if (username.equals(user.getUsername())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean save(User user) throws SQLException {
        if (user.getUsername().equals(this.user.getUsername())) {
            return false;
        }
        return true;
    }

}
