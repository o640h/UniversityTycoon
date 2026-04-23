/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputHandler {
    final int PAUSE_KEY = 44;

    public Vector2 getMousePos() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    public boolean getIsMouseDown() {
        return Gdx.input.isButtonPressed(0);
    }

    public boolean mouseJustClicked() {
        return Gdx.input.isButtonJustPressed(0);
    }

    public boolean getIsPauseJustPressed() {
        return Gdx.input.isKeyJustPressed(44);
    }

    public boolean getKeyJustPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}

