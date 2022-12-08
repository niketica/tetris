package nl.aniketic.tetris.gamestate;

import nl.aniketic.engine.display.DisplayManager;
import nl.aniketic.engine.gamestate.GameStateManager;
import nl.aniketic.engine.sound.Sound;
import nl.aniketic.tetris.controls.Key;
import nl.aniketic.tetris.controls.TetrisKeyHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TetrisGameStateManager extends GameStateManager {

    private static final int SCORE_CLEAR_ROW = 100;
    private static final int SCORE_MOVE_DOWN_USER_INPUT = 1;
    private static final Map<Integer, Integer> LEVEL_SCORE_MAP = new HashMap<>() {{
        put(1,  1_500);
        put(2,  3_000);
        put(3,  4_500);
        put(4,  6_000);
        put(5,  7_500);
        put(6,  9_000);
        put(7, 10_500);
        put(8, 12_000);
    }};

    private int[][] landed;

    private Tetromino nextTetromino;
    private Tetromino currentTetromino;

    private int blockFallCount;
    private int currentBlockFallCount;

    private int userInputCount = 10;
    private int currentUserInputCount;

    private MainPanel mainPanel;
    private SidePanel sidePanel;

    private Random random;

    private boolean gameOver;
    private int score;
    private int level;

    private Sound landingSound;
    private Sound clearSound;
    private Sound failSound;

    @Override
    protected void startGameState() {
        random = new Random();
        DisplayManager.createDisplay("TETRIS");
        DisplayManager.addKeyListener(new TetrisKeyHandler());

        landingSound = new Sound("/sound/land.wav");
        clearSound = new Sound("/sound/clear.wav");
        failSound = new Sound("/sound/fail.wav");

        startNewGame();
    }

    private void startNewGame() {
        gameOver = false;
        blockFallCount = 30;
        currentBlockFallCount = 0;
        currentUserInputCount = userInputCount;
        score = 0;
        level = 1;

        landed = createNewGamePanel();
        printGamePanel();

        mainPanel = new MainPanel();
        mainPanel.setLanded(landed);

        sidePanel = new SidePanel();

        createNextTetromino();
        setNewTetromino();

        gameObjects.add(mainPanel);
        gameObjects.add(sidePanel);
    }

    private void setNewTetromino() {
        currentTetromino = nextTetromino;
        mainPanel.setCurrent(currentTetromino);
        createNextTetromino();

        if (isCollision(currentTetromino.getShape(), currentTetromino.getTopLeftCol(),
                currentTetromino.getTopLeftRow())) {
            gameOver = true;
        }
    }

    private void createNextTetromino() {
        TetrominoShape tetrominoShape =
                (TetrominoShape) Arrays.stream(TetrominoShape.values()).toArray()[random.nextInt(
                        TetrominoShape.values().length)];
        nextTetromino = new Tetromino(
                tetrominoShape, landed[0].length / 2 - 1, 0
        );
        sidePanel.setNextTetromino(nextTetromino);
    }

    private void addTetrominoToGamePanel(Tetromino tetromino) {
        playSound(landingSound);
        int[][] tetrominoShape = tetromino.getShape();
        for (int row = 0; row < tetrominoShape.length; row++) {
            for (int col = 0; col < tetrominoShape[0].length; col++) {
                int value = tetrominoShape[row][col];
                if (value > 0) {
                    landed[tetromino.getTopLeftRow() + row][tetromino.getTopLeftCol() + col] = value;
                }
            }
        }
    }

    private void clearRows() {
        for (int row = landed.length - 1; row >= 0; row--) {
            clearRow(row);
        }
    }

    private void clearRow(int row) {
        boolean clearRow = true;
        for (int col = 0; col < landed[0].length; col++) {
            int value = landed[row][col];
            if (value == 0) {
                clearRow = false;
                break;
            }
        }

        if (clearRow) {
            for (int i = row; i >= 0; i--) {
                for (int col = 0; col < landed[0].length; col++) {
                    if (i > 0) {
                        landed[i][col] = landed[i - 1][col];
                    } else {
                        landed[i][col] = 0;
                    }
                }
            }
            setScore(score + SCORE_CLEAR_ROW);
            playSound(clearSound);
            clearRow(row);
        }
    }

    private int[][] createNewGamePanel() {
        return new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    private void printGamePanel() {
        System.out.println("NUMBER OF COL=" + landed[0].length);
        System.out.println("NUMBER OF ROW=" + landed.length);
        for (int row = 0; row < landed.length; row++) {
            for (int col = 0; col < landed[row].length; col++) {
                System.out.print("[" + landed[row][col] + "]");
            }
            System.out.println();
        }
    }

    @Override
    protected void updatePreGameState() {
        if (gameOver) {
            Key pressedKey = getPressedKey();
            if (pressedKey != null && pressedKey.isPressed() && pressedKey == Key.ROTATE) {
                mainPanel.deactivatePanelComponent();
                sidePanel.deactivatePanelComponent();
                startNewGame();
            }
        } else {
            updateGame();
        }
    }

    private void updateGame() {
        if (currentBlockFallCount >= blockFallCount) {
            currentBlockFallCount = 0;
            moveDown();
        } else {
            currentBlockFallCount++;
        }

        if (currentUserInputCount >= userInputCount) {
            Key pressedKey = getPressedKey();

            if (pressedKey != null) {
                currentUserInputCount = 0;
                System.out.println("Pressed: " + pressedKey);

                switch (pressedKey) {
                    case LEFT:
                        moveLeft();
                        break;
                    case RIGHT:
                        moveRight();
                        break;
                    case DOWN:
                        moveDown();
                        setScore(score + SCORE_MOVE_DOWN_USER_INPUT);
                        break;
                    case ROTATE:
                        rotate();
                        break;
                    default:
                        System.out.println("Unhandled key input.");
                }
            }
        } else {
            currentUserInputCount++;
        }

        if (gameOver) {
            playSound(failSound);
            sidePanel.setGameOver(gameOver);
        }
    }

    private void moveLeft() {
        int potentialTopLeftCol = currentTetromino.getTopLeftCol() - 1;
        if (!isOutOfBounds(currentTetromino.getShape(), potentialTopLeftCol, currentTetromino.getTopLeftRow())
                && !isCollision(currentTetromino.getShape(), potentialTopLeftCol, currentTetromino.getTopLeftRow())) {
            currentTetromino.setTopLeftCol(currentTetromino.getTopLeftCol() - 1);
        }
    }

    private void moveRight() {
        int potentialTopLeftCol = currentTetromino.getTopLeftCol() + 1;
        System.out.println("potentialTopLeftCol=" + potentialTopLeftCol);
        if (!isOutOfBounds(currentTetromino.getShape(), potentialTopLeftCol, currentTetromino.getTopLeftRow())
                && !isCollision(currentTetromino.getShape(), potentialTopLeftCol, currentTetromino.getTopLeftRow())) {
            int topLeftCol = currentTetromino.getTopLeftCol() + 1;
            currentTetromino.setTopLeftCol(topLeftCol);
        }
    }

    private void moveDown() {
        int potentialTopLeftRow = currentTetromino.getTopLeftRow() + 1;
        if (isLanded(currentTetromino.getShape(), potentialTopLeftRow)) {
            addTetrominoToGamePanel(currentTetromino);
            clearRows();
            setNewTetromino();
        } else if (isCollision(currentTetromino.getShape(), currentTetromino.getTopLeftCol(), potentialTopLeftRow)) {
            addTetrominoToGamePanel(currentTetromino);
            clearRows();
            setNewTetromino();
        } else {
            currentTetromino.setTopLeftRow(potentialTopLeftRow);
        }
    }

    private void rotate() {
        int[][] potentialShape = currentTetromino.rotate();

        if (isOutOfBounds(potentialShape, currentTetromino.getTopLeftCol(), currentTetromino.getTopLeftRow())
                || isCollision(potentialShape, currentTetromino.getTopLeftCol(), currentTetromino.getTopLeftRow())) {
            int topLeftColOffsetRight = currentTetromino.getTopLeftCol() + 1;
            if (isOutOfBounds(potentialShape, topLeftColOffsetRight, currentTetromino.getTopLeftRow())
                    || isCollision(potentialShape, topLeftColOffsetRight, currentTetromino.getTopLeftRow())) {
                int topLeftColOffsetLeft = currentTetromino.getTopLeftCol() - 1;
                if (!isOutOfBounds(potentialShape, topLeftColOffsetLeft, currentTetromino.getTopLeftRow())
                        && !isCollision(potentialShape, topLeftColOffsetLeft, currentTetromino.getTopLeftRow())) {
                    currentTetromino.setTopLeftCol(topLeftColOffsetLeft);
                    currentTetromino.setShape(potentialShape);
                }
            } else {
                currentTetromino.setTopLeftCol(topLeftColOffsetRight);
                currentTetromino.setShape(potentialShape);
            }
        } else {
            currentTetromino.setShape(potentialShape);
        }
    }

    private boolean isLanded(int[][] shape, int topLeftRow) {
        for (int row = shape.length - 1; row >= 0; row--) {
            for (int col = 0; col < shape[0].length; col++) {
                int shapeValue = shape[row][col];
                if (shapeValue > 0) {
                    int currentRow = topLeftRow + row;
                    if (currentRow >= landed.length) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isOutOfBounds(int[][] shape, int topLeftCol, int topLeftRow) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                int shapeValue = shape[row][col];
                if (shapeValue > 0) {
                    int currentCol = topLeftCol + col;
                    int currentRow = topLeftRow + row;
                    if (currentRow < 0 || currentRow >= landed.length || currentCol < 0 ||
                            currentCol >= landed[0].length) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCollision(int[][] shape, int topLeftCol, int topLeftRow) {
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                int shapeValue = shape[row][col];
                if (shapeValue > 0) {
                    int landedValue = landed[row + topLeftRow][col + topLeftCol];
                    if (landedValue > 0) {
                        // Other block occupies this space
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Key getPressedKey() {
        for (Key key : Key.values()) {
            if (key.isPressed()) {
                return key;
            }
        }
        return null;
    }

    public void setScore(int score) {
        this.score = score;
        sidePanel.setScore(score);
        setLevel();
    }

    public void setLevel() {
        Integer levelMaxScore = LEVEL_SCORE_MAP.get(level);
        if (levelMaxScore != null && score >= levelMaxScore) {
            level++;
            sidePanel.setLevel(level);
            blockFallCount -= 2;
        }
    }

    private void playSound(Sound sound) {
        sound.loadClip();
        sound.play();
    }

    @Override
    protected void updateEndGameState() {

    }
}
