package nl.aniketic.tetris.userinterface;

import nl.aniketic.engine.gamestate.GameObject;

import java.awt.Color;

public class GameBox extends GameObject {

    private int screenX;
    private int screenY;
    private int width;
    private int height;

    public GameBox(int screenX, int screenY, int width, int height) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.width = width;
        this.height = height;

        panelComponent = new GameBoxComponent();
        panelComponent.setColor(Color.WHITE);
        panelComponent.setScreenX(screenX);
        panelComponent.setScreenY(screenY);
        panelComponent.setWidth(width);
        panelComponent.setHeight(height);
    }

    @Override
    public void update() {

    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
