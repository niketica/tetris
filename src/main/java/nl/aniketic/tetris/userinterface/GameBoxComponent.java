package nl.aniketic.tetris.userinterface;

import nl.aniketic.engine.display.PanelComponent;
import nl.aniketic.tetris.tetromino.BlockGameObject;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameBoxComponent extends PanelComponent {

    public void paintComponent(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(screenX, screenY, width, height);

        g2.setColor(color);
        g2.drawRect(screenX, screenY, width, height);

        int maxCol = width / BlockGameObject.SIZE;
        int maxRow = height / BlockGameObject.SIZE;

        for (int row=0; row<maxRow; row++) {
            for (int col=0; col<maxCol; col++) {
                int x = screenX + col * BlockGameObject.SIZE;
                g2.drawLine(x, screenY, x, screenY + height);
            }
            int y = screenY + row * BlockGameObject.SIZE;
            g2.drawLine(screenX, y, screenX + width, y);
        }

    }
}
