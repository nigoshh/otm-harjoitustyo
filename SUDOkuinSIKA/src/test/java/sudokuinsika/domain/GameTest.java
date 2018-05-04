package sudokuinsika.domain;

import de.sfuhrm.sudoku.GameMatrix;
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

    private void solve() {
        GameMatrix solution = game.getSolution();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                game.setCellWriteValue(solution.get(row, column));
                game.writeCell(row, column);
            }
        }
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
        game.setCellWriteValue((byte) 5);
        game.writeCell(0, 0);
        assertEquals(5, game.getRiddle().get(0, 0));
    }

    @Test
    public void cellToStringReturnsCorrectString() {
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
    public void timeElapsedReturnsCorrectlyFormattedString() {
        Pattern p = Pattern.compile("\\d+:\\d{2}:\\d{2}");
        Matcher m = p.matcher(game.timeElapsed());
        assertTrue(m.matches());
    }

    @Test
    public void wonReturnsTrueIfRiddleIsSolved() throws SQLException {
        solve();
        assertTrue(game.won());
    }

    @Test
    public void wonReturnsFalseIfRiddleIsntFilledButIsValid() throws SQLException {
        assertFalse(game.won());
    }

    @Test
    public void wonReturnsFalseIfRiddleIsntFilledAndIsntValid() throws SQLException {
        game.setCellWriteValue((byte) 1);
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
        solve();
        changeOneCell:
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (game.getRiddle().getWritable(row, column)) {
                    byte cellValue = game.getRiddle().get(row, column);
                    cellValue++;
                    if (cellValue == 10) {
                        cellValue = 1;
                    }
                    game.setCellWriteValue(cellValue);
                    game.writeCell(row, column);
                    break changeOneCell;
                }
            }
        }
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
}
