package sudokuinsika.domain;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Score {

    private User user;
    private Duration score;
    private ZonedDateTime dateTime;

    public Score(User user, Duration score, ZonedDateTime dateTime) {
        this.user = user;
        this.score = score;
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public Duration getScore() {
        return score;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }
}
