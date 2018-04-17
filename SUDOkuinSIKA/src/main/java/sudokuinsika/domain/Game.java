package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;

public class Game {

    private User user;
    private Riddle riddle;
    private byte cellWriteValue;

    public Game(User user) {
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean writeCell(int row, int column) {
        if (isWritable(row, column)) {
            riddle.set(row, column, cellWriteValue);
            return true;
        }
        return false;
    }

    public String cellToString(int row, int column) {
        byte value = riddle.get(row, column);
        String ret = "";
        if (value != 0) {
            ret += value;
        }
        return ret;
    }

    public void createRiddle() {
        GameMatrix matrix = Creator.createFull();
        riddle = Creator.createRiddle(matrix);
    }

    public boolean isWritable(int row, int column) {
        return riddle.getWritable(row, column);
    }

    public boolean checkPuzzle() {
        return riddle.isValid();
    }

    public void setCellWriteValue(byte cellWriteValue) {
        this.cellWriteValue = cellWriteValue;
    }

    public Riddle getRiddle() {
        return riddle;
    }

    public User getUser() {
        return user;
    }
}
