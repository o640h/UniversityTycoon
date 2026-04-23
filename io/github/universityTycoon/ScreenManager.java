/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.universityTycoon.FinalScreen;
import io.github.universityTycoon.FirstScreen;
import io.github.universityTycoon.GameModel;
import io.github.universityTycoon.MainScreen;
import io.github.universityTycoon.SavedScore;
import io.github.universityTycoon.ScoreSummaryScreen;
import java.util.ArrayList;
import java.util.Collections;

public class ScreenManager
extends Game {
    public SpriteBatch batch;
    public MainScreen gameScreen;
    public FirstScreen titleScreen;
    public FinalScreen endScreen;
    public Boolean fullScreen;
    private Preferences leaderboardNames;
    private Preferences leaderboardScores;
    public ArrayList<SavedScore> leaderboard;

    @Override
    public void create() {
        this.gameScreen = new MainScreen(this);
        this.titleScreen = new FirstScreen(this);
        this.endScreen = new FinalScreen(this);
        this.batch = new SpriteBatch();
        this.leaderboardNames = Gdx.app.getPreferences("LeaderboardNames");
        this.leaderboardScores = Gdx.app.getPreferences("LeaderboardScores");
        this.leaderboard = new ArrayList();
        for (int i = 1; i < 6; ++i) {
            this.leaderboard.add(new SavedScore(this.leaderboardNames.getString(String.valueOf(i), "PLAYER"), this.leaderboardScores.getFloat(String.valueOf(i), 0.0f)));
        }
        this.fullScreen = false;
        this.setScreen(this.titleScreen);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(141)) {
            this.fullScreen = Gdx.graphics.isFullscreen();
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            if (this.fullScreen.booleanValue()) {
                Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
            } else {
                Gdx.graphics.setFullscreenMode(currentMode);
            }
        }
        super.render();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        GameModel.font.dispose();
        GameModel.smallerFont.dispose();
        GameModel.blackFont.dispose();
    }

    public void switchToMainScreen() {
        this.setScreen(this.gameScreen);
    }

    public void switchToFinalScreen(String playerName, float playerScore) {
        this.leaderboard.add(new SavedScore(playerName, playerScore));
        Collections.sort(this.leaderboard);
        this.leaderboard.remove(this.leaderboard.size() - 1);
        for (int i = 0; i < this.leaderboard.size(); ++i) {
            this.leaderboardNames.putString(String.valueOf(i + 1), this.leaderboard.get(i).getName());
            this.leaderboardScores.putFloat(String.valueOf(i + 1), this.leaderboard.get(i).getScore());
        }
        this.leaderboardNames.flush();
        this.leaderboardScores.flush();
        this.setScreen(this.endScreen);
        this.gameScreen.restartGame();
    }

    public void switchToScoreSummaryScreen() {
        this.setScreen(new ScoreSummaryScreen(this, this.gameScreen.gameModel));
    }
}

