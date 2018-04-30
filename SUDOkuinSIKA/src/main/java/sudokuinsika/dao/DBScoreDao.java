package sudokuinsika.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public class DBScoreDao implements ScoreDao {

    private Database db;

    public DBScoreDao(Database db) {
        this.db = db;
    }

    @Override
    public List<Score> findScores(int level, boolean help) throws SQLException {
        List<Score> scores = new ArrayList<>();
        Connection conn = db.getConnection();
        String query = "SELECT score, time, username "
                + "FROM score INNER JOIN user ON score.user_id = user.id "
                + "WHERE level = ? AND help = ? ORDER BY score LIMIT 23";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, level);
        stmt.setBoolean(2, help);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            User user = new User(rs.getString("username"));
            Duration score = Duration.of(
                    rs.getLong("score"), ChronoUnit.MILLIS);
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(
                    rs.getTimestamp("time").toInstant(), ZoneId.systemDefault());
            scores.add(new Score(user, score, dateTime));
        }
        return scores;
    }

    @Override
    public List<Score> findScores(User user, int level, boolean help)
            throws SQLException {
        int userId = user.getId();
        List<Score> scores = new ArrayList<>();
        Connection conn = db.getConnection();
        String query = "SELECT score, time FROM score WHERE user_id = ? "
                + "AND level = ? and help = ? ORDER BY score LIMIT 23";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.setInt(2, level);
        stmt.setBoolean(3, help);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Duration score = Duration.of(
                    rs.getLong("score"), ChronoUnit.MILLIS);
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(
                    rs.getTimestamp("time").toInstant(), ZoneId.systemDefault());
            scores.add(new Score(user, score, dateTime));
        }
        return scores;
    }

    @Override
    public void save(int userId, int level, boolean help, long score, long time)
            throws SQLException {
        Connection conn = db.getConnection();
        String query = "INSERT INTO score (user_id, level, help, score, time) "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.setInt(2, level);
        stmt.setBoolean(3, help);
        stmt.setLong(4, score);
        stmt.setTimestamp(5, new Timestamp(time));

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
