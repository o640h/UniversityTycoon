/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.Terrain;

public class LargeTrees
extends Terrain {
    public LargeTrees() {
        super("images/large_trees.png");
        this.width = 2;
        this.height = 2;
        this.satisfactionBonus = 10.0f;
    }
}

