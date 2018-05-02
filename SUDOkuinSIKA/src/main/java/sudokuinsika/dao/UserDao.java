package sudokuinsika.dao;

import java.sql.SQLException;
import sudokuinsika.domain.User;

/**
 * Contains methods to get User data from a file
 */
public interface UserDao {

    /**
     * Finds a user from a file using the attribute username.
     * If there isn't a user with that username, it returns null.
     *
     * @param username the user's username
     * @return a User or null
     * @throws SQLException
     */
    User findOne(String username) throws SQLException;

    /**
     * Saves a user's data to a file and returns true,
     * if no existing user has the same username; otherwise it doesn't save
     * anything to the file and returns false.
     *
     * @param user the user we are saving
     * @return success of the saving operation
     * @throws SQLException
     */
    boolean save(User user) throws SQLException;
}
