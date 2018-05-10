package sudokuinsika.ui;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import sudokuinsika.domain.Score;
import sudokuinsika.domain.User;

/**
 * FXML Controller for the scores scene.
 */
public class ScoresController extends Controller {

    @FXML
    private Text topScoresUser;
    @FXML
    private Text topScoresAll;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderLabel;
    @FXML
    private Slider toggle;
    @FXML
    private Label toggleLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label helpLabel;
    @FXML
    private Hyperlink linkToGame;

    private final IntegerProperty sliderValue = new SimpleIntegerProperty(29);
    private final IntegerProperty toggleValue = new SimpleIntegerProperty(0);

    /**
     * Initializes the scores scene.
     */
    public void init() {
        slider.valueProperty().bindBidirectional(sliderValue);
        sliderLabel.textProperty().bind(Bindings.convert(sliderValue));
        toggle.valueProperty().bindBidirectional(toggleValue);
        toggleLabel.textProperty().bind(Bindings.convert(toggleValue));
    }

    /**
     * Cleans up the scores scene.
     *
     * @throws SQLException if an error occurs while accessing an SQL database
     */
    public void clear() throws SQLException {
        update();
        topScoresUser.requestFocus();
        linkToGame.setVisited(false);
    }

    @FXML
    private void update() throws SQLException {
        updateLabels();
        writeScoresUser();
        writeScoresAll();
    }

    private void writeScoresUser() throws SQLException {
        User user = getUsersMgmt().getLoggedInUser();
        List<Score> scores = getUsersMgmt().getScoreDao().findScores(user, level(), help());
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
        List<Score> scores = getUsersMgmt().getScoreDao().findScores(level(), help());
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

    private void updateLabels() {
        levelLabel.setText("level " + level());
        String helpText = "with";
        if (!help()) {
            helpText += "out";
        }
        helpText += " help";
        helpLabel.setText(helpText);
    }

    private int level() {
        return (int) slider.getValue();
    }

    private boolean help() {
        return toggle.getValue() == 1;
    }

    private String formatScore(long scoreInMillis) {
        return String.format("%d:%02d:%02d.%03d",
                scoreInMillis / 3600000,
                (scoreInMillis % 3600000) / 60000,
                (scoreInMillis % 60000) / 1000,
                (scoreInMillis % 1000));
    }

    private String formatDateTime(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm").format(dateTime);
    }
}
