package sudokuinsika.ui;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import sudokuinsika.domain.Game;

/**
 * FXML Controller for the game scene.
 */
public class GameController extends Controller {

    @FXML
    private GridPane puzzleGrid;
    @FXML
    private GridPane inputGrid;
    @FXML
    private ToggleButton smallDigits;
    @FXML
    private Button newPuzzle;
    @FXML
    private Slider slider;
    @FXML
    private Label sliderLabel;
    @FXML
    private Label level;
    @FXML
    private Label timer;
    @FXML
    private Hyperlink instructions;
    @FXML
    private Hyperlink topScoresLink;
    @FXML
    private Hyperlink settingsLink;
    @FXML
    private Hyperlink logOutLink;

    private Button[] cellButtons;
    private RadioButton[] inputButtons;
    private final IntegerProperty sliderValue = new SimpleIntegerProperty(29);
    private final String instructionsText
            = "to write a digit into a cell select the desired digit from the available choices by clicking a button or pressing the corresponding number key on your keyboard (0 for delete)\n"
            + "then click on the desired cell in the puzzle (delete clears the cell)\n\n"
            + "you can also annotate small digits into a cell to mark possible candidates for that cell\n"
            + "to toggle the small-digits writing mode click the toggle button \"small digits\"\n\n"
            + "you can create a new puzzle by selecting the desired level with the slider and then clicking the \"new puzzle\" button\n\n"
            + "the level number represents the number of cells that are already filled in at the beginning\n"
            + "so 79 is the easiest level, and 23 the hardest\n\n"
            + "you can check that all the digits you have input are valid by clicking the \"check puzzle\" button\n"
            + "this checks if each normal sized digit appears at most once in any column, row or block\n"
            + "small digits are not taken into account when checking the puzzle\n\n"
            + "if you click the \"check puzzle\" button at least once when solving a puzzle your score will be saved into a different category (\"with help\")\n\n"
            + "try to solve the puzzle as quickly as possible!";
    private final String logOutWarning
            = "you sure you wanna log out?\n\n"
            + "you won't be able to continue solving this particular puzzle\n"
            + "the next time you log in";
    private final String cellButtonBaseStyle
            = "-fx-background-color: #ffffff; "
            + "-fx-background-radius: 0; "
            + "-fx-text-fill: #000000; "
            + "-fx-font-size: 23";
    private final String cellButtonSmallDigitsStyle
            = "-fx-background-color: #ffffff; "
            + "-fx-background-radius: 0; "
            + "-fx-text-fill: #000000; "
            + "-fx-font-size: 11.25; "
            + "-fx-line-spacing: -5";

    /**
     * Initializes the game scene.
     */
    public void init() {
        createCellButtons();
        createInputButtons();
        bindSliderAndLabel();
        startTimer();
    }

    /**
     * Cleans up the game scene.
     */
    public void clear() {
        instructions.setVisited(false);
        logOutLink.setVisited(false);
        topScoresLink.setVisited(false);
        settingsLink.setVisited(false);
        level.requestFocus();
        updateTimer();
    }

    /**
     * Cleans up the game scene when coming from the login scene.
     */
    public void clearForLogin() {
        slider.setValue(29);
        newPuzzle.fire();
    }

    /**
     * Draws the sudoku board.
     */
    public void drawBoard() {
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Button b = cellButtons[i];
                if (getGame().containsSmallDigits(row, column)) {
                    setStyleSmallDigits(b);
                } else if (isWritable(row, column)) {
                    setStyleNormalCell(b);
                } else {
                    setStyleBold(b);
                }
                b.setText(getCellText(row, column));
                i++;
            }
        }
    }

    @FXML
    private void newPuzzle(ActionEvent event) {
        Game game = getGame();
        game.createRiddle((int) slider.getValue());
        drawBoard();
        level.setText("level " + game.getLevel());
        game.resetTimer();
        updateTimer();
    }

    @FXML
    private void checkPuzzle(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        setCustomIcon(alert);
        alert.setTitle("puzzle validity AKA your great skills");
        alert.setHeaderText(null);
        String message = "";
        if (getGame().checkPuzzle()) {
            message += "the puzzle is valid, hurray for you";
        } else {
            message += "you messed up mate, something's wrong here";
        }
        alert.setContentText(message);
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDefaultButton(false);
        alert.showAndWait();
    }

    @FXML
    private void showInstructions(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        setCustomIcon(alert);
        alert.setTitle("instructions");
        alert.setHeaderText(null);
        alert.setContentText(instructionsText);
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDefaultButton(false);
        alert.showAndWait();
    }

    @FXML
    private void seeSolution(ActionEvent event) {
        getGame().solveRiddle();
        drawBoard();
    }

    @FXML
    private void toggleWriteSmall(ActionEvent event) {
        if (smallDigits.isSelected()) {
            getGame().setWriteSmall(true);
        } else {
            getGame().setWriteSmall(false);
        }
    }

    @FXML
    private void logOut(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, logOutWarning, ButtonType.YES, ButtonType.NO);
        setCustomIcon(alert);
        alert.setTitle("forget this puzzle and log out");
        alert.setHeaderText(null);
        Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        yesButton.setDefaultButton(false);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            getUsersMgmt().logOut();
            toLogin(event);
        }
    }

    @FXML
    private void fireButtonsWithKeys(KeyEvent event) {
        switch (event.getCode()) {
            case S:
                smallDigits.fire();
                break;
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
                b.setWrapText(true);
                b.setMaxHeight(Double.MAX_VALUE);
                b.setMaxWidth(Double.MAX_VALUE);
                final int cellRow = buttonToRowIndex(i);
                final int cellColumn = buttonToColumnIndex(i);
                b.setOnAction((ActionEvent e) -> {
                    if (getGame().writeCell(cellRow, cellColumn)) {
                        if (smallDigits.isSelected()) {
                            setStyleSmallDigits(b);
                        } else {
                            setStyleNormalCell(b);
                        }
                        b.setText(getCellText(cellRow, cellColumn));
                        try {
                            if (getUsersMgmt().sudoKuinSika()) {
                                congrats();
                                newPuzzle.fire();
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
            getGame().setWriteValue((byte) 0);
        });
        inputGrid.add(delete, 2, 4);
        inputButtons[0] = delete;
        byte i = 1;
        for (int row = 1; row < 4; row++) {
            for (int column = 1; column < 4; column++) {
                RadioButton rb = new RadioButton("" + i);
                rb.getStyleClass().remove("radio-button");
                rb.getStyleClass().add("toggle-button");
                rb.setStyle("-fx-font-size: 17");
                rb.setToggleGroup(group);
                final byte writeValue = i;
                rb.setOnAction((ActionEvent e) -> {
                    getGame().setWriteValue(writeValue);
                });
                inputGrid.add(rb, column, row);
                inputButtons[i] = rb;
                i++;
            }
        }
    }

    private void congrats() {
        Alert alert = new Alert(AlertType.INFORMATION);
        setCustomIcon(alert);
        alert.setTitle("congrats mate");
        alert.setHeaderText(null);
        alert.setContentText("you made it! cheers");
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDefaultButton(false);
        alert.showAndWait();
    }

    private void bindSliderAndLabel() {
        slider.valueProperty().bindBidirectional(sliderValue);
        sliderLabel.textProperty().bind(Bindings.convert(sliderValue));
    }

    private final Timeline timerTL = new Timeline(new KeyFrame(
            Duration.seconds(1), (ActionEvent event) -> {
        updateTimer();
    }));

    private void startTimer() {
        timerTL.setCycleCount(Timeline.INDEFINITE);
        timerTL.play();

    }

    private void updateTimer() {
        Game game = getGame();
        if (game != null) {
            timer.setText(game.timeElapsed());
        }
    }

    private void setStyleSmallDigits(Button b) {
        b.setStyle(cellButtonSmallDigitsStyle);
        b.setPadding(new Insets(-6, 0, -6, -1));
    }

    private void setStyleNormalCell(Button b) {
        b.setStyle(cellButtonBaseStyle);
        b.setPadding(new Insets(0));
    }

    private void setStyleBold(Button b) {
        b.setStyle(cellButtonBaseStyle + "; -fx-font-weight: bold");
        b.setPadding(new Insets(0));
    }

    private int buttonToRowIndex(int buttonIndex) {
        return buttonIndex / 9;
    }

    private int buttonToColumnIndex(int buttonIndex) {
        return buttonIndex % 9;
    }

    private String getCellText(int row, int column) {
        return getGame().cellToString(row, column);
    }

    private boolean isWritable(int row, int column) {
        return getGame().isWritable(row, column);
    }
}
