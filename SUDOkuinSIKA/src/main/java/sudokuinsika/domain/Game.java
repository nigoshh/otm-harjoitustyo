package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;

public class Game {

    private User user;

    public Game(User user) {
        this.user = user;
    }

    public void setU(User user) {
        this.user = user;
    }
}
