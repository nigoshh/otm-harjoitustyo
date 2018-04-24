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

public class UsersManagement {

    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private User loggedInUser;

    private int pwIterations = 100000;

    private final int pwKeyLength = 512;

    private final int pwSaltLength = 64;

    public UsersManagement(UserDao userDao, ScoreDao scoreDao) {
        this.userDao = userDao;
        this.scoreDao = scoreDao;
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

    public boolean createUser(String username, char[] password, String email)
            throws SQLException, NoSuchAlgorithmException {

        byte[] pwSalt = getSalt();
        byte[] pwHash =
                hashPassword(password, pwSalt, pwIterations, pwKeyLength);
        User user = new User(username);
        wipeSensitiveData(password);
        user.setPwHash(pwHash);
        user.setPwSalt(pwSalt);
        user.setPwIterations(pwIterations);
        user.setPwKeyLength(pwKeyLength);
        user.setEmail(email);
        return userDao.save(user);
    }

    public Game logIn(String username, char[] password) throws SQLException {

        loggedInUser = userDao.findOne(username);
        if (loggedInUser == null) {
            wipeSensitiveData(password);
            return null;
        }
        byte[] pwHash = hashPassword(password, loggedInUser.getPwSalt(),
                loggedInUser.getPwIterations(), loggedInUser.getPwKeyLength());
        wipeSensitiveData(password);
        if (Arrays.equals(pwHash, loggedInUser.getPwHash())) {
            return new Game(this);
        }
        return null;
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
}
