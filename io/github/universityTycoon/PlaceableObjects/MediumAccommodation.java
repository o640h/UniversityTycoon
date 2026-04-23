/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.AccommodationBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class MediumAccommodation
extends AccommodationBuilding {
    public MediumAccommodation(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/medium_sleep_building.png");
        this.name = "Medium Accommodation";
        this.width = 3;
        this.height = 3;
        this.constructionGameTime = Duration.ofDays(50L);
        this.buildingCapacity = 500.0f;
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.studentRating = 4;
    }
}

