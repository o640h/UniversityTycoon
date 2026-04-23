/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import io.github.universityTycoon.AchievementTypes;
import io.github.universityTycoon.EventTypes;
import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import java.util.ArrayList;
import java.util.HashMap;

public class AchievementManager {
    boolean[] achievements = new boolean[8];
    float previousSatisfactionScore = 0.0f;
    float timeReachedEightyPercent;

    public void checkGameEndAchievements(float satisfactionScore, MapObject[][] mapObjects, HashMap<GameEvent, Float> handledEvents) {
        if (satisfactionScore == 100.0f) {
            this.achievements[0] = true;
        } else if (satisfactionScore == 0.0f) {
            this.achievements[1] = true;
        }
        int buildingNumber = 0;
        ArrayList<String> buildingTypes = new ArrayList<String>();
        for (int i = 0; i < mapObjects.length; ++i) {
            for (int j = 0; j < mapObjects[i].length; ++j) {
                MapObject mapObject = mapObjects[i][j];
                if (!(mapObject instanceof Building)) continue;
                Building building = (Building)mapObject;
                ++buildingNumber;
                if (buildingTypes.contains(building.getName())) continue;
                buildingTypes.add(building.getName());
            }
        }
        if (buildingNumber == 5) {
            this.achievements[3] = true;
        } else if (buildingNumber > 30) {
            this.achievements[4] = true;
        }
        if (buildingTypes.size() == 13) {
            this.achievements[5] = true;
        }
        int avertedNegativeEvents = 0;
        int mishandledNeutralEvents = 0;
        for (GameEvent i : handledEvents.keySet()) {
            if (i.getEventType() == EventTypes.FLOODING || i.getEventType() == EventTypes.HURRICANE || i.getEventType() == EventTypes.COFFEE_MACHINE_BREAKDOWN || i.getEventType() == EventTypes.STUDENT_PROTEST) {
                if (handledEvents.get(i).floatValue() != 0.0f) continue;
                ++avertedNegativeEvents;
                continue;
            }
            if (i.getEventType() != EventTypes.GOOD_WEATHER && i.getEventType() != EventTypes.GEESE_INVASION || !(handledEvents.get(i).floatValue() < 0.0f)) continue;
            ++mishandledNeutralEvents;
        }
        if (avertedNegativeEvents >= 3) {
            this.achievements[6] = true;
        }
        if (mishandledNeutralEvents >= 2) {
            this.achievements[7] = true;
        }
    }

    public void checkContinuousAchievements(float satisfactionScore, float timeRemainingSeconds) {
        if (this.previousSatisfactionScore < 80.0f && satisfactionScore >= 80.0f) {
            this.timeReachedEightyPercent = 300.0f - timeRemainingSeconds;
        }
        if (satisfactionScore < 80.0f) {
            this.timeReachedEightyPercent = 301.0f;
        }
        if (300.0f - timeRemainingSeconds - this.timeReachedEightyPercent >= 180.0f && this.timeReachedEightyPercent != 301.0f) {
            this.achievements[2] = true;
        }
        this.previousSatisfactionScore = satisfactionScore;
    }

    public float calculateNewSatisfactionScore(float satisfactionScore) {
        for (int i = 0; i < this.achievements.length; ++i) {
            if (!this.achievements[i]) continue;
            satisfactionScore += AchievementTypes.values()[i].scoreBonus;
        }
        if (satisfactionScore > 100.0f) {
            this.achievements[0] = true;
            if (true) {
                return 101.0f;
            }
            return 100.0f;
        }
        if (satisfactionScore == 0.0f) {
            this.achievements[1] = true;
            if (true) {
                return -1.0f;
            }
            return 0.0f;
        }
        return satisfactionScore;
    }

    public boolean[] getAchievements() {
        return this.achievements;
    }
}

