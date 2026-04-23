/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

public abstract class MapObject {
    String name = "";
    String texturePath = "";
    boolean isStackable = false;
    int width = 1;
    int height = 1;
    float satisfactionBonus = 0.0f;

    public abstract float calculateSatisfaction(int var1, int var2, MapObject[][] var3);

    public abstract String getName();

    public abstract String getTexturePath();

    public abstract boolean getIsStackable();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract float getSatisfactionBonus();
}

