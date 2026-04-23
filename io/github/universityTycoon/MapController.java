/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.GameModel;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.Event;
import io.github.universityTycoon.PlaceableObjects.LargeTrees;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.MapObjectPointer;
import io.github.universityTycoon.PlaceableObjects.Road;
import io.github.universityTycoon.PlaceableObjects.Tree;
import io.github.universityTycoon.PlaceableObjects.Water;
import java.time.LocalDateTime;
import java.util.Random;

public class MapController {
    private int tilesWide;
    private int tilesHigh;
    MapObject[][] mapObjects;
    private final int MIN_DISTANCE_TO_TOP = 2;

    public MapController(int tilesWide, int tilesHigh) {
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        this.mapObjects = new MapObject[tilesWide][tilesHigh];
        this.addObject(new Road(), 4, 6);
        this.addObject(new Road(), 4, 13);
        this.addObject(new Water(), 10, 7);
        this.addObject(new Water(), 17, 9);
        this.addObject(new Tree(), 16, 8);
        this.addObject(new Tree(), 16, 6);
        this.addObject(new Tree(), 5, 11);
        this.addObject(new Tree(), 6, 7);
        this.addObject(new Tree(), 5, 2);
        this.addObject(new Tree(), 25, 8);
        this.addObject(new Tree(), 29, 3);
        this.addObject(new Tree(), 22, 11);
        this.addObject(new LargeTrees(), 0, 13);
        this.addObject(new LargeTrees(), 0, 11);
        this.addObject(new LargeTrees(), 6, 1);
        this.addObject(new LargeTrees(), 18, 5);
        this.addObject(new LargeTrees(), 24, 12);
        this.addObject(new LargeTrees(), 27, 7);
    }

    public boolean addObject(MapObject object, int xPos, int yPos) {
        int j;
        int i;
        boolean objectFits = true;
        block0: for (i = 0; i < object.getWidth(); ++i) {
            for (j = 0; j < object.getHeight(); ++j) {
                if (object instanceof Building) {
                    if (xPos + i < this.tilesWide && yPos - j >= 2 && yPos - j < this.tilesHigh && this.mapObjects[xPos + i][yPos - j] == null) continue;
                    objectFits = false;
                    continue block0;
                }
                if (xPos + i < this.tilesWide && yPos - j < this.tilesHigh && this.mapObjects[xPos + i][yPos - j] == null) continue;
                objectFits = false;
                continue block0;
            }
        }
        if (objectFits) {
            this.mapObjects[xPos][yPos] = object;
            for (i = 0; i < object.getWidth(); ++i) {
                for (j = 0; j < object.getHeight(); ++j) {
                    if (i == 0 && j == 0) continue;
                    this.mapObjects[xPos + i][yPos - j] = new MapObjectPointer(object);
                }
            }
        }
        return objectFits;
    }

    public void removeObject(MapObject object, int xPos, int yPos) {
        this.mapObjects[xPos][yPos] = null;
        for (int i = 0; i < object.getWidth(); ++i) {
            for (int j = 0; j < object.getHeight(); ++j) {
                if (i == 0 && j == 0) continue;
                this.mapObjects[xPos + i][yPos - j] = null;
            }
        }
    }

    public void updateBuildings(LocalDateTime gameTime) {
        for (int x = 0; x < this.tilesWide; ++x) {
            for (int y = 0; y < this.tilesHigh; ++y) {
                if (!(this.mapObjects[x][y] instanceof Building)) continue;
                ((Building)this.mapObjects[x][y]).update(gameTime);
            }
        }
    }

    public int[] findRandomFreeTile() {
        Random random = new Random();
        int x = 0;
        int y = 0;
        for (int i = 0; i < 100; ++i) {
            x = random.nextInt(this.tilesWide);
            if (this.mapObjects[x][y = random.nextInt(this.tilesHigh)] != null) continue;
            return new int[]{x, y};
        }
        System.out.println("Tile already occupied.");
        return null;
    }

    public int[] placeEvent(GameEvent event) {
        Event eventToPlace = new Event(event);
        int[] freeTile = this.findRandomFreeTile();
        if (freeTile == null) {
            return null;
        }
        int x = freeTile[0];
        int y = freeTile[1];
        this.mapObjects[x][y] = eventToPlace;
        return freeTile;
    }

    public void updateEvents(LocalDateTime gameTime, GameModel gameModel) {
        for (int x = 0; x < this.tilesWide; ++x) {
            for (int y = 0; y < this.tilesHigh; ++y) {
                if (!(this.mapObjects[x][y] instanceof Event)) continue;
                ((Event)this.mapObjects[x][y]).getGameEvent().updateEvent(gameTime, x, y, gameModel);
            }
        }
    }
}

