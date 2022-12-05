package nl.aniketic.tetris.controls;

import nl.aniketic.engine.controls.KeyHandler;

import java.awt.event.KeyEvent;

public class TetrisKeyHandler extends KeyHandler {

    public TetrisKeyHandler() {
        putKey(KeyEvent.VK_A, Key.LEFT.getKeyObserver());
        putKey(KeyEvent.VK_D, Key.RIGHT.getKeyObserver());
        putKey(KeyEvent.VK_S, Key.DOWN.getKeyObserver());
        putKey(KeyEvent.VK_SPACE, Key.ROTATE.getKeyObserver());
    }
}
