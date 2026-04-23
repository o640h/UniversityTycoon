/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.universityTycoon.BuildingTypes;
import io.github.universityTycoon.Events.GameEventHandler;
import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.GameModel;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.Event;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.Terrain;
import io.github.universityTycoon.PlayerInputHandler;
import io.github.universityTycoon.ScreenManager;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;

public class MainScreen
implements Screen {
    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    ShapeRenderer sr;
    Viewport viewport;
    HashMap<String, Texture> mapObjTextures;
    Texture backgroundTexture;
    Texture pauseTexture;
    Texture playTexture;
    Texture constructionTexture;
    Texture squareTexture;
    Texture rightArrowTexture;
    Texture leftArrowTexture;
    Texture percentTexture;
    BuildingTypes currentBuilding;
    Rectangle pausePlayBox;
    Rectangle buildingIcon;
    Rectangle rightButton;
    Rectangle leftButton;
    Rectangle background;
    Vector2 mousePos;
    boolean mouseDown;
    boolean placeMode;
    PlayerInputHandler playerInputHandler;
    boolean handlingEvent;
    boolean renderTutorial;
    String time;
    String dateTimeString;
    float delta;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/main.mp3"));
    final ScreenManager game;

    public MainScreen(ScreenManager main) {
        this.game = main;
        this.gameModel = new GameModel();
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.viewport = new FitViewport(16.0f, 9.0f);
        this.sr = new ShapeRenderer();
        this.shapeRenderer = new ShapeRenderer();
        this.mousePos = new Vector2(0.0f, 0.0f);
        this.playerInputHandler = new PlayerInputHandler();
        this.backgroundTexture = new Texture("images/map.png");
        this.pauseTexture = new Texture("ui/pause.png");
        this.playTexture = new Texture("ui/play.png");
        this.constructionTexture = new Texture("images/scaffold.png");
        this.squareTexture = new Texture("images/Gridsquare.png");
        this.rightArrowTexture = new Texture("ui/right-arrow.png");
        this.leftArrowTexture = new Texture("ui/left-arrow.png");
        this.percentTexture = new Texture("images/percent_plate.png");
        this.mapObjTextures = new HashMap();
        this.currentBuilding = this.gameModel.DEFAULT_SELECTED_BUILDING_TYPE;
        this.buildingIcon = new Rectangle();
        this.rightButton = new Rectangle();
        this.leftButton = new Rectangle();
        this.pausePlayBox = new Rectangle();
        this.background = new Rectangle();
        this.music.setVolume(0.5f);
        this.music.setLooping(true);
        this.music.play();
        this.handlingEvent = false;
        this.renderTutorial = false;
    }

    @Override
    public void render(float v) {
        this.delta = v;
        this.gameModel.runGame(v);
        this.input();
        this.logic();
        this.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    private void input() {
        if (this.playerInputHandler.getIsPauseJustPressed()) {
            if (this.gameModel.getIsPaused()) {
                this.resume();
            } else {
                this.pause();
            }
        }
        if (this.playerInputHandler.getIsMouseDown()) {
            this.mousePos = this.playerInputHandler.getMousePos();
            this.mouseDown = true;
        } else {
            this.mouseDown = false;
        }
        if (this.playerInputHandler.getKeyJustPressed(36) || this.handlingEvent) {
            this.renderTutorial = !this.renderTutorial && !this.handlingEvent;
        }
        if (this.playerInputHandler.getKeyJustPressed(8) && this.handlingEvent) {
            GameEventHandler currentEventHandler = this.gameModel.getHandlers().get((Object)this.gameModel.eventManager.getCurrentActiveEvent().getEventType());
            this.gameModel.scoreCalculator.addActiveModifier(Float.valueOf(currentEventHandler.getResponse(1).getEffect()));
            this.gameModel.eventManager.getCurrentActiveEvent().setEventDealtWith(true);
            this.gameModel.handledEvents.put(this.gameModel.eventManager.getCurrentActiveEvent(), Float.valueOf(currentEventHandler.getResponse(1).getEffect()));
            this.gameModel.eventManager.clearActiveCurrentEvent();
        } else if (this.playerInputHandler.getKeyJustPressed(9) && this.handlingEvent) {
            GameEventHandler currentEventHandler = this.gameModel.getHandlers().get((Object)this.gameModel.eventManager.getCurrentActiveEvent().getEventType());
            this.gameModel.scoreCalculator.addActiveModifier(Float.valueOf(currentEventHandler.getResponse(2).getEffect()));
            this.gameModel.eventManager.getCurrentActiveEvent().setEventDealtWith(true);
            this.gameModel.handledEvents.put(this.gameModel.eventManager.getCurrentActiveEvent(), Float.valueOf(currentEventHandler.getResponse(2).getEffect()));
            this.gameModel.eventManager.clearActiveCurrentEvent();
        } else if (this.playerInputHandler.getKeyJustPressed(10) && this.handlingEvent) {
            GameEventHandler currentEventHandler = this.gameModel.getHandlers().get((Object)this.gameModel.eventManager.getCurrentActiveEvent().getEventType());
            this.gameModel.scoreCalculator.addActiveModifier(Float.valueOf(currentEventHandler.getResponse(3).getEffect()));
            this.gameModel.eventManager.getCurrentActiveEvent().setEventDealtWith(true);
            this.gameModel.handledEvents.put(this.gameModel.eventManager.getCurrentActiveEvent(), Float.valueOf(currentEventHandler.getResponse(3).getEffect()));
            this.gameModel.eventManager.clearActiveCurrentEvent();
        }
    }

    private void logic() {
        Vector3 touch = new Vector3(this.mousePos.x, this.mousePos.y, 0.0f);
        this.viewport.getCamera().unproject(touch);
        this.gameModel.eventManager.processEvents(this.delta, this.gameModel.getGameTimeGMT());
        if (this.gameModel.eventManager.getPlannedEvent() != null) {
            this.gameModel.eventManager.processPlannedEvent(this.delta, this.gameModel.getGameTimeGMT(), this.gameModel.eventManager.getPlannedEvent());
        }
        this.handlingEvent = this.gameModel.eventManager.getCurrentActiveEvent() != null;
        if (this.mouseDown && this.buildingIcon.contains(touch.x, touch.y) && !this.handlingEvent && !this.renderTutorial) {
            this.placeMode = true;
        } else if (!this.mouseDown && this.placeMode && this.mousePos.y < 810.0f) {
            this.placeBuilding();
        } else if (!this.mouseDown && this.placeMode || this.handlingEvent && this.placeMode || this.renderTutorial && this.placeMode) {
            this.placeMode = false;
        }
        if (this.playerInputHandler.mouseJustClicked() && this.rightButton.contains(touch.x, touch.y)) {
            this.currentBuilding = BuildingTypes.values()[(this.currentBuilding.ordinal() + 1) % BuildingTypes.values().length];
        } else if (this.playerInputHandler.mouseJustClicked() && this.leftButton.contains(touch.x, touch.y)) {
            this.currentBuilding = this.currentBuilding.ordinal() - 1 < 0 ? BuildingTypes.values()[BuildingTypes.values().length - 1] : BuildingTypes.values()[this.currentBuilding.ordinal() - 1];
        }
        if (this.playerInputHandler.mouseJustClicked() && this.pausePlayBox.contains(touch.x, touch.y)) {
            if (this.gameModel.getIsPaused()) {
                this.resume();
            } else {
                this.pause();
            }
        }
        this.time = String.valueOf(Math.floorDiv((int)this.gameModel.getTimeRemainingSeconds(), 60)) + ":" + String.format("%02d", (int)this.gameModel.getTimeRemainingSeconds() % 60);
        this.dateTimeString = this.gameModel.getGameTimeGMT().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        if (this.gameModel.getTimeRemainingSeconds() < 0.0f) {
            this.music.stop();
            this.game.switchToScoreSummaryScreen();
        }
    }

    private void draw() {
        GameEvent plannedEvent;
        this.batch.begin();
        ScreenUtils.clear(Color.BLACK);
        this.viewport.apply();
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);
        this.pausePlayBox.set(-0.5f, 8.1f, 1.5f, 0.75f);
        this.batch.draw(this.backgroundTexture, 0.0f, 2.0f, 16.0f, 7.0f);
        if (!this.gameModel.isPaused) {
            this.batch.draw(this.pauseTexture, -0.5f, 8.1f, 1.5f, 0.75f);
        } else {
            this.batch.draw(this.playTexture, -0.5f, 8.1f, 1.5f, 0.75f);
        }
        this.drawMapObjects();
        GameModel.font.draw((Batch)this.batch, this.time, 7.6f, 8.5f);
        GameModel.font.draw((Batch)this.batch, "Satisfaction score: " + String.format("%.1f", Float.valueOf(this.gameModel.getSatisfactionScore())) + "%", 5.8f, 8.9f);
        GameModel.smallerFont.draw((Batch)this.batch, this.dateTimeString, 0.05f, 8.95f);
        GameModel.smallerFont.draw((Batch)this.batch, "Leisure Buildings: " + this.gameModel.getLeisureBuildingCount(), 13.15f, 8.9f);
        GameModel.smallerFont.draw((Batch)this.batch, "Teaching Buildings: " + this.gameModel.getTeachingBuildingCount(), 13.15f, 8.7f);
        GameModel.smallerFont.draw((Batch)this.batch, "Food & Drink Buildings: " + this.gameModel.getFoodAndDrinkBuildingCount(), 13.15f, 8.5f);
        GameModel.smallerFont.draw((Batch)this.batch, "Accommodation Buildings: " + this.gameModel.getAccommodationBuildingCount(), 13.15f, 8.3f);
        GameModel.smallerFont.draw((Batch)this.batch, "Press H for help!", 13.15f, 7.9f);
        this.batch.end();
        this.drawBuildingMenu();
        GameEvent currentActiveEvent = this.gameModel.getEventManager().getCurrentActiveEvent();
        if (currentActiveEvent != null || this.renderTutorial) {
            this.sr.setProjectionMatrix(this.viewport.getCamera().combined);
            this.sr.begin(ShapeRenderer.ShapeType.Filled);
            this.sr.setColor(0.32941177f, 0.47058824f, 0.49019608f, 1.0f);
            this.sr.rect(3.0f, 2.8f, 10.4f, 5.0f);
            this.sr.end();
        }
        this.batch.begin();
        Building building = Building.getObjectFromEnum(this.currentBuilding, this.gameModel.getGameTimeGMT());
        GameModel.blackFont.draw((Batch)this.batch, "Building type: " + building.getName(), 0.01f, 1.98f);
        GameModel.blackFont.draw((Batch)this.batch, "Building category: " + this.currentBuilding.category, 0.01f, 1.68f);
        GameModel.blackFont.draw((Batch)this.batch, "Building capacity: " + building.getBuildingCapacity(), 0.01f, 1.38f);
        GameModel.blackFont.draw((Batch)this.batch, "Student rating: " + building.getStudentRating() + "/5", 0.01f, 1.08f);
        GameModel.blackFont.draw((Batch)this.batch, "Time to build: " + building.getConstructionGameTime().getSeconds() / 86400L + " days", 0.01f, 0.78f);
        if (this.placeMode && this.mouseDown) {
            Building buildingToAdd = Building.getObjectFromEnum(this.currentBuilding, this.gameModel.getGameTimeGMT());
            Vector2 mouseGridPos = this.getMouseGridPos(this.mousePos);
            Vector2 screenPos = new Vector2(this.viewport.getWorldWidth() * mouseGridPos.x / (float)this.gameModel.getTilesWide(), this.viewport.getWorldHeight() - (this.viewport.getWorldHeight() - 2.0f) * (mouseGridPos.y + 1.0f) / (float)this.gameModel.getTilesHigh());
            float tileSizeOnScreen = this.viewport.getWorldWidth() / (float)this.gameModel.getTilesWide();
            String texturePath = buildingToAdd.getTexturePath();
            if (!this.mapObjTextures.containsKey(texturePath)) {
                this.mapObjTextures.put(texturePath, new Texture(texturePath));
            }
            this.batch.draw(this.mapObjTextures.get(buildingToAdd.getTexturePath()), screenPos.x, screenPos.y, tileSizeOnScreen * (float)buildingToAdd.getWidth(), tileSizeOnScreen * (float)buildingToAdd.getHeight());
        }
        if (currentActiveEvent != null) {
            String eventDescription = currentActiveEvent.getDescription();
            GameModel.blackFont.draw((Batch)this.batch, "An event has occurred!", 3.2f, 7.6f);
            GameModel.blackFont.draw((Batch)this.batch, eventDescription, 3.2f, 7.0f);
            GameModel.blackFont.draw((Batch)this.batch, "Press 1, 2 or 3 to choose what to do", 3.2f, 6.0f);
            GameEventHandler currentEventHandler = this.gameModel.getHandlers().get((Object)currentActiveEvent.getEventType());
            GameModel.blackFont.getData().setScale(0.0015f);
            for (int i = 0; i < 3; ++i) {
                GameModel.blackFont.draw((Batch)this.batch, Integer.toString(i + 1) + ": " + currentEventHandler.getResponse(i + 1).getDescription(), 3.2f, 5.4f - 0.4f * (float)i);
            }
            GameModel.blackFont.getData().setScale(0.002f);
        }
        if (this.renderTutorial) {
            GameModel.blackFont.draw((Batch)this.batch, "Welcome to University Tycoon!", 3.2f, 7.6f);
            GameModel.blackFont.draw((Batch)this.batch, "The aim of the game is maximising your student satisfaction score.", 3.2f, 7.0f);
            GameModel.blackFont.draw((Batch)this.batch, "You can do this by strategically placing buildings,", 3.2f, 6.6f);
            GameModel.blackFont.draw((Batch)this.batch, "and dealing with different events that occur during the game.", 3.2f, 6.3f);
            GameModel.blackFont.draw((Batch)this.batch, "To place a building, just drag the icon from the bottom of the screen", 3.2f, 5.9f);
            GameModel.blackFont.draw((Batch)this.batch, "or click on the arrow icons to swap between building types.", 3.2f, 5.6f);
            GameModel.blackFont.draw((Batch)this.batch, "When events occur, you can select between 3 courses of action,", 3.2f, 5.2f);
            GameModel.blackFont.draw((Batch)this.batch, "but make sure to choose wisely.", 3.2f, 4.9f);
            GameModel.blackFont.draw((Batch)this.batch, "The game only lasts for 5 minutes of real time, so be careful to", 3.2f, 4.5f);
            GameModel.blackFont.draw((Batch)this.batch, "consider the time it takes to construct your buildings!", 3.2f, 4.2f);
            GameModel.blackFont.draw((Batch)this.batch, "To exit the tutorial, press H again.", 3.2f, 3.6f);
        }
        if ((plannedEvent = this.gameModel.eventManager.getPlannedEvent()) != null) {
            GameModel.font.draw((Batch)this.batch, "Warning: a planned event will occur soon!", 2.0f, 4.0f);
            GameModel.font.draw((Batch)this.batch, "Incoming: " + plannedEvent.getDescription(), 2.0f, 3.6f);
        }
        this.batch.end();
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.rectLine(1570.0f, 938.0f, 1570.0f, 1080.0f, 5.0f, Color.BLACK, Color.BLACK);
        this.shapeRenderer.rectLine(1568.0f, 940.0f, 1920.0f, 940.0f, 4.0f, Color.BLACK, Color.BLACK);
        this.shapeRenderer.end();
    }

    private void placeBuilding() {
        Vector2 gridPos = this.getMouseGridPos(this.mousePos);
        Building buildingToAdd = Building.getObjectFromEnum(this.currentBuilding, this.gameModel.getGameTimeGMT());
        if (this.gameModel.mapController.addObject(buildingToAdd, (int)gridPos.x, (int)gridPos.y)) {
            switch (this.currentBuilding.category) {
                case "Accommodation": {
                    ++this.gameModel.accommodationBuildingCount;
                    break;
                }
                case "Leisure": {
                    ++this.gameModel.leisureBuildingCount;
                    break;
                }
                case "Food & Drink": {
                    ++this.gameModel.foodAndDrinkBuildingCount;
                    break;
                }
                case "Teaching": {
                    ++this.gameModel.teachingBuildingCount;
                }
            }
        }
    }

    public Vector2 getMouseGridPos(Vector2 mouseScreenPos) {
        Vector2 mouseWorldPos = this.viewport.unproject(new Vector2(mouseScreenPos.x, (float)this.viewport.getScreenHeight() - mouseScreenPos.y));
        int tileLocationX = (int)((float)this.gameModel.getTilesWide() * mouseWorldPos.x / this.viewport.getWorldWidth());
        int tileLocationY = (int)((float)this.gameModel.getTilesHigh() * mouseWorldPos.y / (this.viewport.getWorldHeight() - 2.0f));
        return new Vector2(tileLocationX, tileLocationY);
    }

    private void drawMapObjects() {
        MapObject[][] mapObjects = this.gameModel.getMapObjects();
        for (int i = 0; i < mapObjects.length; ++i) {
            for (int j = 0; j < mapObjects[i].length; ++j) {
                Object eventTexturePath;
                Object texturePath;
                MapObject mapObject;
                float tileSizeOnScreen = this.viewport.getWorldWidth() / (float)this.gameModel.getTilesWide();
                Vector2 screenPos = new Vector2((float)i * tileSizeOnScreen, this.viewport.getWorldHeight() - (float)(j + 1) * tileSizeOnScreen);
                if (this.placeMode && this.mouseDown) {
                    this.batch.draw(this.squareTexture, screenPos.x, screenPos.y, tileSizeOnScreen, tileSizeOnScreen);
                }
                if ((mapObject = mapObjects[i][j]) instanceof Building) {
                    Building building = (Building)mapObject;
                    texturePath = mapObjects[i][j].getTexturePath();
                    if (!this.mapObjTextures.containsKey(texturePath)) {
                        this.mapObjTextures.put((String)texturePath, new Texture((String)texturePath));
                    }
                    this.batch.draw(this.mapObjTextures.get(texturePath), screenPos.x, screenPos.y, tileSizeOnScreen * (float)building.getWidth(), tileSizeOnScreen * (float)building.getHeight());
                    if (!building.isUnderConstruction) continue;
                    this.batch.draw(this.constructionTexture, screenPos.x, screenPos.y, tileSizeOnScreen * (float)building.getWidth(), tileSizeOnScreen * (float)building.getHeight());
                    this.batch.draw(this.percentTexture, screenPos.x + 0.17f, screenPos.y + 0.25f, 0.71999997f, 0.48f);
                    GameModel.blackFont.draw((Batch)this.batch, String.format("%.0f%%", Float.valueOf(building.getConstructionPercent(this.gameModel.getGameTimeGMT()))), screenPos.x + 0.29f, screenPos.y + 0.61f);
                    continue;
                }
                texturePath = mapObjects[i][j];
                if (texturePath instanceof Event) {
                    Event event = (Event)texturePath;
                    eventTexturePath = event.getTexturePath();
                    if (eventTexturePath == null) continue;
                    if (!this.mapObjTextures.containsKey(eventTexturePath)) {
                        this.mapObjTextures.put((String)eventTexturePath, new Texture((String)eventTexturePath));
                    }
                    this.batch.draw(this.mapObjTextures.get(eventTexturePath), screenPos.x, screenPos.y, tileSizeOnScreen, tileSizeOnScreen);
                    continue;
                }
                eventTexturePath = mapObjects[i][j];
                if (!(eventTexturePath instanceof Terrain)) continue;
                Terrain terrain = (Terrain)eventTexturePath;
                texturePath = mapObjects[i][j].getTexturePath();
                if (!this.mapObjTextures.containsKey(texturePath)) {
                    this.mapObjTextures.put((String)texturePath, new Texture((String)texturePath));
                }
                this.batch.draw(this.mapObjTextures.get(texturePath), screenPos.x, screenPos.y, tileSizeOnScreen * (float)terrain.getWidth(), tileSizeOnScreen * (float)terrain.getHeight());
            }
        }
    }

    private void drawBuildingMenu() {
        this.background.set(0.0f, 0.0f, this.viewport.getScreenWidth(), 2.0f);
        this.buildingIcon.set(7.1f, 0.1f, 1.8f, 1.8f);
        this.rightButton.set(9.0f, 0.5f, 0.5f, 1.0f);
        this.leftButton.set(6.5f, 0.5f, 0.5f, 1.0f);
        this.sr.setProjectionMatrix(this.viewport.getCamera().combined);
        this.sr.begin(ShapeRenderer.ShapeType.Filled);
        this.sr.setColor(0.32941177f, 0.47058824f, 0.49019608f, 1.0f);
        this.sr.rect(this.background.x, this.background.y, this.background.width, this.background.height);
        this.sr.setColor(0.41960785f, 0.6f, 0.5921569f, 1.0f);
        this.sr.rect(this.buildingIcon.x, this.buildingIcon.y, this.buildingIcon.width, this.buildingIcon.height);
        this.sr.setColor(0.5411765f, 0.65882355f, 0.72156864f, 1.0f);
        this.sr.rect(this.rightButton.x, this.rightButton.y, this.rightButton.width, this.rightButton.height);
        this.sr.setColor(0.5411765f, 0.65882355f, 0.72156864f, 1.0f);
        this.sr.rect(this.leftButton.x, this.leftButton.y, this.leftButton.width, this.leftButton.height);
        this.sr.end();
        String currentBuildingTP = Building.getObjectFromEnum(this.currentBuilding, this.gameModel.getGameTimeGMT()).getTexturePath();
        this.batch.begin();
        if (!this.mapObjTextures.containsKey(currentBuildingTP)) {
            this.mapObjTextures.put(currentBuildingTP, new Texture(currentBuildingTP));
        }
        this.batch.draw(this.mapObjTextures.get(currentBuildingTP), this.buildingIcon.x, this.buildingIcon.y, this.buildingIcon.width, this.buildingIcon.height);
        this.batch.draw(this.rightArrowTexture, this.rightButton.x, 0.75f, this.rightButton.width, 0.5f);
        this.batch.draw(this.leftArrowTexture, this.leftButton.x, 0.75f, this.leftButton.width, 0.5f);
        this.batch.end();
    }

    public void restartGame() {
        this.gameModel = new GameModel();
    }

    @Override
    public void pause() {
        this.gameModel.isPaused = true;
        this.music.pause();
    }

    @Override
    public void resume() {
        this.gameModel.isPaused = false;
        this.music.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.sr.dispose();
        this.shapeRenderer.dispose();
        GameModel.font.dispose();
        GameModel.smallerFont.dispose();
        GameModel.blackFont.dispose();
        this.backgroundTexture.dispose();
        this.pauseTexture.dispose();
        this.playTexture.dispose();
        this.constructionTexture.dispose();
        this.squareTexture.dispose();
        this.rightArrowTexture.dispose();
        this.leftArrowTexture.dispose();
        this.percentTexture.dispose();
    }
}

