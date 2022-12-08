package nl.aniketic.tetris.gamestate;

public class Tetromino {

    private int[][] shape;
    private int topLeftCol;
    private int topLeftRow;

    public Tetromino(TetrominoShape tetrominoShape, int topLeftCol, int topLeftRow) {
        this.shape = tetrominoShape.getShape();
        this.topLeftCol = topLeftCol;
        this.topLeftRow = topLeftRow;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public void setShape(int[][] shape, int topLeftCol, int topLeftRow) {
        this.shape = shape;
        this.topLeftCol = topLeftCol;
        this.topLeftRow = topLeftRow;
    }

    public int getTopLeftCol() {
        return topLeftCol;
    }

    public void setTopLeftCol(int topLeftCol) {
        this.topLeftCol = topLeftCol;
    }

    public int getTopLeftRow() {
        return topLeftRow;
    }

    public void setTopLeftRow(int topLeftRow) {
        this.topLeftRow = topLeftRow;
    }

    public int[][] rotate() {
        int[][] newShape = new int[shape[0].length][shape.length];

        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                newShape[col][shape.length - 1 - row] = shape[row][col];
            }
        }

        return newShape;
    }
}
