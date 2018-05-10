package sudokuinsika.dao;

import java.sql.SQLException;
import java.util.List;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

/**
 * Contains methods to get Score data from a file.
 */
public interface ScoreDao {

    /**
     * Fetches from a file all users' scores of the given level and help.
     *
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing all users' scores of the given level and help
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    List<Score> findScores(int level, boolean help) throws SQLException;

    /**
     * Fetches from a file one user's scores of the given level and help.
     *
     * @param user the user whose scores we are finding
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing one user's scores of the given level and help
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    List<Score> findScores(User user, int level, boolean help)
            throws SQLException;

    /**
     * Saves one score to a file.
     *
     * @param score the score we're saving
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    void save(Score score) throws SQLException;
}
