/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import io.github.universityTycoon.GameEvent;
import java.util.function.Consumer;

public class GameEventListener {
    private Consumer<GameEvent> eventHandler;

    public GameEventListener(Consumer<GameEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void raiseEvent(GameEvent event) {
        this.eventHandler.accept(event);
    }
}

