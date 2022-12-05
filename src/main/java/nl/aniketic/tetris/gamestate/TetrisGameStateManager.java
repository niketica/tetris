package nl.aniketic.tetris.gamestate;

import nl.aniketic.engine.display.DisplayManager;
import nl.aniketic.engine.gamestate.GameStateManager;
import nl.aniketic.tetris.controls.Key;
import nl.aniketic.tetris.controls.TetrisKeyHandler;
import nl.aniketic.tetris.tetromino.BlockGameObject;
import nl.aniketic.tetris.tetromino.Tetromino;
import nl.aniketic.tetris.tetromino.TetrominoI;
import nl.aniketic.tetris.tetromino.TetrominoL;
import nl.aniketic.tetris.tetromino.TetrominoO;
import nl.aniketic.tetris.tetromino.TetrominoS;
import nl.aniketic.tetris.tetromino.TetrominoT;
import nl.aniketic.tetris.userinterface.GameBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TetrisGameStateManager extends GameStateManager {

    private static final int MAX_COL = 10;
    private static final int MAX_ROW = 20;
    private static final int GAME_BOX_WIDTH = BlockGameObject.SIZE * 10;
    private static final int GAME_BOX_HEIGHT = BlockGameObject.SIZE * 20;

    private final GameBox mainGameBox = new GameBox(40, 40, GAME_BOX_WIDTH, GAME_BOX_HEIGHT);
    private final GameBox nextGameBox = new GameBox(40 + GAME_BOX_WIDTH + 40, 40, 100, 100);

    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private Random random;

    protected int horizontalInputCooldown = 10;
    protected int horizontalInputCount = horizontalInputCooldown;

    protected int verticalInputCooldown = 40;
    protected int verticalInputCount = verticalInputCooldown;

    protected int updatePositionCooldown = 10;
    protected int currentPositionCooldown = updatePositionCooldown;

    private List<Tetromino> previousTetrominos;

    private BlockGameObject[][] landedBlocks;

    @Override
    protected void startGameState() {
        random = new Random();
        TetrisKeyHandler tetrisKeyHandler = new TetrisKeyHandler();

        DisplayManager.createDisplay("TETRIS");
        DisplayManager.addKeyListener(tetrisKeyHandler);

        startNewGame();
    }

    private void startNewGame() {
        gameObjects.add(mainGameBox);
        mainGameBox.activatePanelComponent();

        gameObjects.add(nextGameBox);
        nextGameBox.activatePanelComponent();

        verticalInputCount = 0;
        landedBlocks = new BlockGameObject[MAX_COL][MAX_ROW];
        previousTetrominos = new ArrayList<>();
        nextTetromino = createNextTetromino();
        setCurrentTetromino();
    }

    @Override
    protected void updatePreGameState() {
        if (horizontalInputCount >= horizontalInputCooldown) {
            horizontalInputCount = 0;
            if (Key.LEFT.isPressed() && !currentTetromino.hitLeftSide(0)) {
                if (!isCollision(currentTetromino, -1, 0)) {
                    currentTetromino.moveLeft();
                }
            } else if (Key.RIGHT.isPressed() && !currentTetromino.hitRightSide(MAX_COL - 1)) {
                if (!isCollision(currentTetromino, 1, 0)) {
                    currentTetromino.moveRight();
                }
            }
        } else {
            horizontalInputCount++;
        }

        if (Key.DOWN.isPressed() && !currentTetromino.hitBottom(MAX_ROW - 1)) {
            if (!isCollision(currentTetromino, 0, 1)) {
                currentTetromino.moveDown();
            }
        }

        boolean landed = false;
        if (verticalInputCount >= verticalInputCooldown && !currentTetromino.hitBottom(MAX_ROW - 1)) {
            if (!isCollision(currentTetromino, 0, 1)) {
                currentTetromino.moveDown();
            } else {
                landed = true;
            }
            verticalInputCount = 0;
        } else {
            verticalInputCount++;
        }

        if (Key.ROTATE.isPressed() && currentPositionCooldown >= updatePositionCooldown) {
            currentPositionCooldown = 0;
            currentTetromino.rotate();
        } else if (currentPositionCooldown < updatePositionCooldown) {
            currentPositionCooldown++;
        }

        if (!landed) {
            if (currentTetromino.hitBottom(MAX_ROW - 1)) {
                landed = true;
            } else {
                landed = previousTetrominos.stream()
                        .anyMatch(previousTetromino -> currentTetromino.overlap(previousTetromino));
            }
        }

        checkRowClear();

        if (landed) {
            currentTetromino.setActive(false);
            previousTetrominos.add(currentTetromino);

            for (BlockGameObject block : currentTetromino.getBlocks()) {
                int col = block.getCol();
                int row = block.getRow();
                landedBlocks[col][row] = block;
            }

            setCurrentTetromino();
        }
    }

    public void checkRowClear() {

        for (int row=MAX_ROW - 1; row>=0; row--) {
            boolean checkCurrentRow = true;

            while (checkCurrentRow) {
                boolean rowClear = isRowClear(row);
                if (rowClear) {
                    System.out.println("ROW CLEAR!");

                    for (int col=0; col<MAX_COL; col++) {
                        landedBlocks[col][row].deactivatePanelComponent();
                    }

                    for (int row2=row - 1; row2>0; row2--) {
                        moveRowDown(row2);
                    }

                } else {
                    checkCurrentRow = false;
                }
            }
        }
    }

    private boolean isRowClear(int row) {
        for (int col=0; col<MAX_COL; col++) {
            if (landedBlocks[col][row] == null) {
                return false;
            }
        }
        return true;
    }

    private void moveRowDown(int row) {
        System.out.println("Move row " + row + " down to row " + (row + 1));
        for (int col=0; col<MAX_COL; col++) {

            BlockGameObject block = landedBlocks[col][row];
            if (block != null) {
                block.setGridPosition(
                        block.getCol(),
                        block.getRow() + 1
                );
            }

            landedBlocks[col][row + 1] = landedBlocks[col][row];
        }
    }

    @Override
    protected void updateEndGameState() {

    }

    private void setCurrentTetromino() {
        currentTetromino = nextTetromino;

        currentTetromino.setScreenOffSet(mainGameBox.getScreenX(), mainGameBox.getScreenY());

        currentTetromino.setGridPosition(5, -3);
        while (Arrays.stream(currentTetromino.getBlocks()).anyMatch(block -> block.getRow() < 0)) {
            currentTetromino.moveDown();
        }

        if (isCollision(currentTetromino, 0, 0)) {
            cleanupGame();
            startNewGame();
        } else {
            currentTetromino.setActive(true);
            nextTetromino = createNextTetromino();
        }
    }

    private void cleanupGame() {
        DisplayManager.clearGamePanel();
    }

    private Tetromino createNextTetromino() {
        Tetromino tetromino = createRandomTetromino();

        tetromino.setScreenOffSet(nextGameBox.getScreenX(), nextGameBox.getScreenY());
        tetromino.setGridPosition(1, 1);

        gameObjects.add(tetromino);
        tetromino.activatePanelComponent();
        tetromino.setActive(false);
        return tetromino;
    }

    private Tetromino createRandomTetromino() {
        switch (random.nextInt(5)) {
            case 0:
                return new TetrominoI();
            case 1:
                return new TetrominoL();
            case 2:
                return new TetrominoO();
            case 3:
                return new TetrominoS();
            case 4:
                return new TetrominoT();
            default:
                throw new IllegalStateException("Unhandled item");
        }
    }

    private boolean isCollision(Tetromino tetromino, int offsetCol, int offsetRow) {

        for (BlockGameObject block : tetromino.getBlocks()) {
            int col = block.getCol() + offsetCol;
            int row = block.getRow() + offsetRow;

            if (col >= 0 && col < MAX_COL && row >= 0 && row < MAX_ROW) {
                if (landedBlocks[col][row] != null) {
                    return true;
                }
            }
        }

        return false;
    }

}
