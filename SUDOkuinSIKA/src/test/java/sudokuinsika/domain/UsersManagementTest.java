package sudokuinsika.domain;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sudokuinsika.dao.FakeScoreDao;
import sudokuinsika.dao.FakeUserDao;
public class UsersManagementTest {

    private UsersManagement userMgmt;
    private char[] password;
    private FakeUserDao userDao;
    private FakeScoreDao scoreDao;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        userDao = new FakeUserDao();
        scoreDao = new FakeScoreDao();
        userMgmt = new UsersManagement(userDao, scoreDao);
        userMgmt.setPwIterations(1);
        String pw = "password";
        password = pw.toCharArray();
        userMgmt.createUser("test", password, "test");
    }

    @Test
    public void createUserCreatesUserAndReturnsTrueIfUsernameIsntTaken() throws SQLException, NoSuchAlgorithmException {
        assertTrue(userMgmt.createUser("new stuff", password, "new stuff"));
        assertNotNull(userDao.findOne("new stuff"));
    }

    @Test
    public void createUserDoesntCreateUserAndReturnsFalseIfUsernameIsTaken() throws SQLException, NoSuchAlgorithmException {
        assertFalse(userMgmt.createUser("test", password, "test"));
        assertEquals(1, userDao.getUsers().size());
    }

    @Test
    public void logInLogsInExistingUserAndReturnsNotNullGame() throws SQLException {
        assertNotNull(userMgmt.logIn("test", password));
        assertEquals("test", userMgmt.getLoggedInUser().getUsername());
    }

    @Test
    public void logInDoesntLogInNonExistingUserAndReturnsNull() throws SQLException {
        assertNull(userMgmt.logIn("wrong", password));
        assertNull(userMgmt.getLoggedInUser());
    }

    @Test
    public void logInReturnsNullWithWrongPassword() throws SQLException {
        String wrongPW = "wrong";
        char[] wPW = wrongPW.toCharArray();
        assertNull(userMgmt.logIn("test", wPW));
    }

    @Test
    public void logOutLogsOutUserAndSetsGameToNull() throws SQLException {
        userMgmt.logIn("test", password);
        userMgmt.logOut();
        assertNull(userMgmt.getGame());
        assertNull(userMgmt.getLoggedInUser());
    }

    @Test
    public void changeUsernameUpdatesUsernameAndReturnsTrueIfUsernameIsNotTaken() throws SQLException {
        userMgmt.logIn("test", password);
        assertTrue(userMgmt.changeUsername("new stuff"));
        assertEquals("new stuff", userMgmt.getLoggedInUser().getUsername());
        userMgmt.logOut();
        assertNotNull(userMgmt.logIn("new stuff", password));
    }

    @Test
    public void changeUsernameDoesntUpdateUsernameAndReturnsFalseIfUsernameIsTaken() throws SQLException, NoSuchAlgorithmException {
        userMgmt.createUser("new stuff", password, "test");
        userMgmt.logIn("test", password);
        assertFalse(userMgmt.changeUsername("new stuff"));
        assertEquals("test", userMgmt.getLoggedInUser().getUsername());
        assertNotNull(userDao.findOne("test"));
        assertNotNull(userDao.findOne("new stuff"));
    }

    @Test
    public void changePasswordUpdatesPassword() throws SQLException, NoSuchAlgorithmException {
        userMgmt.logIn("test", password);
        char[] newPW = "new stuff".toCharArray();
        userMgmt.changePassword(newPW);
        userMgmt.logOut();
        assertNotNull(userMgmt.logIn("test", newPW));
    }

    @Test
    public void changeEmailUpdatesEmail() throws SQLException {
        userMgmt.logIn("test", password);
        userMgmt.changeEmail("new stuff");
        assertEquals("new stuff", userMgmt.getLoggedInUser().getEmail());
        assertEquals("new stuff", userDao.findOne("test").getEmail());
    }

    @Test
    public void checkUsernameLengthReturnsTrueWithCorrectLengths() {
        assertTrue(userMgmt.checkUsernameLength("1"));
        assertTrue(userMgmt.checkUsernameLength("26usernameusernameusername"));
        assertTrue(userMgmt.checkUsernameLength("230usernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameuse"));
    }

    @Test
    public void checkUsernameLengthReturnsFalseWithWrongLengths() {
        assertFalse(userMgmt.checkUsernameLength(""));
        assertFalse(userMgmt.checkUsernameLength("231usernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameusernameuser"));
    }

    @Test
    public void checkPasswordLengthReturnsTrueWithCorrectLengths() {
        assertTrue(userMgmt.checkPasswordLength("10password"));
        assertTrue(userMgmt.checkPasswordLength("26passwordpasswordpassword"));
        assertTrue(userMgmt.checkPasswordLength("1000passwordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpass"));
    }

    @Test
    public void checkPasswordLengthReturnsFalseWithWrongLengths() {
        assertFalse(userMgmt.checkPasswordLength(""));
        assertFalse(userMgmt.checkPasswordLength("9password"));
        assertFalse(userMgmt.checkPasswordLength("1001passwordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpasswordpassw"));
    }

    @Test
    public void checkEmailLengthReturnsTrueWithCorrectLengths() {
        assertTrue(userMgmt.checkEmailLength(""));
        assertTrue(userMgmt.checkEmailLength("6email"));
        assertTrue(userMgmt.checkEmailLength("230emailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailem"));
    }

    @Test
    public void checkEmailLengthReturnsFalseWithWrongLengths() {
        assertFalse(userMgmt.checkEmailLength("231emailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailemailema"));
    }

    @Test
    public void deleteUserDeletesAndLogsOutUser() throws SQLException {
        assertNotNull(userDao.findOne("test"));
        userMgmt.logIn("test", password);
        assertNotNull(userMgmt.getLoggedInUser());
        userMgmt.deleteUser();
        assertNull(userMgmt.getLoggedInUser());
        assertNull(userDao.findOne("test"));
    }
}
