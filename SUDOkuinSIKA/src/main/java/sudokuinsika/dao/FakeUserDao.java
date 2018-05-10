package sudokuinsika.dao;

import java.util.HashMap;
import java.util.Map;
import sudokuinsika.domain.User;

/**
 * Mock UserDao implementation to be used in integration testing.
 */
public class FakeUserDao implements UserDao {

    private final Map<String, User> users;

    /**
     * Sole constructor.
     */
    public FakeUserDao() {
        users = new HashMap<>();
    }

    /**
     * Finds a user using the attribute username.
     * If there isn't a user with this username, it returns null.
     *
     * @param username the user's username
     * @return a User or null
     */
    @Override
    public User findOne(String username) {
        return users.getOrDefault(username, null);
    }

    /**
     * Saves a user's data and returns true, if no existing user has the same
     * username; otherwise it doesn't save anything and returns false.
     *
     * @param user the user we are saving
     * @return success of the save operation
     */
    @Override
    public boolean save(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        return true;
    }

    /**
     * Updates a user's username and returns true. If the new username is
     * already taken, it updates nothing and returns false.
     *
     * @param currentUsername the user's current username
     * @param newUsername the new username
     * @return success of the update operation (false if new username is taken)
     */
    @Override
    public boolean changeUsername(String currentUsername, String newUsername) {
        if (users.containsKey(newUsername)) {
            return false;
        }
        users.put(newUsername, users.remove(currentUsername));
        return true;
    }

    /**
     * Updates a user's password data, which is all the information we need for
     * the PBKDF2 key derivation function (hash, salt, iterations and key length).
     *
     * @param user the user whose password data we're updating
     */
    @Override
    public void changePasswordData(User user) {
        User toUpdate = users.get(user.getUsername());
        toUpdate.setPwHash(user.getPwHash());
        toUpdate.setPwSalt(user.getPwSalt());
        toUpdate.setPwIterations(user.getPwIterations());
        toUpdate.setPwKeyLength(user.getPwKeyLength());
    }

    /**
     * Updates a user's email.
     *
     * @param username the user's username
     * @param newEmail the new email
     */
    @Override
    public void changeEmail(String username, String newEmail) {
        users.get(username).setEmail(newEmail);
    }

    /**
     * Deletes a user.
     *
     * @param user the user we are deleting
     */
    @Override
    public void delete(User user) {
        users.remove(user.getUsername());
    }
}
