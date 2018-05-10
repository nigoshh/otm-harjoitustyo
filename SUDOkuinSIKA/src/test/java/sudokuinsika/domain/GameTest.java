package sudokuinsika.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.createRiddle(29);
    }

    @Test
    public void isWritableReturnsTrueIfCellIsWritable() {
        game.getRiddle().setWritable(0, 0, true);
        assertTrue(game.isWritable(0, 0));
    }

    @Test
    public void isWritableReturnsFalseIfCellIsNotWritable() {
        game.getRiddle().setWritable(0, 0, false);
        assertFalse(game.isWritable(0, 0));
    }

    @Test
    public void writeCellReturnsTrueIfCellIsWritable() {
        game.getRiddle().setWritable(0, 0, true);
        assertTrue(game.writeCell(0, 0));
    }

    @Test
    public void writeCellReturnsFalseIfCellIsNotWritable() {
        game.getRiddle().setWritable(0, 0, false);
        assertFalse(game.writeCell(0, 0));
    }

    @Test
    public void writeCellWritesCorrectValueToCorrectCell() {
        game.getRiddle().setWritable(0, 0, true);
        game.setWriteValue((byte) 5);
        game.writeCell(0, 0);
        assertEquals(5, game.getRiddle().get(0, 0));
    }

    @Test
    public void cellToStringReturnsCorrectStringForBigDigitsOnly() {
        game.getRiddle().setWritable(0, 0, true);
        game.getRiddle().setWritable(0, 1, true);
        game.getRiddle().setWritable(0, 2, true);
        byte cell0 = 0;
        byte cell1 = 4;
        byte cell2 = 9;
        game.getRiddle().set(0, 0, cell0);
        game.getRiddle().set(0, 1, cell1);
        game.getRiddle().set(0, 2, cell2);
        assertEquals("", game.cellToString(0, 0));
        assertEquals("4", game.cellToString(0, 1));
        assertEquals("9", game.cellToString(0, 2));
    }

    @Test
    public void cellToStringReturnsCorrectStringAlsoForSmallDigits() {
        game.setWriteSmall(true);
        game.getRiddle().setWritable(0, 0, true);
        for (byte i = 1; i <= 9; i++) {
            game.setWriteValue(i);
            game.writeCell(0, 0);
        }
        String str = "1 2 3\n4 5 6\n7 8 9";
        assertEquals(str, game.cellToString(0, 0));
        for (byte i = 4; i <= 7; i++) {
            game.setWriteValue(i);
            game.writeCell(0, 0);
        }
        str = "1 2 3\n        \n   8 9";
        assertEquals(str, game.cellToString(0, 0));
        game.setWriteValue((byte) 0);
        game.writeCell(0, 0);
        assertEquals("", game.cellToString(0, 0));
        game.setWriteSmall(false);
        game.setWriteValue((byte) 5);
        game.writeCell(0, 0);
        assertEquals("5", game.cellToString(0, 0));
    }

    @Test
    public void timeElapsedReturnsCorrectlyFormattedString() {
        Pattern p = Pattern.compile("\\d+:\\d{2}:\\d{2}");
        Matcher m = p.matcher(game.timeElapsed());
        assertTrue(m.matches());
    }

    @Test
    public void getScoreReturnsANotNullScoreIfRiddleIsSolved() {
        game.solveRiddle();
        assertNotNull(game.getScore());
    }

    @Test
    public void getScoreReturnsNullIfRiddleIsntFilledButIsValid() {
        assertNull(game.getScore());
    }

    @Test
    public void getScoreReturnsNullIfRiddleIsntFilledAndIsntValid() {
        game.setWriteValue((byte) 1);
        int i = 0;
        writeSomeWrongNumbers:
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 8; column++) {
                if (game.getRiddle().getWritable(row, column)) {
                    game.writeCell(row, column);
                    i++;
                    if (i == 51) {
                        break;
                    }
                }
            }
        }
        assertNull(game.getScore());
    }

    @Test
    public void getScoreReturnsNullIfRiddleIsFilledButNotValid() {
        int[] cell = findFirstWritableCell();
        int row = cell[0];
        int column = cell[1];
        game.solveRiddle();
        byte cellValue = game.getRiddle().get(row, column);
        cellValue++;
        if (cellValue == 10) {
            cellValue = 1;
        }
        game.setWriteValue(cellValue);
        game.getRiddle().setWritable(row, column, true);
        game.writeCell(row, column);
        assertNull(game.getScore());
    }

    @Test
    public void resetTimerResetsStartTimeValue() throws InterruptedException {
        game.resetTimer();
        Thread.sleep(1);
        long now = System.currentTimeMillis();
        assertTrue(now > game.getStartTime());
        Thread.sleep(1);
        game.resetTimer();
        assertTrue(now < game.getStartTime());
    }

    @Test
    public void containsSmallDigitsReturnsTrueIfCellContainsSmallDigits() {
        game.getRiddle().setWritable(0, 0, true);
        game.setWriteSmall(true);
        for (byte i = 2; i <= 5; i++) {
            game.setWriteValue(i);
            game.writeCell(0, 0);
            assertTrue(game.containsSmallDigits(0, 0));
        }
    }

    @Test
    public void containsSmallDigitsReturnsFalseIfCellContainsNoSmallDigits() {
        assertFalse(game.containsSmallDigits(0, 0));
        game.getRiddle().setWritable(0, 0, true);
        for (byte i = 0; i < 5; i++) {
            game.setWriteValue(i);
            game.writeCell(0, 0);
            assertFalse(game.containsSmallDigits(0, 0));
        }
        game.setWriteSmall(true);
        for (byte i = 3; i <= 0; i--) {
            game.setWriteValue(i);
            game.writeCell(0, 0);
        }
        assertFalse(game.containsSmallDigits(0, 0));
    }

    @Test
    public void getLevelReturnsRiddleLevel() {
        assertEquals(29, game.getLevel());
        game.createRiddle(42);
        assertEquals(42, game.getLevel());
        game.createRiddle(69);
        assertEquals(69, game.getLevel());
    }

    @Test
    public void usingHelpMarksScoreAsHelpUsed() {
        game.checkPuzzle();
        game.solveRiddle();
        game.getScore().withHelp();
        assertTrue(game.getScore().withHelp());
    }

    private int[] findFirstWritableCell() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (game.getRiddle().getWritable(row, column)) {
                    int[] cell = new int[2];
                    cell[0] = row;
                    cell[1] = column;
                    return cell;
                }
            }
        }
        return new int[2];
    }
}
