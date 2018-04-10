package sudokuinsika.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sudokuinsika.domain.User;

public class DBUserDaoTest {

    DBUserDao dao;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        Database db = new Database("jdbc:sqlite:test.db");
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
        User user = new User("test", "test", "test");
        dao.save(user);
        assertEquals(user, dao.findOne(user.getUsername()));
    }

    @Test
    public void findOneReturnsNullForNonexistentUser() throws SQLException {
        assertEquals(null, dao.findOne("test"));
    }
}
