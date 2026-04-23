/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon.Events;

public class Response {
    private float effect;
    private String description;

    public Response(int effect, String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.effect = effect;
        this.description = description;
    }

    public float getEffect() {
        return this.effect;
    }

    public String getDescription() {
        return this.description;
    }
}

