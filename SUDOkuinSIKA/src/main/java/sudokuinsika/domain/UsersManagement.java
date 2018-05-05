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
 * Manages users, creates games for them and manages their scores
 */
public class UsersManagement {

    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private User loggedInUser;
    private Game game;

    private int pwIterations = 100000;

    private final int pwKeyLength = 512;

    private final int pwSaltLength = 64;

    public UsersManagement(UserDao userDao, ScoreDao scoreDao) {
        this.userDao = userDao;
        this.scoreDao = scoreDao;
        this.game = null;
    }

    private byte[] hashPassword(final char[] password, final byte[] salt,
            final int iterations, final int keyLength) {

        try {
            SecretKeyFactory skf
                    = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec
                    = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return res;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * If a User with the given username doesn't already exist in userDao,
     * creates a new User, saves it to userDao, and returns true.
     * Otherwise returns false.
     *
     * @param username the User's username
     * @param password the User's password
     * @param email the User's email address
     * @return success of the User creating operation
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    public boolean createUser(String username, char[] password, String email)
            throws SQLException, NoSuchAlgorithmException {
        User user = new User(username);
        setPasswordData(user, password);
        user.setEmail(email);
        return userDao.save(user);
    }

    public boolean checkUsernameLength(String username) {
        return username.length() > 0 && username.length() <= 230;
    }

    public boolean checkPasswordLength(String password) {
        return password.length() > 9 && password.length() <= 1000;
    }

    public boolean checkEmailLength(String email) {
        return email.length() <= 230;
    }

    public boolean changeUsername(String newUsername) throws SQLException {
        if (userDao.changeUsername(loggedInUser.getUsername(), newUsername)) {
            loggedInUser.setUsername(newUsername);
            return true;
        }
        return false;
    }

    public void changePassword(char[] password)
            throws NoSuchAlgorithmException, SQLException {
        setPasswordData(loggedInUser, password);
        userDao.changePasswordData(loggedInUser);
    }

    public void changeEmail(String newEmail) throws SQLException {
        userDao.changeEmail(loggedInUser.getUsername(), newEmail);
        loggedInUser.setEmail(newEmail);
    }

    public void deleteUser() throws SQLException {
        userDao.delete(loggedInUser.getId());
        logOut();
    }

    /**
     * If username and password match with those of an existing User, logs in
     * the User and returns a Game; otherwise returns null.
     *
     * @param username the User's username
     * @param password the User's password
     * @return a new Game, if username and password match with those
     * of an existing User; otherwise null
     * @throws SQLException
     */
    public Game logIn(String username, char[] password) throws SQLException {

        loggedInUser = userDao.findOne(username);
        if (loggedInUser == null) {
            wipeSensitiveData(password);
            return null;
        }
        if (checkPassword(password)) {
            game = new Game(this);
            return game;
        }
        return null;
    }

    /**
     * Logs out a User (loggedInUser)
     */
    public void logOut() {
        game = null;
        loggedInUser = null;
    }

    public boolean checkPassword(char[] password) {
        byte[] pwHash = hashPassword(password, loggedInUser.getPwSalt(),
                loggedInUser.getPwIterations(), loggedInUser.getPwKeyLength());
        wipeSensitiveData(password);
        return Arrays.equals(pwHash, loggedInUser.getPwHash());
    }

    private void setPasswordData(User user, char[] password) throws NoSuchAlgorithmException {
        byte[] pwSalt = getSalt();
        byte[] pwHash = hashPassword(password, pwSalt, pwIterations, pwKeyLength);
        wipeSensitiveData(password);
        user.setPwHash(pwHash);
        user.setPwSalt(pwSalt);
        user.setPwIterations(pwIterations);
        user.setPwKeyLength(pwKeyLength);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public ScoreDao getScoreDao() {
        return scoreDao;
    }

    public void setPwIterations(int pwIterations) {
        this.pwIterations = pwIterations;
    }

    public Game getGame() {
        return game;
    }
}
