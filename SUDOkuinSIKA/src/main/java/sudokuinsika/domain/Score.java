package sudokuinsika.domain;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Represents a score obtained by solving a sudoku puzzle.
 */
public class Score {

    private User user;
    private final int level;
    private final boolean help;
    private final Duration score;
    private final ZonedDateTime dateTime;

    /**
     * Sole constructor.
     *
     * @param user the user who solved the puzzle and obtained this score
     * @param level difficulty level of the sudoku puzzle (indicates how many
     * cells were already set at the beginning)
     * @param help indicates if any help was used while solving the puzzle
     * @param score time elapsed from the puzzle's creation until all cells were
     * filled correctly
     * @param dateTime the date and time when the puzzle was solved
     */
    public Score(User user, int level, boolean help, Duration score, ZonedDateTime dateTime) {
        this.user = user;
        this.level = level;
        this.help = help;
        this.score = score;
        this.dateTime = dateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Returns true if any help was used while solving the puzzle.
     *
     * @return {@link #help}'s value
     */
    public boolean withHelp() {
        return help;
    }

    public Duration getScore() {
        return score;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
}
