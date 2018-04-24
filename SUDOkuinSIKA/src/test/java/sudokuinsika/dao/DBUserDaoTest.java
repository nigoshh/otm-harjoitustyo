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
        boolean dbExists = (new File("test.db")).isFile();
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
        User user1 = new User("test");
        dao.save(user1);
        User user2 = dao.findOne(user1.getUsername());
        assertEquals(user1.getUsername(), user2.getUsername());
    }

    @Test
    public void findOneReturnsNullForNonexistentUser() throws SQLException {
        assertEquals(null, dao.findOne("test"));
    }
}
