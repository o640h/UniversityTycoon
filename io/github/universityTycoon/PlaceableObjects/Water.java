/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.Terrain;

public class Water
extends Terrain {
    public Water() {
        super("images/lake.png");
        this.width = 6;
        this.height = 4;
        this.satisfactionBonus = 15.0f;
    }
}

