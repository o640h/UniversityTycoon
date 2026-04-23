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

public class FloodingHandler
extends GameEventHandler {
    private Map<Integer, Response> eventResponses = new HashMap<Integer, Response>();

    public FloodingHandler(GameModel gameModel) {
        super(gameModel);
        this.initialiseEventResponses();
    }

    public void initialiseEventResponses() {
        this.eventResponses.put(1, new Response(0, "Temporarily relocate affected classes to other parts of the campus."));
        this.eventResponses.put(2, new Response(-5, "Focus on immediate repair of only the most damaged buildings."));
        this.eventResponses.put(3, new Response(-12, "Commit to a long-term plan to repair all flood-affected areas, delaying immediate repairs."));
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

