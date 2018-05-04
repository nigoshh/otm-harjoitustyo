package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents a sudoku game
 */
public class Game {

    private final UsersManagement usersMgmt;
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

    /**
     * If the given cell is writable, sets its value to the value contained
     * in object variable cellWriteValue and returns true; otherwise it does
     * nothing and returns false.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return success of the writing operation
     */
    public boolean writeCell(int row, int column) {
        lastWriteTime = System.currentTimeMillis();
        if (isWritable(row, column)) {
            riddle.set(row, column, cellWriteValue);
            return true;
        }
        return false;
    }

    /**
     * Returns a String corresponding to the value of the given cell.
     * When the cell's value is 0, it returns an empty String.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return a String corresponding to the value of the given cell
     */
    public String cellToString(int row, int column) {
        byte value = riddle.get(row, column);
        String ret = "";
        if (value != 0) {
            ret += value;
        }
        return ret;
    }

    /**
     * Creates a new Riddle of the given difficulty level.
     *
     * @param level number of cells whose value is known at the beginning
     * of the puzzle
     */
    public void createRiddle(int level) {
        boolean success = false;
        while (!success) {
            solution = Creator.createFull();
            riddle = Creator.createRiddle(solution);
            help = false;
            success = fillRiddle(level);
        }
        this.level = level;
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

    /**
     * Returns true if the given cell is writable, false if it isn't.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return true if the given cell is writable, false if it isn't
     */
    public boolean isWritable(int row, int column) {
        return riddle.getWritable(row, column);
    }

    /**
     * Checks that each digit appears at most once in each row, column and block.
     *
     * @return true if each digit appears at most once in each row, column and
     * block, false otherwise
     */
    public boolean checkPuzzle() {
        return riddle.isValid();
    }

    public void setCellWriteValue(byte cellWriteValue) {
        this.cellWriteValue = cellWriteValue;
    }

    public Riddle getRiddle() {
        return riddle;
    }

    /**
     * Resets the game's timer, by updating object variable startTime.
     */
    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Returns a String corresponding to the time elapsed since the last Riddle
     * was created.
     *
     * @see #createRiddle(int)
     * @return a String corresponding to the time elapsed since the last Riddle
     * was created
     */
    public String timeElapsed() {
        long now = System.currentTimeMillis();
        long elapsedSeconds = (now - startTime) / 1000;
        return String.format("%d:%02d:%02d",
                elapsedSeconds / 3600,
                (elapsedSeconds % 3600) / 60,
                (elapsedSeconds % 60));
    }

    /**
     * Returns true if the puzzle is solved, otherwise false.
     *
     * @return true if the puzzle is solved, otherwise false
     * @throws SQLException
     */
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

    public long getStartTime() {
        return startTime;
    }
}
