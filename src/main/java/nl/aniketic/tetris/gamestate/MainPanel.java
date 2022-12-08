package nl.aniketic.tetris.gamestate;

import nl.aniketic.engine.gamestate.GameObject;

public class MainPanel extends GameObject {

    private final MainPanelComponent gamePanelComponent;

    public MainPanel() {
        this.panelComponent = new MainPanelComponent(10, 10);
        this.gamePanelComponent = (MainPanelComponent) this.panelComponent;
        this.activatePanelComponent();
    }

    @Override
    public void update() {

    }

    public void setLanded(int[][] landed) {
        gamePanelComponent.setLanded(landed);
    }

    public void setCurrent(Tetromino current) {
        gamePanelComponent.setCurrent(current);
    }
}
