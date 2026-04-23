/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.Events;

import io.github.universityTycoon.Events.GameEventHandler;
import io.github.universityTycoon.Events.Response;
import io.github.universityTycoon.GameEvent;
import io.github.universityTycoon.GameModel;
import java.util.HashMap;
import java.util.Map;

public class CulturalFairHandler
extends GameEventHandler {
    private Map<Integer, Response> eventResponses = new HashMap<Integer, Response>();

    public CulturalFairHandler(GameModel gameModel) {
        super(gameModel);
        this.initialiseEventResponses();
    }

    public void initialiseEventResponses() {
        this.eventResponses.put(1, new Response(5, "Charge a \u00a35 entry fee to fund additional activities and improve the event."));
        this.eventResponses.put(2, new Response(0, "Limit the fair to showcasing local cultural traditions only."));
        this.eventResponses.put(3, new Response(7, "Let students take charge of organizing the event with minimal university support."));
    }

    @Override
    public Response getResponse(int responseID) {
        if (responseID < 1 || responseID > 3) {
            throw new IllegalArgumentException("Response ID must be 1, 2 or 3");
        }
        return this.eventResponses.get(responseID);
    }

    public Map<Integer, Response> getAllResponses() {
        return this.eventResponses;
    }

    public boolean isResolved(GameEvent event) {
        return true;
    }
}

