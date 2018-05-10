package sudokuinsika.domain;

/**
 * Represents a sudoku cell with small digits written in it.
 * It can contain a maximum of 9 digits at the same time.
 */
public class SmallDigitsCell {

    private final boolean[] digits;
    private byte writtenDigitsCount;

    /**
     * Sole constructor. Initializes an empty small-digits cell.
     */
    public SmallDigitsCell() {
        digits = new boolean[10];
        writtenDigitsCount = 0;
    }

    /**
     * Writes a small digit to this cell. If the cell already contains the given
     * digit, that digit only is deleted from the cell. Calling this method with
     * 0 will clear all digits from the cell.
     *
     * @param digit the digit to be written or deleted (0 clears the cell)
     */
    public void writeOrDeleteDigit(int digit) {
        if (digit == 0) {
            clearCell();
        } else {
            if (digits[digit]) {
                writtenDigitsCount--;
            } else {
                writtenDigitsCount++;
            }
            digits[digit] = !digits[digit];
        }
    }

    /**
     * Returns true if this cell doesn't contain any digits.
     *
     * @return true if the cell doesn't contain any digits, false otherwise
     */
    public boolean isEmpty() {
        return writtenDigitsCount == 0;
    }

    /**
     * Returns a string representing the digits contained in this cell.
     * To be used in a UI.
     *
     * @return a String representing the digits contained in this cell
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 9; i++) {
            if (digits[i]) {
                sb.append(i);
            } else {
                sb.append("  ");
            }
            if (i == 3 || i == 6) {
                sb.append("\n");
            } else if (i != 9) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private void clearCell() {
        for (int i = 1; i <= 9; i++) {
            digits[i] = false;
        }
        writtenDigitsCount = 0;
    }
}
