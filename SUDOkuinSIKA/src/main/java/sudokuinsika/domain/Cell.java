package sudokuinsika.domain;

public class Cell {

    private byte digit;
    private boolean modifiable;

    public Cell(byte digit, boolean modifiable) {
        this.digit = digit;
        this.modifiable = modifiable;
    }

    public int getDigit() {
        return digit;
    }

    public boolean setDigit(byte digit) {
        if (modifiable) {
            this.digit = digit;
        }
        return false;
    }

    public boolean isModifiable() {
        return modifiable;
    }
}
