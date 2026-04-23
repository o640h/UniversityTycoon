/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.TeachingBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class HumanitiesBuilding
extends TeachingBuilding {
    public HumanitiesBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/library_building.png");
        this.name = "Humanities Building";
        this.width = 2;
        this.height = 2;
        this.constructionGameTime = Duration.ofDays(60L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 400.0f;
        this.studentRating = 3;
    }
}

