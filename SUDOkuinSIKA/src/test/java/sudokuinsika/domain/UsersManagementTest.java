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
    public void createUserCreatesUser() {
        assertEquals(1, userDao.users.size());
    }

    @Test
    public void logInLogsInExistingUser() throws SQLException {
        Game game = userMgmt.logIn("test", password);
        assertNotNull(game);
        assertEquals("test", userMgmt.getLoggedInUser().getUsername());
    }

    @Test
    public void logInReturnsNullForNonExistingUser() throws SQLException {
        assertNull(userMgmt.logIn("wrong", password));
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
}
