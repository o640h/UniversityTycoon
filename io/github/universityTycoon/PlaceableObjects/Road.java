/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.Terrain;

public class Road
extends Terrain {
    public Road() {
        super("images/road.png");
        this.width = 1;
        this.height = 7;
        this.satisfactionBonus = -10.0f;
    }
}

