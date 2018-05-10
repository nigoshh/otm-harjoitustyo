package sudokuinsika.dao;

import java.sql.SQLException;
import sudokuinsika.domain.User;

/**
 * Contains methods to get User data from a file.
 */
public interface UserDao {

    /**
     * Finds a user from a file using the attribute username.
     * If there isn't a user with this username, it returns null.
     *
     * @param username the user's username
     * @return a User or null
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    User findOne(String username) throws SQLException;

    /**
     * Saves a user's data to a file and returns true, if no existing user has
     * the same username; otherwise it doesn't save anything to the file and
     * returns false.
     *
     * @param user the user we are saving to a file
     * @return success of the save operation
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    boolean save(User user) throws SQLException;

    /**
     * Updates a user's username and returns true. If the new username is
     * already taken, it updates nothing and returns false.
     *
     * @param currentUsername the user's current username
     * @param newUsername the new username
     * @return success of the update operation (false if new username is taken)
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    boolean changeUsername(String currentUsername, String newUsername) throws SQLException;

    /**
     * Updates a user's password data, which is all the information we need for
     * the PBKDF2 key derivation function (hash, salt, iterations and key length).
     *
     * @param user the user whose password data we're updating
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    void changePasswordData(User user) throws SQLException;

    /**
     * Updates a user's email.
     *
     * @param username the user's username
     * @param newEmail the new email
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    void changeEmail(String username, String newEmail) throws SQLException;

    /**
     * Deletes a user from a file.
     *
     * @param user the user we are deleting
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    void delete(User user) throws SQLException;
}
