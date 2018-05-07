package sudokuinsika.dao;

import java.sql.SQLException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public class FakeScoreDao implements ScoreDao {

    public List<Score> scores;

    public FakeScoreDao() {
        scores = new ArrayList<>();
    }

    @Override
    public List<Score> findScores(int level, boolean help) throws SQLException {
        return scores.stream()
                .filter(s -> s.getLevel() == level && s.usedHelp() == help)
                .collect(Collectors.toList());
    }

    @Override
    public List<Score> findScores(User user, int level, boolean help) throws SQLException {
        return scores.stream()
                .filter(s -> s.getUser().getUsername().equals(user.getUsername())
                        && s.getLevel() == level && s.usedHelp() == help)
                .collect(Collectors.toList());
    }

    @Override
    public void save(User user, int level, boolean help, long score, long time) throws SQLException {
        scores.add(new Score(user, level, help, Duration.ofMillis(score), ZonedDateTime.now()));
    }
}
