package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;

public class Game {

    private User user;
    private Riddle riddle;

    public Game(User user) {
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createRiddle() {
        GameMatrix matrix = Creator.createFull();
        riddle = Creator.createRiddle(matrix);
    }

    public Riddle getRiddle() {
        return riddle;
    }
}
