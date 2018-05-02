package sudokuinsika.dao;

import java.sql.SQLException;
import java.util.List;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

/**
 * Contains methods to get Score data from a file
 */
public interface ScoreDao {

    /**
     * Fetches all users scores of the given level and help from a file.
     * The scores are in ascending order, and the limit is 23 scores.
     *
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing all users' scores of the given level and help
     * @throws SQLException
     */
    List<Score> findScores(int level, boolean help) throws SQLException;

    /**
     * Fetches one user's scores of the given level and help from a file.
     * The scores are in ascending order, and the limit is 23 scores.
     *
     * @param user the user whose scores we are finding
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing one user's scores of the given level and help
     * @throws SQLException
     */
    List<Score> findScores(User user, int level, boolean help)
            throws SQLException;

    /**
     * Saves a score's data to a file.
     *
     * @param userId the id of the user whose score we are saving
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @param score time elapsed while solving the puzzle (in milliseconds)
     * @param time when the puzzle was solved (date and time)
     * @throws SQLException
     */
    void save(int userId, int level, boolean help, long score, long time)
            throws SQLException;
}
