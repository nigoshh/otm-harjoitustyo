package sudokuinsika.domain;

public class SmallDigitsCell {

    private final boolean[] digits;
    private byte setDigitsCount;

    public SmallDigitsCell() {
        digits = new boolean[10];
        setDigitsCount = 0;
    }

    public void writeOrDeleteDigit(int digit) {
        if (digit == 0) {
            clearCell();
        } else {
            if (digits[digit]) {
                setDigitsCount--;
            } else {
                setDigitsCount++;
            }
            digits[digit] = !digits[digit];
        }
    }

    public boolean isEmpty() {
        return setDigitsCount == 0;
    }

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
        setDigitsCount = 0;
    }
}
