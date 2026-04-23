/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import io.github.universityTycoon.PlaceableObjects.AccommodationBuilding;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.FoodAndDrinkBuilding;
import io.github.universityTycoon.PlaceableObjects.LeisureBuilding;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.TeachingBuilding;
import java.util.ArrayList;

public class ScoreCalculator {
    ArrayList<Float> activeModifiers = new ArrayList();
    float currentScore;
    int currentStudents;

    public void addActiveModifier(Float modifier) {
        this.activeModifiers.add(modifier);
    }

    public void removeActiveModifier(Float modifier) {
        this.activeModifiers.remove(modifier);
    }

    public ArrayList<Float> getActiveModifiers() {
        return this.activeModifiers;
    }

    public float calculateScore(MapObject[][] mapObjects) {
        float campusScore = this.calculateCampusScore(mapObjects);
        float averageBuildingScore = 0.0f;
        for (int i = 0; i < mapObjects.length; ++i) {
            for (int j = 0; j < mapObjects[i].length; ++j) {
                MapObject mapObject = mapObjects[i][j];
                if (!(mapObject instanceof Building)) continue;
                Building building = (Building)mapObject;
                if (building.isUnderConstruction) continue;
                if (averageBuildingScore == 0.0f) {
                    averageBuildingScore += building.calculateSatisfaction(i, j, mapObjects);
                    continue;
                }
                averageBuildingScore = (averageBuildingScore + building.calculateSatisfaction(i, j, mapObjects)) / 2.0f;
            }
        }
        this.currentScore = campusScore == 0.0f ? 0.0f : (campusScore + averageBuildingScore) / 2.0f;
        for (Float i : this.activeModifiers) {
            this.currentScore += i.floatValue();
        }
        return Math.max(0.0f, Math.min(100.0f, this.currentScore));
    }

    private float calculateCampusScore(MapObject[][] mapObjects) {
        float currentStudents = 0.0f;
        float[] buildingTypeCapacities = new float[3];
        for (int i = 0; i < mapObjects.length; ++i) {
            for (int j = 0; j < mapObjects[i].length; ++j) {
                MapObject mapObject = mapObjects[i][j];
                if (!(mapObject instanceof Building)) continue;
                Building building = (Building)mapObject;
                if (building.isUnderConstruction) continue;
                if (building instanceof AccommodationBuilding) {
                    currentStudents += building.getBuildingCapacity();
                    continue;
                }
                if (building instanceof TeachingBuilding) {
                    buildingTypeCapacities[0] = buildingTypeCapacities[0] + building.getBuildingCapacity();
                    continue;
                }
                if (building instanceof FoodAndDrinkBuilding) {
                    buildingTypeCapacities[1] = buildingTypeCapacities[1] + building.getBuildingCapacity();
                    continue;
                }
                if (!(building instanceof LeisureBuilding)) continue;
                buildingTypeCapacities[2] = buildingTypeCapacities[2] + building.getBuildingCapacity();
            }
        }
        if (currentStudents == 0.0f) {
            return 0.0f;
        }
        float output = 0.0f;
        for (float i : buildingTypeCapacities) {
            if (i == 0.0f) continue;
            if (i >= currentStudents) {
                output += 100.0f;
                continue;
            }
            output += 100.0f * (i / currentStudents);
        }
        return output / 3.0f;
    }
}

