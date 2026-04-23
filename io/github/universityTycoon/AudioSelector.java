/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.audio.Music;
import io.github.universityTycoon.GameModel;
import java.util.ArrayList;

public class AudioSelector {
    ArrayList<Music> tracks = new ArrayList();

    public Music selectTrack(GameModel gameModel) {
        return this.tracks.get(0);
    }
}

