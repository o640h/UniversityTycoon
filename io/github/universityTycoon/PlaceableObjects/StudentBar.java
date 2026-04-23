/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.LeisureBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class StudentBar
extends LeisureBuilding {
    public StudentBar(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/fun_building.png");
        this.name = "Student Bar";
        this.width = 2;
        this.height = 4;
        this.constructionGameTime = Duration.ofDays(45L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 150.0f;
        this.studentRating = 5;
    }
}

