package sudokuinsika.domain;

public class Board {

    private Cell[][] cells;
    private Cell[][] solution;

    public Board() {
        this.cells = new Cell[9][9];
        this.solution = new Cell[9][9];
    }

    public int getDigit(int row, int column) {
        return cells[row][column].getDigit();
    }
}
