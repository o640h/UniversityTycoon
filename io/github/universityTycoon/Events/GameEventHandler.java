/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.Events;

import io.github.universityTycoon.Events.Response;
import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.GameModel;
import java.util.HashMap;
import java.util.Map;

public class GameEventHandler {
    GameModel gameModel;
    private Map<Integer, Response> eventResponses = new HashMap<Integer, Response>();

    public GameEventHandler(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public Response getResponse(int responseID) {
        Response response = this.eventResponses.get(responseID);
        if (response == null) {
            return null;
        }
        return response;
    }

    public void handleResponse(int responseID, GameEvent event) {
        Response response = this.getResponse(responseID);
        event.setResponseEffect(response.getEffect());
        event.setResponseDescription(response.getDescription());
    }

    public void handle(GameEvent event) {
        this.displayMessageToPlayer(event);
        this.placeIconOnMap(event);
    }

    public void displayMessageToPlayer(GameEvent event) {
        this.gameModel.addDescriptionMessage(event.getDescription());
    }

    public void placeIconOnMap(GameEvent event) {
        this.gameModel.getMapController().placeEvent(event);
    }
}

