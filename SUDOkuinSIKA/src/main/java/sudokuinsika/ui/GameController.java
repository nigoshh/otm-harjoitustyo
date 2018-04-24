package sudokuinsika.ui;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sudokuinsika.domain.Game;

public class GameController extends Controller {

    @FXML
    private GridPane puzzleGrid;

    @FXML
    private GridPane inputGrid;

    @FXML
    private Hyperlink logOutLink;

    @FXML
    private Text instructions;

    @FXML
    private Label timer;

    @FXML
    private Button check;

    @FXML
    private Button newPuzzle;

    @FXML
    private Hyperlink topScoresLink;

    private Button[] cellButtons;
    private RadioButton[] inputButtons;
    private String cellButtonBaseStyle
            = "-fx-background-color: #ffffff; "
            + "-fx-background-radius: 0; "
            + "-fx-text-fill: #000000; "
            + "-fx-font-size: 23";

    @FXML
    private void newPuzzle(ActionEvent event) {
        Game game = getGame();
        game.createRiddle();
        drawBoard();
        game.resetTimer();
        startTimer();
        updateTimer();
    }

    @FXML
    private void checkPuzzle(ActionEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("puzzle validity AKA your great skills");
        alert.setHeaderText(null);
        String message = "";
        if (getGame().checkPuzzle()) {
            message += "the puzzle is valid, hurray for you";
        } else {
            message += "you messed up mate, something's wrong here";
        }
        alert.setContentText(message);

        alert.showAndWait();
    }

    @FXML
    private void setCellWriteValuePressingKeys(KeyEvent event) {
        switch (event.getCode()) {
            case DIGIT0:
            case NUMPAD0:
                inputButtons[0].fire();
                break;
            case DIGIT1:
            case NUMPAD1:
                inputButtons[1].fire();
                break;
            case DIGIT2:
            case NUMPAD2:
                inputButtons[2].fire();
                break;
            case DIGIT3:
            case NUMPAD3:
                inputButtons[3].fire();
                break;
            case DIGIT4:
            case NUMPAD4:
                inputButtons[4].fire();
                break;
            case DIGIT5:
            case NUMPAD5:
                inputButtons[5].fire();
                break;
            case DIGIT6:
            case NUMPAD6:
                inputButtons[6].fire();
                break;
            case DIGIT7:
            case NUMPAD7:
                inputButtons[7].fire();
                break;
            case DIGIT8:
            case NUMPAD8:
                inputButtons[8].fire();
                break;
            case DIGIT9:
            case NUMPAD9:
                inputButtons[9].fire();
                break;
            default:
                break;
        }
    }

    private void createCellButtons() {
        cellButtons = new Button[81];
        int i = 0;
        for (int gridRow = 1; gridRow < 18; gridRow += 2) {
            for (int gridColumn = 1; gridColumn < 18; gridColumn += 2) {
                Button b = new Button();
                b.setPadding(new Insets(0));
                b.setMaxHeight(Double.MAX_VALUE);
                b.setMaxWidth(Double.MAX_VALUE);
                final int cellRow = buttonToRowIndex(i);
                final int cellColumn = buttonToColumnIndex(i);
                b.setOnAction((ActionEvent e) -> {
                    if (getGame().writeCell(cellRow, cellColumn)) {
                        b.setText(getCellValue(cellRow, cellColumn));
                        try {
                            if (getGame().won()) {
                                victory();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(GameController.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    }
                });
                puzzleGrid.add(b, gridColumn, gridRow);
                cellButtons[i] = b;
                i++;
            }
        }
    }

    private void createInputButtons() {
        ToggleGroup group = new ToggleGroup();
        inputButtons = new RadioButton[10];
        RadioButton delete = new RadioButton("delete");
        delete.getStyleClass().remove("radio-button");
        delete.getStyleClass().add("toggle-button");
        delete.setStyle("-fx-font-size: 17");
        delete.setToggleGroup(group);
        delete.setSelected(true);
        delete.setOnAction((ActionEvent e) -> {
            getGame().setCellWriteValue((byte) 0);
        });
        GridPane.setColumnSpan(delete, 3);
        inputGrid.add(delete, 1, 5);
        inputButtons[0] = delete;
        byte i = 1;
        for (int row = 2; row < 5; row++) {
            for (int column = 1; column < 4; column++) {
                RadioButton rb = new RadioButton("" + i);
                rb.getStyleClass().remove("radio-button");
                rb.getStyleClass().add("toggle-button");
                rb.setStyle("-fx-font-size: 17");
                rb.setToggleGroup(group);
                final byte cellWriteValue = i;
                rb.setOnAction((ActionEvent e) -> {
                    getGame().setCellWriteValue(cellWriteValue);
                });
                inputGrid.add(rb, column, row);
                inputButtons[i] = rb;
                i++;
            }
        }
    }

    private int cellToButtonIndex(int row, int column) {
        return (row * 9) + column;
    }

    private int buttonToRowIndex(int buttonIndex) {
        return buttonIndex / 9;
    }

    private int buttonToColumnIndex(int buttonIndex) {
        return buttonIndex % 9;
    }

    private String getCellValue(int row, int column) {
        return getGame().cellToString(row, column);
    }

    private boolean isWritable(int row, int column) {
        return getGame().isWritable(row, column);
    }

    public void drawBoard() {
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Button b = cellButtons[i];
                b.setText(getCellValue(row, column));
                String style = cellButtonBaseStyle;
                if (isWritable(row, column)) {
                    style += "; -fx-font-weight: normal";
                } else {
                    style += "; -fx-font-weight: bold";
                }
                b.setStyle(style);
                i++;
            }
        }
    }

    public void init() {
        createCellButtons();
        drawBoard();
        createInputButtons();
        startTimer();
    }

    public void clear() {
        instructions.setText("instructions:"
                + "\nselect the desired input from the available choices"
                + "\n(click on a button or press the corresponding key on your keyboard)"
                + "\nthen click on the desired cell in the puzzle");
        logOutLink.setVisited(false);
        topScoresLink.setVisited(false);
        check.requestFocus();
        updateTimer();
    }

    private final Timeline timerTL = new Timeline(new KeyFrame(
            Duration.seconds(1), (ActionEvent event) -> {
                updateTimer();
    }));

    private void updateTimer() {
        timer.setText(getGame().timeElapsed());
    }

    public void startTimer() {
        timerTL.setCycleCount(Timeline.INDEFINITE);
        timerTL.play();

    }

    private void victory() {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("congrats mate");
        alert.setHeaderText(null);
        String message = "you won! cheers";
        alert.setContentText(message);

        alert.showAndWait();
        newPuzzle.fire();
    }
}
