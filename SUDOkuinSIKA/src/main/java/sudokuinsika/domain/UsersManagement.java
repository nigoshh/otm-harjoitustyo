package sudokuinsika.domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import sudokuinsika.dao.ScoreDao;
import sudokuinsika.dao.UserDao;

/**
 * Manages Users, creates Games for them and manages their Scores.
 */
public class UsersManagement {

    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private User loggedInUser;
    private Game game;

    private int pwIterations = 100000;
    private final int pwKeyLength = 512;
    private final int pwSaltLength = 64;

    /**
     * Sole constructor.
     *
     * @param userDao a DAO object to access user data
     * @param scoreDao a DAO object to access score data
     */
    public UsersManagement(UserDao userDao, ScoreDao scoreDao) {
        this.userDao = userDao;
        this.scoreDao = scoreDao;
    }

    /**
     * If no user in userDao already has the same username this creates a new user,
     * saves her data to userDao, and returns true. Otherwise it returns false.
     *
     * @param username the user's username
     * @param password the user's password
     * @param email the user's email address
     * @return success of create user operation
     * @throws NoSuchAlgorithmException if the requested cryptographic algorithm
     * is not available in the environment
     * @throws InvalidKeySpecException for invalid key specifications
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public boolean createUser(String username, char[] password, String email)
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        User user = new User(username);
        setPasswordData(user, password);
        user.setEmail(email);
        return userDao.save(user);
    }

    /**
     * Checks if the given username's length is between 1 and 230 characters.
     *
     * @param username a string representing a username
     * @return true if username's length is between 1 and 230 characters
     */
    public boolean checkUsernameLength(String username) {
        return username.length() > 0 && username.length() <= 230;
    }

    /**
     * Checks if the given password's length is between 1 and 1000 characters.
     *
     * @param password a string representing a password
     * @return true if password's length is between 1 and 1000 characters
     */
    public boolean checkPasswordLength(String password) {
        return password.length() > 9 && password.length() <= 1000;
    }

    /**
     * Checks if the given email's length is no more than 230 characters.
     *
     * @param email a string representing an email address
     * @return true if email's length is no more than 230 characters
     */
    public boolean checkEmailLength(String email) {
        return email.length() <= 230;
    }

    /**
     * Checks if the input password is the {@link #loggedInUser}'s.
     *
     * @param password the password we are checking
     * @return true if the password is the {@link #loggedInUser}'s
     * @throws NoSuchAlgorithmException if the requested cryptographic algorithm
     * is not available in the environment
     * @throws InvalidKeySpecException for invalid key specifications
     */
    public boolean checkPassword(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] pwHash = hashPassword(password, loggedInUser.getPwSalt(),
                loggedInUser.getPwIterations(), loggedInUser.getPwKeyLength());
        wipeSensitiveData(password);
        return Arrays.equals(pwHash, loggedInUser.getPwHash());
    }

    /**
     * If the new username isn't already taken, this updates a user's username
     * and returns true. Otherwise it returns false.
     *
     * @param newUsername the new username
     * @return success of the update operation (false if new username is taken)
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public boolean changeUsername(String newUsername) throws SQLException {
        if (userDao.changeUsername(loggedInUser.getUsername(), newUsername)) {
            loggedInUser.setUsername(newUsername);
            return true;
        }
        return false;
    }

    /**
     * Updates a user's password.
     *
     * @param password the new password
     * @throws NoSuchAlgorithmException if the requested cryptographic algorithm
     * is not available in the environment
     * @throws InvalidKeySpecException for invalid key specifications
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public void changePassword(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        setPasswordData(loggedInUser, password);
        userDao.changePasswordData(loggedInUser);
    }

    /**
     * Updates a user's email address.
     *
     * @param newEmail the new email address
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public void changeEmail(String newEmail) throws SQLException {
        userDao.changeEmail(loggedInUser.getUsername(), newEmail);
        loggedInUser.setEmail(newEmail);
    }

    /**
     * Deletes a user and all of her data, including her scores.
     *
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public void deleteUser() throws SQLException {
        userDao.delete(loggedInUser);
        logOut();
    }

    /**
     * If username and password match with those of an existing user, this logs
     * in the user and returns a game; otherwise it returns null.
     *
     * @param username the user's username
     * @param password the user's password
     * @return a new Game, if username and password match with those of an
     * existing User; otherwise null
     * @throws SQLException if an error occurs while accessing an SQL database
     * @throws NoSuchAlgorithmException if the requested cryptographic algorithm
     * is not available in the environment
     * @throws InvalidKeySpecException for invalid key specifications
     */
    public Game logIn(String username, char[] password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        loggedInUser = userDao.findOne(username);
        if (loggedInUser == null) {
            wipeSensitiveData(password);
            return null;
        }
        if (checkPassword(password)) {
            game = new Game();
            return game;
        }
        return null;
    }

    /**
     * Logs out {@link #loggedInUser}.
     */
    public void logOut() {
        game = null;
        loggedInUser = null;
    }

    /**
     * Checks if the game's sudoku puzzle is solved, in which case it saves the
     * game's score and returns true. The game's score is the time elapsed from
     * the puzzle creation until all cells are filled correctly.
     *
     * @return true if the game is solved, false otherwise
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public boolean sudoKuinSika() throws SQLException {
        Score score = game.getScore();
        if (score == null) {
            return false;
        }
        score.setUser(loggedInUser);
        scoreDao.save(score);
        return true;
    }

    private void setPasswordData(User user, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] pwSalt = getSalt();
        byte[] pwHash = hashPassword(password, pwSalt, pwIterations, pwKeyLength);
        wipeSensitiveData(password);
        user.setPwHash(pwHash);
        user.setPwSalt(pwSalt);
        user.setPwIterations(pwIterations);
        user.setPwKeyLength(pwKeyLength);
    }

    private byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKey key = skf.generateSecret(spec);
        return key.getEncoded();
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[pwSaltLength];
        sr.nextBytes(salt);
        return salt;
    }

    private void wipeSensitiveData(char[] data) {
        for (char c : data) {
            c = '*';
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ScoreDao getScoreDao() {
        return scoreDao;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Game getGame() {
        return game;
    }

    public void setPwIterations(int pwIterations) {
        this.pwIterations = pwIterations;
    }
}
