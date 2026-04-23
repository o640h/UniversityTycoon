/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import io.github.universityTycoon.PlaceableObjects.LeisureBuilding;
import java.time.Duration;
import java.time.LocalDateTime;

public class CommonRoom
extends LeisureBuilding {
    public CommonRoom(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/fun_building.png");
        this.name = "Common Room";
        this.width = 2;
        this.height = 2;
        this.satisfactionBonus = 0.0f;
        this.constructionGameTime = Duration.ofDays(45L);
        this.finishDate = constructionStartedAt.plus(this.constructionGameTime);
        this.buildingCapacity = 150.0f;
        this.studentRating = 4;
    }
}

