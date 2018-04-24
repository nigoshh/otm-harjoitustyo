package sudokuinsika.dao;

import java.sql.SQLException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public class FakeScoreDao implements ScoreDao {

    public List<Score> scores;

    public FakeScoreDao() {
        scores = new ArrayList<>();
        scores.add(new Score(new User("fake"), Duration.ofSeconds(230),
                ZonedDateTime.now()));
    }

    @Override
    public List<Score> findScores() throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public List<Score> findScores(User user) throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void save(int userId, long score, long time) throws SQLException {
    }

}
