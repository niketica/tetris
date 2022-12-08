package nl.aniketic.tetris.gamestate;

import nl.aniketic.engine.display.PanelComponent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class MainPanelComponent extends PanelComponent {

    private static final int BLOCK_SIZE = 20;

    private final int screenX;
    private final int screenY;
    private final Map<Integer, Color> colorMap;

    private int[][] landed;
    private Tetromino current;

    public MainPanelComponent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.colorMap = createColorMap();
    }

    @Override
    public void paintComponent(Graphics2D g2) {
        if (landed == null || current == null) {
            return;
        }

        int topLeftCol = current.getTopLeftCol();
        int topLeftRow = current.getTopLeftRow();
        int[][] currentShape = current.getShape();
        for (int row = 0; row< currentShape.length; row++) {
            for (int col = 0; col<currentShape[0].length; col++) {
                int x = screenX + topLeftCol * BLOCK_SIZE + col * BLOCK_SIZE;
                int y = screenY + topLeftRow * BLOCK_SIZE + row * BLOCK_SIZE;

                int value = currentShape[row][col];
                if (value > 0) {
                    g2.setColor(colorMap.get(value));
                    g2.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        for (int row = 0; row< landed.length; row++) {
            for (int col = 0; col<landed[0].length; col++) {
                int x = screenX + col * BLOCK_SIZE;
                int y = screenY + row * BLOCK_SIZE;

                int value = landed[row][col];
                if (value > 0) {
                    g2.setColor(colorMap.get(value));
                    g2.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        g2.setColor(Color.WHITE);
        for (int row = 0; row<=landed.length; row++) {
            for (int col = 0; col <= landed[0].length; col++) {
                int xPos = screenX + col * BLOCK_SIZE;
                g2.drawLine(xPos, screenY, xPos, screenY + landed.length * BLOCK_SIZE);
            }
            int yPos = screenY + row * BLOCK_SIZE;
            g2.drawLine(screenX, yPos, screenX + landed[0].length * BLOCK_SIZE, yPos);
        }
    }

    public void setLanded(int[][] landed) {
        this.landed = landed;
    }

    public void setCurrent(Tetromino current) {
        this.current = current;
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
