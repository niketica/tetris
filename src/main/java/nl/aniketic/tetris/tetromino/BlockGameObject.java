package nl.aniketic.tetris.tetromino;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Color;

public class BlockGameObject extends GameObject {

    public static final int SIZE = 20;

    protected int offsetX;
    protected int offsetY;

    private int col;
    private int row;

    public BlockGameObject(Color color) {
        panelComponent = new BlockPanelComponent();
        panelComponent.setWidth(BlockGameObject.SIZE);
        panelComponent.setHeight(BlockGameObject.SIZE);
        panelComponent.setColor(color);
    }

    @Override
    public void update() {
        updateScreen();
    }

    private void updateScreen() {
        panelComponent.setScreenX(offsetX + SIZE * col);
        panelComponent.setScreenY(offsetY + SIZE * row);
    }

    public void setGridPosition(int col, int row) {
        this.col = col;
        this.row = row;

        updateScreen();
    }

    public void setScreenOffSet(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
