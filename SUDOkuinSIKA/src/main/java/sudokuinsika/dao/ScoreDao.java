package sudokuinsika.dao;

import java.sql.SQLException;
import java.util.List;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public interface ScoreDao {
    List<Score> findScores() throws SQLException;
    List<Score> findScores(User user) throws SQLException;
    void save(int userId, long score, long time)
            throws SQLException;
}
