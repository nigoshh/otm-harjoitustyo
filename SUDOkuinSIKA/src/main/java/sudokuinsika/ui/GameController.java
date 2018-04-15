package sudokuinsika.ui;

import de.sfuhrm.sudoku.Riddle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import sudokuinsika.domain.Game;

public class GameController extends Controller {

    @FXML
    private GridPane grid;

    private Button[] cellButtons;

    @FXML
    private void createCellButtons(ActionEvent event) {
        cellButtons = new Button[81];
        int i = 0;
        for (int row = 1; row < 18; row += 2) {
            System.out.println("row " + row);
            for (int column = 1; column < 18; column += 2) {
                System.out.println("column " + column);
                Button b = new Button();
                b.setStyle("-fx-background-color: #ffffff; "
                        + "-fx-background-radius: 0; "
                        + "-fx-text-fill: #000000; "
                        + "-fx-font-weight: bold; "
                        + "-fx-font-size: 23");
                b.setPadding(new Insets(0));
                b.setMaxHeight(Double.MAX_VALUE);
                b.setMaxWidth(Double.MAX_VALUE);
                b.setText("3");
                grid.add(b, column, row);
                cellButtons[i] = b;
                i++;
            }
        }
    }

    @FXML
    private void debbbug(ActionEvent event) {
        System.out.println("debbbbbug");
    }

    @FXML
    public boolean writeCell(byte row, byte column, byte value) {
        return false;
    }

    @FXML
    public void writeBoard() {
        Riddle riddle = getGame().getRiddle();
        ObservableList<Node> buttons = grid.getChildren();
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 0; column++) {
                byte value = riddle.get(row, column);
                if (value != 0) {

                }
            }
        }
    }
}
