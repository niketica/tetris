package nl.aniketic.tetris.gamestate;

import nl.aniketic.engine.gamestate.GameObject;

public class SidePanel extends GameObject {

    private final SidePanelComponent sidePanelComponent;

    public SidePanel() {
        this.panelComponent = new SidePanelComponent(240, 10);
        this.sidePanelComponent = (SidePanelComponent) this.panelComponent;
        this.activatePanelComponent();
    }

    @Override
    public void update() {

    }

    public void setNextTetromino(Tetromino next) {
        sidePanelComponent.setNextTetromino(next);
    }

    public void setScore(int score) {
        sidePanelComponent.setScore(score);
    }

    public void setLevel(int level) {
        sidePanelComponent.setLevel(level);
    }

    public void setGameOver(boolean gameOver) {
        sidePanelComponent.setGameOver(gameOver);
    }
}
