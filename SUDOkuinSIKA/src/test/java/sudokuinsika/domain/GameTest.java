package sudokuinsika.domain;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sudokuinsika.dao.FakeScoreDao;
import sudokuinsika.dao.FakeUserDao;

public class GameTest {

    private Game game;
    private UsersManagement usersMgmt;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        usersMgmt = new UsersManagement(new FakeUserDao(), new FakeScoreDao());
        usersMgmt.setPwIterations(1);
        String pw = "password";
        char[] password = pw.toCharArray();
        usersMgmt.createUser("test", password, "test");
        usersMgmt.logIn("test", password);
        game = new Game(usersMgmt);
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
    public void wonReturnsTrueIfRiddleIsSolved() throws SQLException {
        game.solve();
        assertTrue(game.won());
    }

    @Test
    public void wonReturnsFalseIfRiddleIsntFilledButIsValid() throws SQLException {
        assertFalse(game.won());
    }

    @Test
    public void wonReturnsFalseIfRiddleIsntFilledAndIsntValid() throws SQLException {
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
        assertFalse(game.won());
    }

    @Test
    public void wonReturnsFalseIfRiddleIsFilledButNotValid() throws SQLException {
        int[] cell = findFirstWritableCell();
        int row = cell[0];
        int column = cell[1];
        game.solve();
        byte cellValue = game.getRiddle().get(row, column);
        cellValue++;
        if (cellValue == 10) {
            cellValue = 1;
        }
        game.setWriteValue(cellValue);
        game.getRiddle().setWritable(row, column, true);
        game.writeCell(row, column);
        assertFalse(game.won());
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
    public void usingHelpMarksScoreAsHelpUsed() throws SQLException {
        game.checkPuzzle();
        game.solve();
        game.won();
        assertEquals(1, usersMgmt.getScoreDao().findScores(29, true).size());
        assertTrue(usersMgmt.getScoreDao().findScores(29, false).isEmpty());
    }

    @Test
    public void wonSavesScoresCorrectly() throws SQLException {
        game.solve();
        game.won();
        assertEquals(1, usersMgmt.getScoreDao().findScores(29, false).size());
        assertTrue(usersMgmt.getScoreDao().findScores(29, true).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(23, false).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(23, true).isEmpty());
        assertEquals(1, usersMgmt.getScoreDao().findScores(new User("test"), 29, false).size());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("test"), 29, true).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("test"), 23, false).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("test"), 23, true).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("wrong"), 29, false).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("wrong"), 29, true).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("wrong"), 23, false).isEmpty());
        assertTrue(usersMgmt.getScoreDao().findScores(new User("wrong"), 23, true).isEmpty());
    }
}
