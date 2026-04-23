/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.LeisureBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class BasketballCourt
extends LeisureBuilding {
    public BasketballCourt(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/fun_building.png");
        this.name = "Basketball Court";
        this.width = 3;
        this.height = 2;
        this.constructionGameTime = Duration.ofDays(60L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 30.0f;
        this.studentRating = 3;
    }
}

