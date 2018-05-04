/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuinsika.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

/**
 *
 * @author nigosH
 */
public class DBScoreDaoTest {

    public DBScoreDaoTest() {
    }

    DBScoreDao scoreDao;
    DBUserDao userDao;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        boolean dbExists = (new File("test.db")).isFile();
        Database db = new Database("jdbc:sqlite:test.db");
        if (!dbExists) {
            db.init();
        }
        scoreDao = new DBScoreDao(db);
        userDao = new DBUserDao(db);

        Connection conn = db.getConnection();
        String query = "DELETE FROM score";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Test
    public void saveSavesScoreAndFindScoresUserFindsIt() throws SQLException {
        User user = new User("test");
        user.setId(1);
        
        scoreDao.save(1, 35, true, 323120, 380912742);
        List<Score> scores = scoreDao.findScores(user, 35, true);
        Score score = scores.get(0);
        assertEquals(323120, score.getScore().toMillis());
        assertEquals(380912742, score.getDateTime().toInstant().toEpochMilli());
    }

    @Test
    public void saveSavesScoreAndFindScoresFindsIt() throws SQLException {
        User user = new User("test");
        user.setId(1);
        userDao.save(user);

        scoreDao.save(1, 52, false, 254230, 97391572);
        List<Score> scores = scoreDao.findScores(52, false);
        Score score = scores.get(0);
        assertEquals("test", score.getUser().getUsername());
        assertEquals(254230, score.getScore().toMillis());
        assertEquals(97391572, score.getDateTime().toInstant().toEpochMilli());
    }
}
