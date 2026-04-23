/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.AccommodationBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class SmallAccommodation
extends AccommodationBuilding {
    public SmallAccommodation(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/sleep_building.png");
        this.name = "Small Accommodation";
        this.width = 2;
        this.height = 2;
        this.constructionGameTime = Duration.ofDays(30L);
        this.buildingCapacity = 300.0f;
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.studentRating = 4;
    }
}

