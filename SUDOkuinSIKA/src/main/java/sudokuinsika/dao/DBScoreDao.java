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
    public List<Score> findScores() throws SQLException {
        List<Score> scores = new ArrayList<>();
        Connection conn = db.getConnection();
        String query = "SELECT score, time, username "
                + "FROM score INNER JOIN user "
                + "ON score.user_id = user.id "
                + "ORDER BY score LIMIT 23";
        PreparedStatement stmt = conn.prepareStatement(query);
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
    public List<Score> findScores(User user) throws SQLException {
        int userId = user.getId();
        List<Score> scores = new ArrayList<>();
        Connection conn = db.getConnection();
        String query = "SELECT score, time FROM score WHERE user_id = ? "
                + "ORDER BY score LIMIT 23";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
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
    public void save(int userId, long score, long time) throws SQLException {
        Connection conn = db.getConnection();
        String query = "INSERT INTO score (user_id, score, time) "
                + "VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.setLong(2, score);
        stmt.setTimestamp(3, new Timestamp(time));

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
