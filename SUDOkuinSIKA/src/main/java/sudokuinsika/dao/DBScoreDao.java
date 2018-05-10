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

/**
 * Contains methods to get Score data from an SQL database.
 */
public class DBScoreDao implements ScoreDao {

    private final Database db;

    /**
     * Sole constructor.
     *
     * @param db the database we connect to
     */
    public DBScoreDao(Database db) {
        this.db = db;
    }

    /**
     * Fetches from an SQL database all users scores of the given level and help.
     * The scores are in ascending order, and the limit is 19 scores.
     *
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing all users' scores of the given level and help
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public List<Score> findScores(int level, boolean help) throws SQLException {
        List<Score> scores = new ArrayList<>();
        Connection conn = db.getConnection();
        String query = "SELECT score, time, username "
                + "FROM score INNER JOIN user ON score.user_id = user.id "
                + "WHERE level = ? AND help = ? ORDER BY score LIMIT 19";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, level);
        stmt.setBoolean(2, help);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getString("username"));
            Duration score = Duration.of(rs.getLong("score"), ChronoUnit.MILLIS);
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(
                    rs.getTimestamp("time").toInstant(), ZoneId.systemDefault());
            scores.add(new Score(user, level, help, score, dateTime));
        }
        return scores;
    }

    /**
     * Fetches from an SQL database one user's scores of the given level and help.
     * The scores are in ascending order, and the limit is 19 scores.
     *
     * @param user the user whose scores we are finding
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing one user's scores of the given level and help
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public List<Score> findScores(User user, int level, boolean help) throws SQLException {
        int userId = user.getId();
        List<Score> scores = new ArrayList<>();
        Connection conn = db.getConnection();
        String query = "SELECT score, time FROM score WHERE user_id = ? "
                + "AND level = ? and help = ? ORDER BY score LIMIT 19";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.setInt(2, level);
        stmt.setBoolean(3, help);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Duration score = Duration.of(rs.getLong("score"), ChronoUnit.MILLIS);
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(
                    rs.getTimestamp("time").toInstant(), ZoneId.systemDefault());
            scores.add(new Score(user, level, help, score, dateTime));
        }
        return scores;
    }

    /**
     * Saves one score to an SQL database.
     *
     * @param score the score we're saving
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    @Override
    public void save(Score score) throws SQLException {
        Connection conn = db.getConnection();
        String query = "INSERT INTO score (user_id, level, help, score, time) "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, score.getUser().getId());
        stmt.setInt(2, score.getLevel());
        stmt.setBoolean(3, score.withHelp());
        stmt.setLong(4, score.getScore().toMillis());
        stmt.setTimestamp(5, new Timestamp(score.getDateTime().toInstant().toEpochMilli()));
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
