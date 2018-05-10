package sudokuinsika.domain;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.Riddle;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents a game of sudoku.
 */
public class Game {

    private Riddle riddle;
    private GameMatrix solution;
    private SmallDigitsCell[][] smallDigits;
    private int level;
    private boolean help;
    private byte writeValue;
    private boolean writeSmall;
    private long startTime;
    private long lastWriteTime;

    /**
     * Sole constructor.
     */
    public Game() {
    }

    /**
     * Creates a new {@link #riddle} of the given difficulty {@link #level}. The
     * level indicates how many cells are already set at the beginning.
     * <p>
     * From {@link de.sfuhrm.sudoku.Creator#createRiddle(GameMatrix)} we get a
     * maximally cleared sudoku grid, meaning that no more cells can be cleared
     * without compromising the solution's uniqueness. Then we set more cells in
     * random order using the riddle's {@link #solution}, until we reach the
     * amount of set cells specified in the parameter.
     *
     * @see de.sfuhrm.sudoku.Creator#createRiddle(GameMatrix)
     * @param level number of cells whose value is known at the beginning
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
        smallDigits = new SmallDigitsCell[9][9];
        writeSmall = false;
    }

    /**
     * If the given cell {@link #isWritable(int, int)}, this Writes the value
     * contained in {@link #writeValue} to the given cell and returns true.
     * Otherwise it writes nothing and returns false. If {@link #writeSmall} is
     * true it writes to {@link #smallDigits}, otherwise to {@link #riddle}.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return success of the write operation
     */
    public boolean writeCell(int row, int column) {
        lastWriteTime = System.currentTimeMillis();
        if (isWritable(row, column)) {
            if (writeSmall) {
                writeSmallDigit(row, column);
            } else {
                writeBigDigit(row, column);
            }
            return true;
        }
        return false;
    }

    /**
     * Marks that help has been used by setting {@link #help} to true, then
     * checks if each digit appears at most once in each row, column and block.
     *
     * @return true if each digit appears at most once in each row, column
     * and block, false otherwise
     */
    public boolean checkPuzzle() {
        help = true;
        return riddle.isValid();
    }

    /**
     * Solves the {@link #riddle}.
     */
    public void solveRiddle() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                setWriteValue(solution.get(row, column));
                if (writeCell(row, column)) {
                    riddle.setWritable(row, column, false);
                }
            }
        }
    }

    /**
     * Checks if {@link #riddle} is solved, in which case it returns the game's
     * score, which is the time elapsed from {@link #riddle}'s creation until
     * all cells are filled correctly. If {@link #riddle} isn't solved yet, null
     * is returned.
     *
     * @return a Score or null
     */
    public Score getScore() {
        if (riddle.getSetCount() == 81 && riddle.isValid()) {
            Duration score = Duration.of(lastWriteTime - startTime, ChronoUnit.MILLIS);
            ZonedDateTime dateTime = Instant.ofEpochMilli(lastWriteTime).atZone(ZoneId.systemDefault());
            return new Score(null, level, help, score, dateTime);
        }
        return null;
    }

    /**
     * Resets the game's timer by updating {@link #startTime}.
     */
    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Checks if the given cell contains small digits.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return true if the cell contains at least one small digit, false otherwise
     */
    public boolean containsSmallDigits(int row, int column) {
        return smallDigits[row][column] != null && !smallDigits[row][column].isEmpty();
    }

    /**
     * Checks if the given cell is writable.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return true if the given cell is writable, false otherwise
     */
    public boolean isWritable(int row, int column) {
        return riddle.getWritable(row, column);
    }

    /**
     * Returns a string corresponding to the value of the given cell. When the
     * cell's value is 0, it returns an empty string.
     *
     * @param row the given cell's row
     * @param column the given cell's column
     * @return a String corresponding to the value of the given cell
     */
    public String cellToString(int row, int column) {
        if (containsSmallDigits(row, column)) {
            return smallDigits[row][column].toString();
        } else {
            byte value = riddle.get(row, column);
            String ret = "";
            if (value != 0) {
                ret += value;
            }
            return ret;
        }
    }

    /**
     * Returns a string corresponding to the time elapsed since {@link #riddle}
     * was created.
     *
     * @see #createRiddle(int)
     * @return a String corresponding to the time elapsed since {@link #riddle}
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

    private void writeBigDigit(int row, int column) {
        smallDigits[row][column] = null;
        riddle.set(row, column, writeValue);
    }

    private void writeSmallDigit(int row, int column) {
        if (smallDigits[row][column] == null) {
            riddle.set(row, column, (byte) 0);
            smallDigits[row][column] = new SmallDigitsCell();
        }
        smallDigits[row][column].writeOrDeleteDigit(writeValue);
    }

    public Riddle getRiddle() {
        return riddle;
    }

    public GameMatrix getSolution() {
        return solution;
    }

    public int getLevel() {
        return level;
    }

    public void setWriteValue(byte writeValue) {
        this.writeValue = writeValue;
    }

    public void setWriteSmall(boolean writeSmall) {
        this.writeSmall = writeSmall;
    }

    public long getStartTime() {
        return startTime;
    }
}
