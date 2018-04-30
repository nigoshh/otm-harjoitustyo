package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Game {

    private UsersManagement usersMgmt;
    private Riddle riddle;
    private GameMatrix solution;
    private int level;
    private boolean help;
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

    public void createRiddle(int level) {
        boolean success = false;
        while (!success) {
            solution = Creator.createFull();
            riddle = Creator.createRiddle(solution);
            help = false;
            success = fillRiddle(level);
        }
        this.level = level;
        startTime = System.currentTimeMillis();
    }

    private boolean fillRiddle(int level) {
        int setCellsCount = riddle.getSetCount();
        if (setCellsCount > level) {
            return false;
        }
        if (riddle.getSetCount() < level) {
            fillRandomCells(level - setCellsCount);
        }
        return true;
    }

    private void fillRandomCells(int toBeFilledCount) {
        int freeCells = 81 - riddle.getSetCount();
        int[] toBeFilled = new Random().ints(0, freeCells)
                .distinct().limit(toBeFilledCount).toArray();
        Arrays.sort(toBeFilled);
        fillCellsFromArray(toBeFilled, riddle.getArray());
    }

    private void fillCellsFromArray(int[] toBeFilled, byte[][] riddleMatrix) {
        int freeCellCounter = 0;
        int i = 0;
        for (int row = 0; row < riddleMatrix.length; row++) {
            for (int column = 0; column < riddleMatrix.length; column++) {
                if (riddleMatrix[row][column] == 0) {
                    if (freeCellCounter == toBeFilled[i]) {
                        byte cellValue = solution.get(row, column);
                        riddle.set(row, column, cellValue);
                        riddle.setWritable(row, column, false);
                        i++;
                        if (i == toBeFilled.length) {
                            return;
                        }
                    }
                    freeCellCounter++;
                }
            }
        }
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
            usersMgmt.getScoreDao().save(
                    userId, level, help, score, lastWriteTime);
            return true;
        }

        return false;
    }

    public GameMatrix getSolution() {
        return solution;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public int getLevel() {
        return level;
    }
}
