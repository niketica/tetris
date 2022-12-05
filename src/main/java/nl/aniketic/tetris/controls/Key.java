package nl.aniketic.tetris.controls;

import nl.aniketic.engine.controls.KeyObserver;

public enum Key {
    LEFT,
    RIGHT,
    DOWN,
    ROTATE;

    private final KeyObserver keyObserver;

    Key() {
        keyObserver = new KeyObserver(this.name());
    }

    public KeyObserver getKeyObserver() {
        return keyObserver;
    }

    public boolean isPressed() {
        return keyObserver.isPressed();
    }
}
