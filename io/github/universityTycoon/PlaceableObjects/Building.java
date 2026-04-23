/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.math.MathUtils;
import io.github.universityTycoon.BuildingTypes;
import io.github.universityTycoon.PlaceableObjects.ArtsBuilding;
import io.github.universityTycoon.PlaceableObjects.BasketballCourt;
import io.github.universityTycoon.PlaceableObjects.Cafe;
import io.github.universityTycoon.PlaceableObjects.CommonRoom;
import io.github.universityTycoon.PlaceableObjects.ConvenienceStore;
import io.github.universityTycoon.PlaceableObjects.DiningHall;
import io.github.universityTycoon.PlaceableObjects.HumanitiesBuilding;
import io.github.universityTycoon.PlaceableObjects.LargeAccommodation;
import io.github.universityTycoon.PlaceableObjects.Library;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.MapObjectPointer;
import io.github.universityTycoon.PlaceableObjects.MediumAccommodation;
import io.github.universityTycoon.PlaceableObjects.SmallAccommodation;
import io.github.universityTycoon.PlaceableObjects.StemBuilding;
import io.github.universityTycoon.PlaceableObjects.StudentBar;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Building
extends MapObject {
    public Duration constructionGameTime;
    public LocalDateTime finishDate;
    public boolean isUnderConstruction = true;
    public float satisfactionBonus;
    public float buildingCapacity;
    LocalDateTime constructionStartedAt;
    int studentRating;

    public Building(LocalDateTime constructionStartedAt, String texturePath) {
        this.constructionStartedAt = constructionStartedAt;
        this.texturePath = texturePath;
        this.satisfactionBonus = 0.0f;
    }

    public Building(LocalDateTime constructionStartedAt, String texturePath, Duration constructionGameTime) {
        this.constructionStartedAt = constructionStartedAt;
        this.constructionGameTime = constructionGameTime;
        this.texturePath = texturePath;
        this.finishDate = constructionStartedAt.plus(constructionGameTime);
        this.satisfactionBonus = 0.0f;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTexturePath() {
        return this.texturePath;
    }

    @Override
    public float getSatisfactionBonus() {
        return this.satisfactionBonus;
    }

    public float getBuildingCapacity() {
        return this.buildingCapacity;
    }

    public Duration getConstructionGameTime() {
        return this.constructionGameTime;
    }

    @Override
    public boolean getIsStackable() {
        return this.isStackable;
    }

    @Override
    public float calculateSatisfaction(int ownX, int ownY, MapObject[][] mapObjects) {
        MapObjectPointer pointer;
        MapObject mapObject;
        int j;
        int i;
        ArrayList<MapObject> nearbyObjects = new ArrayList<MapObject>();
        float satisfaction = 0.0f;
        for (i = ownX; i < ownX + this.width + 6 && i < mapObjects.length; ++i) {
            for (j = ownY; j < ownY + this.height + 6 && j < mapObjects[i].length; ++j) {
                if (mapObjects[i][j] == null || mapObjects[i][j] == this) continue;
                mapObject = mapObjects[i][j];
                if (mapObject instanceof MapObjectPointer) {
                    pointer = (MapObjectPointer)mapObject;
                    if (nearbyObjects.contains(pointer.original) || pointer.original == this) continue;
                    nearbyObjects.add(pointer.original);
                    continue;
                }
                if (nearbyObjects.contains(mapObjects[i][j])) continue;
                nearbyObjects.add(mapObjects[i][j]);
            }
        }
        for (i = ownX; i > ownX - 6 && i >= 0; --i) {
            for (j = ownY; j > ownY - 6 && j >= 0; --j) {
                if (mapObjects[i][j] == null || mapObjects[i][j] == this) continue;
                mapObject = mapObjects[i][j];
                if (mapObject instanceof MapObjectPointer) {
                    pointer = (MapObjectPointer)mapObject;
                    if (nearbyObjects.contains(pointer.original) || pointer.original == this) continue;
                    nearbyObjects.add(pointer.original);
                    continue;
                }
                if (nearbyObjects.contains(mapObjects[i][j])) continue;
                nearbyObjects.add(mapObjects[i][j]);
            }
        }
        float minimumNearbyScore = 6.0f * this.buildingCapacity;
        float nearbyScore = 0.0f;
        for (MapObject i2 : nearbyObjects) {
            if (i2 instanceof Building) {
                Building building = (Building)i2;
                if (!building.isUnderConstruction) {
                    nearbyScore += building.getBuildingCapacity() * (float)building.getStudentRating();
                    continue;
                }
                satisfaction -= 10.0f;
                continue;
            }
            satisfaction += i2.getSatisfactionBonus();
        }
        satisfaction = nearbyScore >= minimumNearbyScore ? (satisfaction += 100.0f) : (satisfaction += 100.0f * (nearbyScore / minimumNearbyScore));
        if (satisfaction > 100.0f) {
            return 100.0f;
        }
        return satisfaction;
    }

    public void update(LocalDateTime currentGameTime) {
        if (currentGameTime.isAfter(this.finishDate)) {
            this.isUnderConstruction = false;
        }
    }

    public float getConstructionPercent(LocalDateTime currentGameTime) {
        Duration timePassed = Duration.between(this.constructionStartedAt, currentGameTime);
        float percent = MathUtils.clamp((float)timePassed.getSeconds() / (float)this.constructionGameTime.getSeconds(), 0.0f, 1.0f);
        return percent * 100.0f;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public int getStudentRating() {
        return this.studentRating;
    }

    public static Building getObjectFromEnum(BuildingTypes type, LocalDateTime time) {
        return switch (type) {
            case BuildingTypes.SmallAccommodation -> new SmallAccommodation(time);
            case BuildingTypes.MediumAccommodation -> new MediumAccommodation(time);
            case BuildingTypes.LargeAccommodation -> new LargeAccommodation(time);
            case BuildingTypes.DiningHall -> new DiningHall(time);
            case BuildingTypes.ConvenienceStore -> new ConvenienceStore(time);
            case BuildingTypes.Cafe -> new Cafe(time);
            case BuildingTypes.CommonRoom -> new CommonRoom(time);
            case BuildingTypes.StudentBar -> new StudentBar(time);
            case BuildingTypes.BasketballCourt -> new BasketballCourt(time);
            case BuildingTypes.StemBuilding -> new StemBuilding(time);
            case BuildingTypes.HumanitiesBuilding -> new HumanitiesBuilding(time);
            case BuildingTypes.ArtsBuilding -> new ArtsBuilding(time);
            case BuildingTypes.Library -> new Library(time);
            default -> null;
        };
    }
}

