/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.universityTycoon.AchievementManager;
import io.github.universityTycoon.AudioSelector;
import io.github.universityTycoon.BuildingTypes;
import io.github.universityTycoon.EventManager;
import io.github.universityTycoon.EventTypes;
import io.github.universityTycoon.Events.AnonymousGrantHandler;
import io.github.universityTycoon.Events.CelebrityGuestHandler;
import io.github.universityTycoon.Events.CoffeeMachineBreakdownHandler;
import io.github.universityTycoon.Events.CulturalFairHandler;
import io.github.universityTycoon.Events.FloodingHandler;
import io.github.universityTycoon.Events.FootballVictoryHandler;
import io.github.universityTycoon.Events.GameEventHandler;
import io.github.universityTycoon.Events.GeeseInvasionHandler;
import io.github.universityTycoon.Events.GoodWeatherHandler;
import io.github.universityTycoon.Events.HurricaneHandler;
import io.github.universityTycoon.Events.StudentProtestHandler;
import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.GameEventListener;
import io.github.universityTycoon.MapController;
import io.github.universityTycoon.PlaceableObjects.Event;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.ScoreCalculator;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class GameModel {
    private final float YEARS_PER_MINUTE = 1.0f;
    private final int STARTING_YEAR = 2024;
    public final BuildingTypes DEFAULT_SELECTED_BUILDING_TYPE = BuildingTypes.SmallAccommodation;
    final float START_TIME_SECONDS = 300.0f;
    public float timeRemainingSeconds = 300.0f;
    public static BitmapFont font;
    public static BitmapFont smallerFont;
    public static BitmapFont blackFont;
    public int tilesWide = 32;
    public int tilesHigh = 14;
    int noBuildingTypes = 4;
    public int foodAndDrinkBuildingCount;
    public int accommodationBuildingCount;
    public int leisureBuildingCount;
    public int teachingBuildingCount;
    public float satisfactionScore;
    public boolean isPaused = false;
    GameState gameState;
    EventManager eventManager;
    GameEventListener eventListener = new GameEventListener(this::handleEvent);
    ScoreCalculator scoreCalculator;
    AudioSelector audioSelector;
    MapController mapController;
    AchievementManager achievementManager;
    private Map<EventTypes, GameEventHandler> handlers;
    HashMap<GameEvent, Float> handledEvents;

    public GameModel() {
        this.eventManager = new EventManager(this.eventListener);
        this.scoreCalculator = new ScoreCalculator();
        this.audioSelector = new AudioSelector();
        this.mapController = new MapController(this.tilesWide, this.tilesHigh);
        this.achievementManager = new AchievementManager();
        font = new BitmapFont(Gdx.files.internal("ui/font.fnt"), Gdx.files.internal("ui/font.png"), false);
        smallerFont = new BitmapFont(Gdx.files.internal("ui/font.fnt"), Gdx.files.internal("ui/font.png"), false);
        blackFont = new BitmapFont(Gdx.files.internal("ui/arial.fnt"), Gdx.files.internal("ui/arial.png"), false);
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.003f, 0.003f);
        smallerFont.setUseIntegerPositions(false);
        smallerFont.getData().setScale(0.0015f, 0.0015f);
        blackFont.setUseIntegerPositions(false);
        blackFont.getData().setScale(0.002f, 0.002f);
        this.handlers = new HashMap<EventTypes, GameEventHandler>();
        this.handledEvents = new HashMap();
    }

    public void runGame(float delta) {
        if (!this.getIsPaused()) {
            this.timeRemainingSeconds -= Gdx.graphics.getDeltaTime();
            this.mapController.updateBuildings(this.getGameTimeGMT());
            this.mapController.updateEvents(this.getGameTimeGMT(), this);
            this.satisfactionScore = this.scoreCalculator.calculateScore(this.mapController.mapObjects);
            this.achievementManager.checkContinuousAchievements(this.satisfactionScore, this.timeRemainingSeconds);
        }
    }

    public MapObject[][] getMapObjects() {
        return this.mapController.mapObjects;
    }

    public void removeEvent(int x, int y) {
        MapObject[][] mapObjects = this.getMapObjects();
        if (mapObjects[x][y] instanceof Event) {
            mapObjects[x][y] = null;
        }
    }

    public float getTimeElapsed() {
        return 300.0f - this.timeRemainingSeconds;
    }

    public float getTimeRemainingSeconds() {
        return this.timeRemainingSeconds;
    }

    public int getTilesWide() {
        return this.tilesWide;
    }

    public int getTilesHigh() {
        return this.tilesHigh;
    }

    public boolean getIsPaused() {
        return this.isPaused;
    }

    public int getFoodAndDrinkBuildingCount() {
        return this.foodAndDrinkBuildingCount;
    }

    public int getAccommodationBuildingCount() {
        return this.accommodationBuildingCount;
    }

    public int getLeisureBuildingCount() {
        return this.leisureBuildingCount;
    }

    public int getTeachingBuildingCount() {
        return this.teachingBuildingCount;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public int getNoBuildingTypes() {
        return this.noBuildingTypes;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public GameEvent getCurrentActiveEvent() {
        return this.eventManager.getCurrentActiveEvent();
    }

    public float getSatisfactionScore() {
        return this.satisfactionScore;
    }

    public ScoreCalculator getScoreCalculator() {
        return this.scoreCalculator;
    }

    public void setSatisfactionScore(float updatedScore) {
        this.satisfactionScore = updatedScore;
    }

    public void addDescriptionMessage(String message) {
        System.out.println("Event Message: " + message);
    }

    public MapController getMapController() {
        return this.mapController;
    }

    public Map<EventTypes, GameEventHandler> getHandlers() {
        return this.handlers;
    }

    public HashMap<GameEvent, Float> getHandledEvents() {
        return this.handledEvents;
    }

    public void handleEvent(GameEvent event) {
        GameEventHandler handler;
        EventTypes eventType = event.getEventType();
        if (!this.handlers.containsKey((Object)eventType) && (handler = this.createHandler(eventType)) != null) {
            this.handlers.put(eventType, handler);
        }
        if ((handler = this.handlers.get((Object)eventType)) == null) {
            throw new IllegalStateException("Handler for event type: " + eventType + "not found.");
        }
        handler.handle(event);
    }

    private GameEventHandler createHandler(EventTypes type) {
        return switch (type) {
            case EventTypes.FLOODING -> new FloodingHandler(this);
            case EventTypes.HURRICANE -> new HurricaneHandler(this);
            case EventTypes.COFFEE_MACHINE_BREAKDOWN -> new CoffeeMachineBreakdownHandler(this);
            case EventTypes.STUDENT_PROTEST -> new StudentProtestHandler(this);
            case EventTypes.CELEBRITY_GUEST -> new CelebrityGuestHandler(this);
            case EventTypes.FOOTBALL_VICTORY -> new FootballVictoryHandler(this);
            case EventTypes.ANONYMOUS_GRANT -> new AnonymousGrantHandler(this);
            case EventTypes.CULTURAL_FAIR -> new CulturalFairHandler(this);
            case EventTypes.GOOD_WEATHER -> new GoodWeatherHandler(this);
            case EventTypes.GEESE_INVASION -> new GeeseInvasionHandler(this);
            default -> null;
        };
    }

    public LocalDateTime getGameTimeGMT() {
        long inGameSeconds = (long)(this.getTimeElapsed() * 525600.0f * 1.0f);
        long startOfYearSeconds = LocalDateTime.of(2024, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);
        return LocalDateTime.ofEpochSecond(inGameSeconds + startOfYearSeconds, 0, ZoneOffset.UTC);
    }

    public static enum GameState {
        inProgress,
        paused,
        inMenu;

    }
}

