package sudokuinsika.ui;

import de.sfuhrm.sudoku.Riddle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import sudokuinsika.domain.Game;

public class GameController extends Controller {

    @FXML
    private GridPane puzzleGrid;

    @FXML
    private GridPane inputGrid;

    private Button[] cellButtons;
    private RadioButton[] inputButtons;
    private String cellButtonBaseStyle
            = "-fx-background-color: #ffffff; "
            + "-fx-background-radius: 0; "
            + "-fx-text-fill: #000000; "
            + "-fx-font-size: 23";

    @FXML
    public void newPuzzle(ActionEvent event) {
        getGame().createRiddle();
        drawBoard();
    }

    @FXML
    public void checkPuzzle(ActionEvent event) {

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
    }
}
