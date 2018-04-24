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
    public void setUp() {
        userDao = new FakeUserDao();
        scoreDao = new FakeScoreDao();
        userMgmt = new UsersManagement(userDao, scoreDao);
        userMgmt.setPwIterations(1);
        String pw = "password";
        password = pw.toCharArray();
    }

    @Test
    public void createUserCreatesUser()
            throws SQLException, NoSuchAlgorithmException {
        userMgmt.createUser("test", password, "test");
        assertEquals(1, userDao.users.size());
    }

    @Test
    public void logInLogsInUser()
            throws SQLException, NoSuchAlgorithmException {
        userMgmt.createUser("test", password, "test");
        Game game = userMgmt.logIn("test", password);
        assertNotNull(game);
        assertEquals("test", userMgmt.getLoggedInUser().getUsername());
    }
}
