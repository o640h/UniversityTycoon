/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import io.github.universityTycoon.EventTypes;
import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.GameEventListener;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventManager {
    private Map<GameEvent, Integer> eventMap = new HashMap<GameEvent, Integer>();
    private GameEventListener listener;
    private float timeSinceLastEvent = 0.0f;
    private float eventInterval = 60.0f;
    private GameEvent currentActiveEvent;
    private GameEvent currentPlannedEvent;

    public EventManager(GameEventListener listener) {
        this.listener = listener;
        this.currentPlannedEvent = null;
        this.initialiseEventMap();
    }

    private void initialiseEventMap() {
        for (EventTypes eventType : EventTypes.values()) {
            this.eventMap.put(new GameEvent(eventType), eventType.getRarity());
        }
    }

    public GameEvent getCurrentActiveEvent() {
        return this.currentActiveEvent;
    }

    public void clearActiveCurrentEvent() {
        this.currentActiveEvent = null;
    }

    public GameEvent pickRandomEvent() {
        int totalRarity = 0;
        if (this.eventMap.isEmpty()) {
            throw new IllegalStateException("EventMap is empty");
        }
        for (Map.Entry<GameEvent, Integer> entry : this.eventMap.entrySet()) {
            if (!entry.getKey().isActive()) continue;
            totalRarity += entry.getValue().intValue();
        }
        if (totalRarity == 0) {
            throw new IllegalStateException("No active events available.");
        }
        int randomNumber = new Random().nextInt(totalRarity) + 1;
        for (Map.Entry<GameEvent, Integer> entry : this.eventMap.entrySet()) {
            GameEvent event = entry.getKey();
            if (!event.isActive() || (randomNumber -= entry.getValue().intValue()) > 0) continue;
            event.disableEvent();
            return event;
        }
        throw new IllegalStateException("Random event selection failed");
    }

    public void processEvents(float delta, LocalDateTime gameTimeGMT) {
        this.timeSinceLastEvent += delta;
        if (this.timeSinceLastEvent >= this.eventInterval && this.currentPlannedEvent == null) {
            GameEvent pickedEvent = this.pickRandomEvent();
            if (!pickedEvent.getEventType().getPlanned()) {
                this.currentActiveEvent = pickedEvent;
                this.timeSinceLastEvent -= this.eventInterval;
                this.eventInterval = this.generateRandomInterval();
            } else {
                this.currentPlannedEvent = pickedEvent;
            }
            if (pickedEvent != null) {
                pickedEvent.setEventStartedAt(gameTimeGMT);
                this.listener.raiseEvent(pickedEvent);
            }
        }
    }

    public void processPlannedEvent(float delta, LocalDateTime gameTimeGMT, GameEvent pickedEvent) {
        if (gameTimeGMT.isAfter(pickedEvent.getEventStartedAt().plusDays(110L))) {
            // empty if block
        }
        this.currentActiveEvent = pickedEvent;
        this.timeSinceLastEvent -= this.eventInterval;
        this.eventInterval = this.generateRandomInterval();
        this.currentPlannedEvent = null;
        this.timeSinceLastEvent += delta;
    }

    public GameEvent getPlannedEvent() {
        return this.currentPlannedEvent;
    }

    public float generateRandomInterval() {
        Random random = new Random();
        return 30.0f + random.nextFloat() * 30.0f;
    }
}

