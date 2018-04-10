package sudokuinsika.dao;

import java.sql.SQLException;
import sudokuinsika.domain.User;

public interface UserDao {
    User findOne(String username) throws SQLException;
    boolean save(User user) throws SQLException;
}
