package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import java.sql.SQLException;

public class Game {

    private UsersManagement usersMgmt;
    private Riddle riddle;
    private GameMatrix solution;
    private byte cellWriteValue;
    private long startTime;
    private long lastWriteTime;

    public Game(UsersManagement usersMgmt) {
        this.usersMgmt = usersMgmt;
    }

    public boolean writeCell(int row, int column) {
        lastWriteTime = System.currentTimeMillis();
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
        solution = Creator.createFull();
        riddle = Creator.createRiddle(solution);
        startTime = System.currentTimeMillis();
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

    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    public String timeElapsed() {
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - startTime) / 1000;
        return String.format("%d:%02d:%02d",
                elapsedSeconds / 3600,
                (elapsedSeconds % 3600) / 60,
                (elapsedSeconds % 60));
    }

    public boolean won() throws SQLException {

        if (riddle.getSetCount() == 81 && riddle.isValid()) {
            long score = lastWriteTime - startTime;
            int userId = usersMgmt.getLoggedInUser().getId();
            usersMgmt.getScoreDao().save(userId, score, lastWriteTime);
            return true;
        }

        return false;
    }

    public GameMatrix getSolution() {
        return solution;
    }
}
