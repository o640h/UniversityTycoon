/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import java.time.LocalDateTime;

public class TeachingBuilding
extends Building {
    public TeachingBuilding(LocalDateTime constructionStartedAt, String texturePath) {
        super(constructionStartedAt, texturePath);
    }

    @Override
    public float calculateSatisfaction(int ownX, int ownY, MapObject[][] mapObjects) {
        return 0.0f;
    }
}

