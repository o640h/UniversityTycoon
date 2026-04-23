/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import io.github.universityTycoon.EventTypes;
import io.github.universityTycoon.GameModel;
import io.github.universityTycoon.PlaceableObjects.Event;
import java.time.Duration;
import java.time.LocalDateTime;

public class GameEvent {
    private EventTypes eventType;
    private String description;
    private float rarity;
    private String iconPath;
    private Event mapEvent;
    private boolean isActive;
    private LocalDateTime eventStartedAt;
    private float responseEffect;
    private String responseDescription;
    private boolean dealtWith;

    public GameEvent(EventTypes eventType) {
        this.eventType = eventType;
        this.isActive = true;
        this.description = eventType.getDescription();
        this.rarity = eventType.getRarity();
        this.iconPath = eventType.getIconPath();
        this.mapEvent = new Event(this);
        this.dealtWith = false;
    }

    public EventTypes getEventType() {
        return this.eventType;
    }

    public String getDescription() {
        return this.description;
    }

    public float getRarity() {
        return this.rarity;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public Event getMapEvent() {
        return this.mapEvent;
    }

    public void setEventStartedAt(LocalDateTime eventStartedAt) {
        this.eventStartedAt = eventStartedAt;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void disableEvent() {
        this.isActive = false;
    }

    public float getResponseEffect() {
        return this.responseEffect;
    }

    public String getResponseDescription() {
        return this.responseDescription;
    }

    public void setResponseEffect(float effect) {
        this.responseEffect = effect;
    }

    public void setResponseDescription(String description) {
        this.responseDescription = description;
    }

    public LocalDateTime getEventStartedAt() {
        return this.eventStartedAt;
    }

    public void setEventDealtWith(boolean eventDealtWith) {
        this.dealtWith = eventDealtWith;
    }

    public boolean getEventDealtWith() {
        return this.dealtWith;
    }

    public boolean updateEvent(LocalDateTime gameTime, int eventX, int eventY, GameModel gameModel) {
        Duration durationOfEvent = Duration.between(this.eventStartedAt, gameTime);
        if (durationOfEvent.getSeconds() >= 60L && this.dealtWith) {
            gameModel.removeEvent(eventX, eventY);
            return true;
        }
        return false;
    }
}

