/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.PlaceableObjects.MapObject;

public class Event
extends MapObject {
    private GameEvent gameEvent;

    public Event(GameEvent gameEvent) {
        if (gameEvent == null) {
            throw new IllegalArgumentException("GameEvent cannot be null");
        }
        this.gameEvent = gameEvent;
    }

    public GameEvent getGameEvent() {
        return this.gameEvent;
    }

    @Override
    public String getName() {
        return this.gameEvent.getEventType().name();
    }

    @Override
    public String getTexturePath() {
        return this.gameEvent.getIconPath();
    }

    @Override
    public boolean getIsStackable() {
        return false;
    }

    @Override
    public float calculateSatisfaction(int ownX, int ownY, MapObject[][] mapObjects) {
        return 1.0f;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public float getSatisfactionBonus() {
        return this.satisfactionBonus;
    }
}

