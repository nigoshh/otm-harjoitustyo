package sudokuinsika.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public class DBScoreDaoTest {

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
        Duration score = Duration.of(323120, ChronoUnit.MILLIS);
        ZonedDateTime dateTime = Instant.ofEpochMilli(380912742).atZone(ZoneId.systemDefault());
        scoreDao.save(new Score(user, 35, true, score, dateTime));
        List<Score> scores = scoreDao.findScores(user, 35, true);
        Score saved = scores.get(0);
        assertEquals(323120, saved.getScore().toMillis());
        assertEquals(380912742, saved.getDateTime().toInstant().toEpochMilli());
    }

    @Test
    public void saveSavesScoreAndFindScoresFindsIt() throws SQLException {
        User user = new User("test");
        user.setId(1);
        userDao.save(user);
        Duration score = Duration.of(254230, ChronoUnit.MILLIS);
        ZonedDateTime dateTime = Instant.ofEpochMilli(97391572).atZone(ZoneId.systemDefault());
        scoreDao.save(new Score(user, 52, false, score, dateTime));
        List<Score> scores = scoreDao.findScores(52, false);
        Score saved = scores.get(0);
        assertEquals("test", saved.getUser().getUsername());
        assertEquals(254230, saved.getScore().toMillis());
        assertEquals(97391572, saved.getDateTime().toInstant().toEpochMilli());
    }
}
