package sudokuinsika.ui;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

public class ScoresController extends Controller {

    @FXML
    private Text topScoresUser;

    @FXML
    private Text topScoresAll;

    @FXML
    private Hyperlink linkToGame;

    private void writeScoresUser() throws SQLException {
        User user = getUsersMgmt().getLoggedInUser();
        List<Score> scores = getUsersMgmt().getScoreDao().findScores(user);
        StringBuilder b = new StringBuilder();
        scores.stream().forEachOrdered(s -> {
            b.append(formatScore(s.getScore().toMillis()));
            b.append("\t");
            b.append(formatDateTime(s.getDateTime()));
            b.append("\n");
        });
        topScoresUser.setText(b.toString());
    }

    private void writeScoresAll() throws SQLException {
        List<Score> scores = getUsersMgmt().getScoreDao().findScores();
        StringBuilder b = new StringBuilder();
        scores.stream().forEachOrdered(s -> {
            b.append(s.getUser().getUsername());
            b.append("\t");
            b.append(formatScore(s.getScore().toMillis()));
            b.append("\t");
            b.append(formatDateTime(s.getDateTime()));
            b.append("\n");
        });
        topScoresAll.setText(b.toString());
    }

    private String formatScore(long scoreInMillis) {
        return String.format("%d:%02d:%02d.%03d",
                scoreInMillis / 3600000,
                (scoreInMillis % 3600000) / 60000,
                (scoreInMillis % 60000) / 1000,
                (scoreInMillis % 1000));
    }

    private String formatDateTime(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern(
                "dd/MM/yyyy - HH:mm").format(dateTime);
    }

    public void clear() throws SQLException {
        writeScoresUser();
        writeScoresAll();
        topScoresUser.requestFocus();
        linkToGame.setVisited(false);
    }
}
