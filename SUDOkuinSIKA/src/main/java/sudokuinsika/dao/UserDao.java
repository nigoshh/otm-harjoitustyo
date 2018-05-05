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
     * @return success of the save operation
     * @throws SQLException
     */
    boolean save(User user) throws SQLException;

    /**
     * Updates a user's username and returns true, if the new username
     * isn't already in use. Otherwise it does nothing and returns false.
     *
     * @param oldUsername the old username
     * @param newUsername the new username
     * @return success of the update operation
     * @throws SQLException
     */
    boolean changeUsername(String oldUsername, String newUsername) throws SQLException;

    /**
     * Updates a user's password.
     *
     * @param user the user whose password we're changing
     * @throws SQLException
     */
    void changePasswordData(User user) throws SQLException;

    /**
     * Updates a user's email.
     *
     * @param username the user's username
     * @param newEmail the new email
     * @throws SQLException
     */
    void changeEmail(String username, String newEmail) throws SQLException;

    /**
     * Deletes a user from a file.
     * @param userId the user's ID
     * @throws SQLException
     */
    void delete(int userId) throws SQLException;
}
