package sudokuinsika.domain;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sudokuinsika.dao.FakeScoreDao;
import sudokuinsika.dao.FakeUserDao;
import sudokuinsika.dao.ScoreDao;

public class UsersManagementTest {

    private UsersManagement usersMgmt;
    private char[] password;

    @Before
    public void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        usersMgmt = new UsersManagement(new FakeUserDao(), new FakeScoreDao());
        usersMgmt.setPwIterations(1);
        String pw = "password";
        password = pw.toCharArray();
        usersMgmt.createUser("test", password, "test");
    }

    @Test
    public void createUserCreatesUserAndReturnsTrueIfUsernameIsntTaken()
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        assertTrue(usersMgmt.createUser("new stuff", password, "new stuff"));
        assertNotNull(usersMgmt.getUserDao().findOne("new stuff"));
    }

    @Test
    public void createUserDoesntCreateUserAndReturnsFalseIfUsernameIsTaken()
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        assertFalse(usersMgmt.createUser("test", password, "new stuff"));
        assertEquals("test", usersMgmt.getUserDao().findOne("test").getEmail());
    }

    @Test
    public void logInLogsInExistingUserAndReturnsNotNullGame()
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        assertNotNull(usersMgmt.logIn("test", password));
        assertEquals("test", usersMgmt.getLoggedInUser().getUsername());
    }

    @Test
    public void logInDoesntLogInNonExistingUserAndReturnsNull()
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        assertNull(usersMgmt.logIn("wrong", password));
        assertNull(usersMgmt.getLoggedInUser());
    }

    @Test
    public void logInReturnsNullWithWrongPassword() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        String wrongPW = "wrong";
        char[] wPW = wrongPW.toCharArray();
        assertNull(usersMgmt.logIn("test", wPW));
    }

    @Test
    public void logOutLogsOutUserAndSetsGameToNull() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usersMgmt.logIn("test", password);
        usersMgmt.logOut();
        assertNull(usersMgmt.getGame());
        assertNull(usersMgmt.getLoggedInUser());
    }

    @Test
    public void changeUsernameUpdatesUsernameAndReturnsTrueIfUsernameIsNotTaken() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usersMgmt.logIn("test", password);
        assertTrue(usersMgmt.changeUsername("new stuff"));
        assertEquals("new stuff", usersMgmt.getLoggedInUser().getUsername());
        usersMgmt.logOut();
        assertNotNull(usersMgmt.logIn("new stuff", password));
    }

    @Test
    public void changeUsernameDoesntUpdateUsernameAndReturnsFalseIfUsernameIsTaken()
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        usersMgmt.createUser("new stuff", password, "test");
        usersMgmt.logIn("test", password);
        assertFalse(usersMgmt.changeUsername("new stuff"));
        assertEquals("test", usersMgmt.getLoggedInUser().getUsername());
        assertNotNull(usersMgmt.getUserDao().findOne("test"));
        assertNotNull(usersMgmt.getUserDao().findOne("new stuff"));
    }

    @Test
    public void changePasswordUpdatesPassword() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usersMgmt.logIn("test", password);
        char[] newPW = "new stuff".toCharArray();
        usersMgmt.changePassword(newPW);
        usersMgmt.logOut();
        assertNotNull(usersMgmt.logIn("test", newPW));
    }

    @Test
    public void changeEmailUpdatesEmail() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usersMgmt.logIn("test", password);
        usersMgmt.changeEmail("new stuff");
        assertEquals("new stuff", usersMgmt.getLoggedInUser().getEmail());
        assertEquals("new stuff", usersMgmt.getUserDao().findOne("test").getEmail());
    }

    @Test
    public void checkUsernameLengthReturnsTrueWithCorrectLengths() {
        assertTrue(usersMgmt.checkUsernameLength("1"));
        assertTrue(usersMgmt.checkUsernameLength("26usernameusernameusername"));
        assertTrue(usersMgmt.checkUsernameLength("230usernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameuse"));
    }

    @Test
    public void checkUsernameLengthReturnsFalseWithWrongLengths() {
        assertFalse(usersMgmt.checkUsernameLength(""));
        assertFalse(usersMgmt.checkUsernameLength("231usernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameuser"));
    }

    @Test
    public void checkPasswordLengthReturnsTrueWithCorrectLengths() {
        assertTrue(usersMgmt.checkPasswordLength("10password"));
        assertTrue(usersMgmt.checkPasswordLength("26passwordpasswordpassword"));
        assertTrue(usersMgmt.checkPasswordLength("1000passwordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpass"));
    }

    @Test
    public void checkPasswordLengthReturnsFalseWithWrongLengths() {
        assertFalse(usersMgmt.checkPasswordLength(""));
        assertFalse(usersMgmt.checkPasswordLength("9password"));
        assertFalse(usersMgmt.checkPasswordLength("1001passwordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpassw"));
    }

    @Test
    public void checkEmailLengthReturnsTrueWithCorrectLengths() {
        assertTrue(usersMgmt.checkEmailLength(""));
        assertTrue(usersMgmt.checkEmailLength("6email"));
        assertTrue(usersMgmt.checkEmailLength("230emailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailem"));
    }

    @Test
    public void checkEmailLengthReturnsFalseWithWrongLengths() {
        assertFalse(usersMgmt.checkEmailLength("231emailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailema"));
    }

    @Test
    public void deleteUserDeletesAndLogsOutUser() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        assertNotNull(usersMgmt.getUserDao().findOne("test"));
        usersMgmt.logIn("test", password);
        assertNotNull(usersMgmt.getLoggedInUser());
        usersMgmt.deleteUser();
        assertNull(usersMgmt.getLoggedInUser());
        assertNull(usersMgmt.getUserDao().findOne("test"));
    }

    @Test
    public void sudoKuinSikaReturnsTrueIfRiddleIsSolvedAndSavesScoresCorrectly()
            throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usersMgmt.logIn("test", password);
        usersMgmt.getGame().createRiddle(29);
        usersMgmt.getGame().solveRiddle();
        assertTrue(usersMgmt.sudoKuinSika());
        ScoreDao scoreDao = usersMgmt.getScoreDao();
        assertEquals(1, scoreDao.findScores(29, false).size());
        assertTrue(scoreDao.findScores(29, true).isEmpty());
        assertTrue(scoreDao.findScores(23, false).isEmpty());
        assertTrue(scoreDao.findScores(23, true).isEmpty());
        assertEquals(1, scoreDao.findScores(new User("test"), 29, false).size());
        assertTrue(scoreDao.findScores(new User("test"), 29, true).isEmpty());
        assertTrue(scoreDao.findScores(new User("test"), 23, false).isEmpty());
        assertTrue(scoreDao.findScores(new User("test"), 23, true).isEmpty());
        assertTrue(scoreDao.findScores(new User("wrong"), 29, false).isEmpty());
        assertTrue(scoreDao.findScores(new User("wrong"), 29, true).isEmpty());
        assertTrue(scoreDao.findScores(new User("wrong"), 23, false).isEmpty());
        assertTrue(scoreDao.findScores(new User("wrong"), 23, true).isEmpty());
    }

    @Test
    public void sudoKuinSikaReturnsFalseIfRiddleIsntSolved() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        usersMgmt.logIn("test", password);
        usersMgmt.getGame().createRiddle(29);
        assertFalse(usersMgmt.sudoKuinSika());
    }
}
