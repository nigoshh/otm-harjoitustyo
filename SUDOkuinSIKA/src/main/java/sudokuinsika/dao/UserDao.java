package sudokuinsika.dao;

import java.sql.SQLException;
import sudokuinsika.domain.User;

public interface UserDao {
    User findOne(String username) throws SQLException;
    void save(User user) throws SQLException;
}
