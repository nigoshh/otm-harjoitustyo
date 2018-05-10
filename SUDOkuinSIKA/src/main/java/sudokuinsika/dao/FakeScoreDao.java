package sudokuinsika.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

/**
 * Mock ScoreDao implementation to be used in integration testing.
 */
public class FakeScoreDao implements ScoreDao {

    public List<Score> scores;

    /**
     * Sole constructor.
     */
    public FakeScoreDao() {
        scores = new ArrayList<>();
    }

    /**
     * Fetches all users' scores of the given level and help.
     *
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing all users' scores of the given level and help
     */
    @Override
    public List<Score> findScores(int level, boolean help) {
        return scores.stream()
                .filter(s -> s.getLevel() == level && s.withHelp() == help)
                .collect(Collectors.toList());
    }

    /**
     * Fetches one user's scores of the given level and help.
     *
     * @param user the user whose scores we are finding
     * @param level the game difficulty level (how many cells are already set
     * before starting to solve the puzzle)
     * @param help true if help was used while solving the puzzle
     * @return a List containing one user's scores of the given level and help
     */
    @Override
    public List<Score> findScores(User user, int level, boolean help) {
        return scores.stream()
                .filter(s -> s.getUser().getUsername().equals(user.getUsername())
                    && s.getLevel() == level && s.withHelp() == help)
                .collect(Collectors.toList());
    }

    /**
     * Saves one score.
     *
     * @param score the score we're saving
     */
    @Override
    public void save(Score score) {
        scores.add(score);
    }
}
