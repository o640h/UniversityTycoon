/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.LargeTrees;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.Road;
import io.github.universityTycoon.PlaceableObjects.Tree;
import io.github.universityTycoon.PlaceableObjects.Water;
import io.github.universityTycoon.TerrainTypes;
import java.time.LocalDateTime;

public class Terrain
extends MapObject {
    public float satisfactionBonus;

    public Terrain(String texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTexturePath() {
        return this.texturePath;
    }

    @Override
    public boolean getIsStackable() {
        return this.isStackable;
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

    public static Terrain getObjectFromEnum(TerrainTypes type, LocalDateTime time) {
        return switch (type) {
            case TerrainTypes.Road -> new Road();
            case TerrainTypes.Water -> new Water();
            case TerrainTypes.Tree -> new Tree();
            case TerrainTypes.LargeTrees -> new LargeTrees();
            default -> null;
        };
    }
}

