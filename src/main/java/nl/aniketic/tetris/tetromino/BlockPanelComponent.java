package nl.aniketic.tetris.tetromino;

import nl.aniketic.engine.display.PanelComponent;

import java.awt.Color;
import java.awt.Graphics2D;

public class BlockPanelComponent extends PanelComponent {

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(screenX, screenY, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(screenX, screenY, width, height);
    }

}
