/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.AccommodationBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class LargeAccommodation
extends AccommodationBuilding {
    public LargeAccommodation(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/large_sleep_building.png");
        this.name = "Large Accommodation";
        this.width = 4;
        this.height = 4;
        this.constructionGameTime = Duration.ofDays(80L);
        this.buildingCapacity = 1000.0f;
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.studentRating = 3;
    }
}

