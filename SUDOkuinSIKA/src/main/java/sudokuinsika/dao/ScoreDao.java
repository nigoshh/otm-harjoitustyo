package sudokuinsika.dao;

import java.sql.SQLException;
import java.util.List;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public interface ScoreDao {
    List<Score> findScores(int level, boolean help) throws SQLException;
    List<Score> findScores(User user, int level, boolean help)
            throws SQLException;
    void save(int userId, int level, boolean help, long score, long time)
            throws SQLException;
}
