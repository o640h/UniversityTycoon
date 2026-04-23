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

public class CoffeeMachineBreakdownHandler
extends GameEventHandler {
    private Map<Integer, Response> eventResponses = new HashMap<Integer, Response>();

    public CoffeeMachineBreakdownHandler(GameModel gameModel) {
        super(gameModel);
        this.initialiseEventResponses();
    }

    public void initialiseEventResponses() {
        this.eventResponses.put(1, new Response(-5, "Call a repair technician to resolve issue within 2 days."));
        this.eventResponses.put(2, new Response(-10, "Wait for the next scheduled maintenance which is in a week."));
        this.eventResponses.put(3, new Response(0, "Provide a temporary coffee station offering free instant coffee."));
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

