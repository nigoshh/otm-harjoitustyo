package sudokuinsika.domain;

public class Cell {

    private int digit;
    private boolean modifiable;

    public Cell(int digit, boolean modifiable) {
        this.digit = digit;
        this.modifiable = modifiable;
    }

    public int getDigit() {
        return digit;
    }

    public boolean setDigit(int digit) {
        if (modifiable) {
            this.digit = digit;
        }
        return false;
    }

    public boolean isModifiable() {
        return modifiable;
    }
}
