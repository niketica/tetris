package nl.aniketic.tetris.tetromino;

import java.awt.Color;

public class TetrominoO extends Tetromino {

    public TetrominoO() {
        color = Color.ORANGE;
        blocks = new BlockGameObject[4];
        blocks[0] = new BlockGameObject(color);
        blocks[1] = new BlockGameObject(color);
        blocks[2] = new BlockGameObject(color);
        blocks[3] = new BlockGameObject(color);
    }

    @Override
    protected void rotateToPosition0() {
        blocks[0].setGridPosition(col, row + 1);
        blocks[1].setGridPosition(col + 1, row + 1);
        blocks[2].setGridPosition(col + 1, row);
        blocks[3].setGridPosition(col, row);
    }

    @Override
    protected void rotateToPosition1() {
        rotateToPosition0();
    }

    @Override
    protected void rotateToPosition2() {
        rotateToPosition0();
    }

    @Override
    protected void rotateToPosition3() {
        rotateToPosition0();
    }
}
