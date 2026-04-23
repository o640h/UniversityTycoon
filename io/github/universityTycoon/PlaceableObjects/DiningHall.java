/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.FoodAndDrinkBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class DiningHall
extends FoodAndDrinkBuilding {
    public DiningHall(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        this.name = "Dining Hall";
        this.width = 4;
        this.height = 3;
        this.constructionGameTime = Duration.ofDays(10L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 100.0f;
        this.studentRating = 2;
    }
}

