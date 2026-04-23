/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.FoodAndDrinkBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class ConvenienceStore
extends FoodAndDrinkBuilding {
    public ConvenienceStore(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        this.name = "Convenience Store";
        this.width = 2;
        this.height = 2;
        this.constructionGameTime = Duration.ofDays(10L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 100.0f;
        this.studentRating = 3;
    }
}

