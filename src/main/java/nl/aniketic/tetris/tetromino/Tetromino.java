package nl.aniketic.tetris.tetromino;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Color;
import java.util.Arrays;

public abstract class Tetromino extends GameObject {

    protected int position = 0;
    protected boolean active = true;
    protected Color color;

    protected BlockGameObject[] blocks;

    protected int offsetX;
    protected int offsetY;

    protected int col;
    protected int row;

    @Override
    public void update() {
        if (!active) {
            return;
        }

        updateBlocks();
    }

    public void rotate() {
        position++;
        if (position > 3) {
            position = 0;
        }

        updatePosition();
    }

    private void updatePosition() {
        switch (position) {
            case 0:
                rotateToPosition0();
                break;
            case 1:
                rotateToPosition1();
                break;
            case 2:
                rotateToPosition2();
                break;
            case 3:
                rotateToPosition3();
                break;
            default:
                throw new IllegalStateException("Position not known: " + position);
        }
    }

    public boolean hitLeftSide(int value) {
        return Arrays.stream(blocks).anyMatch(block -> block.getCol() <= value);
    }

    public boolean hitRightSide(int value) {
        return Arrays.stream(blocks).anyMatch(block -> block.getCol() >= value);
    }

    public boolean hitBottom(int value) {
        return Arrays.stream(blocks).anyMatch(block -> block.getRow() == value);
    }

    public void moveLeft() {
        setGridPosition(col - 1, row);
    }

    public void moveRight() {
        setGridPosition(col + 1, row);
    }

    public void moveUp() {
        setGridPosition(col, row - 1);
    }

    public void moveDown() {
        setGridPosition(col, row + 1);
    }

    protected void updateBlocks() {
        for (BlockGameObject block : blocks) {
            block.update();
        }
    }

    @Override
    public void activatePanelComponent() {
        if (blocks != null) {
            for (BlockGameObject block : blocks) {
                block.activatePanelComponent();
            }
        }
    }

    @Override
    public void deactivatePanelComponent() {
        if (blocks != null) {
            for (BlockGameObject block : blocks) {
                block.deactivatePanelComponent();
            }
        }
    }

    public void setGridPosition(int col, int row) {
        this.col = col;
        this.row = row;
        updatePosition();
    }

    public void setScreenOffSet(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;

        for (BlockGameObject block : blocks) {
            block.setScreenOffSet(x, y);
        }
    }

    public boolean overlap(Tetromino other) {

        for (BlockGameObject otherBlock : other.blocks) {

            for (BlockGameObject block : blocks) {
                if (block.getCol() == otherBlock.getCol() && block.getRow() == otherBlock.getRow()) {
                    return true;
                }
            }

        }


        return false;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    protected abstract void rotateToPosition0();

    protected abstract void rotateToPosition1();

    protected abstract void rotateToPosition2();

    protected abstract void rotateToPosition3();

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public BlockGameObject[] getBlocks() {
        return blocks;
    }
}
