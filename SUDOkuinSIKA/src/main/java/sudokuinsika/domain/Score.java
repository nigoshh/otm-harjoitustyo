package sudokuinsika.domain;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Score {

    private final User user;
    private final int level;
    private final boolean help;
    private final Duration score;
    private final ZonedDateTime dateTime;

    public Score(User user, int level, boolean help, Duration score, ZonedDateTime dateTime) {
        this.user = user;
        this.level = level;
        this.help = help;
        this.score = score;
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public int getLevel() {
        return level;
    }

    public boolean usedHelp() {
        return help;
    }

    public Duration getScore() {
        return score;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
}
