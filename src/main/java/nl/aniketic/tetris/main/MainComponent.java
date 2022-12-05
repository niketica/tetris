package nl.aniketic.tetris.main;

import nl.aniketic.tetris.gamestate.TetrisGameStateManager;

public class MainComponent {

    public static void main(String[] args) {
        new TetrisGameStateManager().startGame();
    }

}
