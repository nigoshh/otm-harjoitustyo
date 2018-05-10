package sudokuinsika.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sudokuinsika.domain.User;

public class DBUserDaoTest {

    DBUserDao dao;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        boolean dbExists = new File("test.db").isFile();
        Database db = new Database("jdbc:sqlite:test.db");
        if (!dbExists) {
            db.init();
        }
        dao = new DBUserDao(db);
        Connection conn = db.getConnection();
        String query = "DELETE FROM user";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Test
    public void saveSavesUserAndFindOneFindsExistingUser() throws SQLException {
        dao.save(new User("test"));
        assertNotNull(dao.findOne("test"));
    }

    @Test
    public void findOneReturnsNullForNonexistentUser() throws SQLException {
        assertEquals(null, dao.findOne("test"));
        assertEquals(null, dao.findOne("sett"));
    }

    @Test
    public void saveReturnsFalseIfUsernameAlreadyExists() throws SQLException {
        dao.save(new User("test"));
        assertFalse(dao.save(new User("test")));
    }

    @Test
    public void changeUsernameUpdatesUsernameAndReturnsTrueIfNewUsernameIsNotTaken() throws SQLException {
        dao.save(new User("test"));
        assertTrue(dao.changeUsername("test", "new stuff"));
        assertNull(dao.findOne("test"));
        assertNotNull(dao.findOne("new stuff"));
    }

    @Test
    public void changeUsernameDoesntUpdateUsernameAndReturnsFalseIfNewUsernameIsTaken() throws SQLException {
        dao.save(new User("test"));
        dao.save(new User("new stuff"));
        assertFalse(dao.changeUsername("test", "new stuff"));
        assertNotNull(dao.findOne("test"));
        assertNotNull(dao.findOne("new stuff"));
    }

    @Test
    public void changePasswordDataUpdatesPasswordData() throws SQLException {
        byte[] pwHash = new byte[1];
        pwHash[0] = 1;
        byte[] pwSalt = new byte[1];
        pwSalt[0] = 1;
        int pwIterations = 1;
        int pwKeyLength = 32;
        User user = new User("test");
        user.setPwHash(pwHash);
        user.setPwSalt(pwSalt);
        user.setPwIterations(pwIterations);
        user.setPwKeyLength(pwKeyLength);
        dao.save(user);
        pwHash[0]++;
        pwSalt[0]++;
        pwIterations++;
        pwKeyLength *= 2;
        user.setPwHash(pwHash);
        user.setPwSalt(pwSalt);
        user.setPwIterations(pwIterations);
        user.setPwKeyLength(pwKeyLength);
        dao.changePasswordData(user);
        user = dao.findOne("test");
        assertArrayEquals(pwHash, user.getPwHash());
        assertArrayEquals(pwSalt, user.getPwSalt());
        assertEquals(pwIterations, user.getPwIterations());
        assertEquals(pwKeyLength, user.getPwKeyLength());
    }

    @Test
    public void changeEmailUpdatesEmail() throws SQLException {
        User user = new User("test");
        user.setEmail("test");
        dao.save(user);
        dao.changeEmail("test", "new stuff");
        assertEquals("new stuff", dao.findOne("test").getEmail());
    }

    @Test
    public void deleteDeletesUser() throws SQLException {
        dao.save(new User("test"));
        User user = dao.findOne("test");
        assertNotNull(user);
        dao.delete(user);
        assertNull(dao.findOne("test"));
    }
}
