package sudokuinsika.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SmallDigitsCellTest {

    SmallDigitsCell sdCell;

    @Before
    public void setUp() {
        sdCell = new SmallDigitsCell();
    }

    @Test
    public void writeOrDeleteWorksAndtoStringProducesCorrectStrings() {
        for (int i = 1; i <= 9; i++) {
            sdCell.writeOrDeleteDigit(i);
        }
        String str = "1 2 3\n4 5 6\n7 8 9";
        assertEquals(str, sdCell.toString());
        sdCell.writeOrDeleteDigit(3);
        sdCell.writeOrDeleteDigit(5);
        sdCell.writeOrDeleteDigit(7);
        str = "1 2   \n4    6\n   8 9";
        assertEquals(str, sdCell.toString());
        sdCell.writeOrDeleteDigit(3);
        sdCell.writeOrDeleteDigit(4);
        sdCell.writeOrDeleteDigit(6);
        str = "1 2 3\n        \n   8 9";
        assertEquals(str, sdCell.toString());
        sdCell.writeOrDeleteDigit(0);
        str = "        \n        \n        ";
        assertEquals(str, sdCell.toString());
    }
}
