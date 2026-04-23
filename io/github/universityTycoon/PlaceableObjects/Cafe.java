/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.FoodAndDrinkBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class Cafe
extends FoodAndDrinkBuilding {
    public Cafe(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        this.name = "Cafe";
        this.width = 1;
        this.height = 1;
        this.constructionGameTime = Duration.ofDays(10L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 100.0f;
        this.studentRating = 4;
    }
}

