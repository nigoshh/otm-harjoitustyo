package sudokuinsika.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(new User("test", "test", "test"));
        game.createRiddle();
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
    public void setUserSetsUserAndGetUserGetsUser() {
        User user1 = new User("Nyjah", "Nyjah", "Nyjah");
        game.setUser(user1);
        User user2 = game.getUser();
        assertEquals(user1.getUsername(), user2.getUsername());
        assertEquals(user1.getPwHash(), user2.getPwHash());
        assertEquals(user1.getEmail(), user2.getEmail());
    }
}
