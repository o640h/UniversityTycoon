/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.MapObject;

public class MapObjectPointer
extends MapObject {
    MapObject original;

    public MapObjectPointer(MapObject original) {
        this.original = original;
    }

    @Override
    public float calculateSatisfaction(int ownX, int ownY, MapObject[][] mapObjects) {
        return this.original.calculateSatisfaction(ownX, ownY, mapObjects);
    }

    @Override
    public String getName() {
        return this.original.getName();
    }

    @Override
    public String getTexturePath() {
        return this.original.getTexturePath();
    }

    @Override
    public boolean getIsStackable() {
        return this.original.getIsStackable();
    }

    @Override
    public int getWidth() {
        return this.original.getWidth();
    }

    @Override
    public int getHeight() {
        return this.original.getHeight();
    }

    @Override
    public float getSatisfactionBonus() {
        return this.original.getSatisfactionBonus();
    }
}

