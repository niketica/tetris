package nl.aniketic.tetris.gamestate;

import nl.aniketic.engine.display.PanelComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class SidePanelComponent extends PanelComponent {

    private static final int BLOCK_SIZE = 20;
    private static final int PREVIEW_PANEL_MAX_COLS = 4;
    private static final int PREVIEW_PANEL_MAX_ROWS = 4;
    private static final Font ARIAL_40 = new Font("Arial", Font.PLAIN, 20);

    private final int screenX;
    private final int screenY;
    private final Map<Integer, Color> colorMap;
    private Tetromino next;
    private int score;
    private int level;
    private boolean gameOver;

    public SidePanelComponent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.colorMap = createColorMap();
        this.score = 0;
        this.level = 1;
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        if (next != null) {
            int[][] nextShape = next.getShape();
            for (int row = 0; row < nextShape.length; row++) {
                for (int col = 0; col < nextShape[0].length; col++) {
                    int value = nextShape[row][col];
                    if (value > 0) {
                        g2.setColor(colorMap.get(value));
                        g2.fillRect(screenX + col * BLOCK_SIZE, screenY + row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }

        g2.setColor(Color.WHITE);
        for (int row = 0; row <= PREVIEW_PANEL_MAX_ROWS; row++) {
            for (int col = 0; col <= PREVIEW_PANEL_MAX_COLS; col++) {
                int xPos = screenX + col * BLOCK_SIZE;
                g2.drawLine(xPos, screenY, xPos, screenY + PREVIEW_PANEL_MAX_ROWS * BLOCK_SIZE);
            }
            int yPos = screenY + row * BLOCK_SIZE;
            g2.drawLine(screenX, yPos, screenX + PREVIEW_PANEL_MAX_COLS * BLOCK_SIZE, yPos);
        }

        g2.setFont(ARIAL_40);
        g2.drawString("Score: " + score, screenX, screenY + 200);
        g2.drawString("Level: " + level, screenX, screenY + 220);

        if (gameOver) {
            g2.drawString("Game Over!", screenX, screenY + 260);
            g2.drawString("Press [space] to start a new game.", screenX, screenY + 280);
        }
    }

    public void setNextTetromino(Tetromino next) {
        this.next = next;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private Map<Integer, Color> createColorMap() {
        Map<Integer, Color> colorMap = new HashMap<>();
        colorMap.put(1, Color.YELLOW);
        colorMap.put(2, Color.ORANGE);
        colorMap.put(3, Color.BLUE);
        colorMap.put(4, Color.CYAN);
        colorMap.put(5, Color.MAGENTA);
        colorMap.put(6, Color.GREEN);
        colorMap.put(7, Color.RED);
        return colorMap;
    }
}
