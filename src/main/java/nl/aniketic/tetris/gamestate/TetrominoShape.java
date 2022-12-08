package nl.aniketic.tetris.gamestate;

public enum TetrominoShape {
    O_SHAPE(
            new int[][]{
                    {1, 1},
                    {1, 1}}
    ),
    L_SHAPE(
            new int[][]{
                    {2, 0},
                    {2, 0},
                    {2, 2}}
    ),
    J_SHAPE(
            new int[][]{
                    {0, 3},
                    {0, 3},
                    {3, 3}}
    ),
    I_SHAPE(
            new int[][]{
                    {4},
                    {4},
                    {4},
                    {4}}
    ),
    T_SHAPE(
            new int[][]{
                    {0, 5, 0},
                    {5, 5, 5},
                    {0, 0, 0}}
    ),
    S_SHAPE(
            new int[][]{
                    {0, 6, 6},
                    {6, 6, 0}}
    ),
    Z_SHAPE(
            new int[][]{
                    {7, 7, 0},
                    {0, 7, 7}}
    );

    private final int[][] shape;

    TetrominoShape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] getShape() {
        return shape;
    }
}
