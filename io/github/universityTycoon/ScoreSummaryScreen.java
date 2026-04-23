/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.universityTycoon.AchievementTypes;
import io.github.universityTycoon.GameModel;
import io.github.universityTycoon.PlayerInputHandler;
import io.github.universityTycoon.ScreenManager;

public class ScoreSummaryScreen
implements Screen {
    SpriteBatch batch;
    PlayerInputHandler input;
    FitViewport viewport;
    Rectangle continueButton;
    Rectangle exitButton;
    Vector2 mousePos;
    boolean mouseDown;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/title.mp3"));
    Texture background;
    Texture start;
    ShapeRenderer sr;
    GameModel gameModel;
    String[] playerName;
    int namePointer;
    GlyphLayout layout;
    final ScreenManager game;

    public ScoreSummaryScreen(ScreenManager main, GameModel gameModel) {
        this.game = main;
        this.gameModel = gameModel;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.viewport = new FitViewport(16.0f, 9.0f);
        this.input = new PlayerInputHandler();
        this.music.setVolume(0.3f);
        this.music.setLooping(true);
        this.music.play();
        this.continueButton = new Rectangle();
        this.exitButton = new Rectangle();
        this.mousePos = new Vector2(0.0f, 0.0f);
        this.background = new Texture(Gdx.files.internal("images/title_page.png"));
        this.start = new Texture(Gdx.files.internal("images/start.png"));
        this.sr = new ShapeRenderer();
        this.gameModel.achievementManager.checkGameEndAchievements(this.gameModel.getSatisfactionScore(), this.gameModel.getMapObjects(), this.gameModel.getHandledEvents());
        this.playerName = new String[6];
        this.namePointer = 0;
        this.layout = new GlyphLayout();
    }

    @Override
    public void render(float v) {
        this.input();
        this.logic();
        this.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    private void input() {
        if (this.input.getKeyJustPressed(66)) {
            this.music.stop();
            this.changeScreen();
        }
        if (this.input.getKeyJustPressed(111)) {
            this.music.stop();
            this.dispose();
            Gdx.app.exit();
        }
        for (int i = 29; i < 55; ++i) {
            if (!this.input.getKeyJustPressed(i)) continue;
            if (this.namePointer < 5) {
                this.playerName[this.namePointer] = Input.Keys.toString(i);
                ++this.namePointer;
                continue;
            }
            if (this.namePointer != 5 || this.playerName[this.namePointer] != null) continue;
            this.playerName[this.namePointer] = Input.Keys.toString(i);
        }
        if (this.input.getKeyJustPressed(67)) {
            this.playerName[this.namePointer] = null;
            if (this.namePointer != 0) {
                --this.namePointer;
            }
        }
        if (this.input.getIsMouseDown()) {
            this.mousePos = this.input.getMousePos();
            this.mouseDown = true;
        } else {
            this.mouseDown = false;
        }
    }

    private void logic() {
        Vector3 touch = new Vector3(this.mousePos.x, this.mousePos.y, 0.0f);
        this.viewport.getCamera().unproject(touch);
        if (this.mouseDown && this.continueButton.contains(touch.x, touch.y)) {
            this.music.stop();
            this.changeScreen();
        }
        if (this.mouseDown && this.exitButton.contains(touch.x, touch.y)) {
            this.music.stop();
            this.dispose();
            Gdx.app.exit();
        }
    }

    private void draw() {
        int i;
        this.batch.begin();
        ScreenUtils.clear(Color.BLACK);
        this.exitButton.set(3.8f, 0.7f, 4.0f, 0.55f);
        this.exitButton.set(8.3f, 0.7f, 4.0f, 0.55f);
        this.viewport.apply();
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);
        this.batch.draw(this.background, 0.0f, 0.0f, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
        this.batch.end();
        this.sr.setProjectionMatrix(this.viewport.getCamera().combined);
        this.sr.begin(ShapeRenderer.ShapeType.Filled);
        this.sr.setColor(0.32941177f, 0.47058824f, 0.49019608f, 1.0f);
        this.sr.rect(2.4f, 5.1f, 11.0f, 3.5f);
        this.sr.rect(2.4f, 1.4f, 11.0f, 3.5f);
        this.sr.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.sr.rect(5.3f, 2.8f, 5.2f, 1.2f);
        this.sr.setColor(255.0f, 255.0f, 255.0f, 1.0f);
        this.sr.rect(5.4f, 2.9f, 5.0f, 1.0f);
        this.sr.end();
        this.batch.begin();
        GameModel.blackFont.draw((Batch)this.batch, "Final Student Satisfaction: " + String.format("%.1f", Float.valueOf(this.gameModel.achievementManager.calculateNewSatisfactionScore(this.gameModel.getSatisfactionScore()))) + "%", 2.6f, 8.4f);
        GameModel.blackFont.draw((Batch)this.batch, "Achievements Completed: ", 2.6f, 7.8f);
        float displayed = 0.0f;
        for (i = 0; i < this.gameModel.achievementManager.getAchievements().length; ++i) {
            if (!this.gameModel.achievementManager.getAchievements()[i]) continue;
            GameModel.blackFont.draw((Batch)this.batch, AchievementTypes.values()[i].name + ": " + AchievementTypes.values()[i].description, 2.6f, 7.4f - displayed);
            displayed += 0.4f;
        }
        GameModel.blackFont.draw((Batch)this.batch, "Type your name here if you want to save your score:", 4.4f, 4.5f);
        GameModel.blackFont.getData().setScale(0.006f);
        for (i = 0; i < this.playerName.length; ++i) {
            if (this.playerName[i] == null) {
                this.layout.setText(GameModel.blackFont, "-");
                GameModel.blackFont.draw((Batch)this.batch, "-", 5.9f + (0.78f * (float)i - this.layout.width / 2.0f), 3.65f);
                continue;
            }
            this.layout.setText(GameModel.blackFont, this.playerName[i]);
            GameModel.blackFont.draw((Batch)this.batch, this.playerName[i], 5.9f + (0.78f * (float)i - this.layout.width / 2.0f), 3.65f);
        }
        GameModel.blackFont.getData().setScale(0.002f);
        this.batch.draw(this.start, 3.8f, 0.7f, 4.0f, 0.55f);
        this.batch.draw(this.start, 8.3f, 0.7f, 4.0f, 0.55f);
        this.batch.end();
    }

    public void changeScreen() {
        boolean completeName = true;
        for (String i : this.playerName) {
            if (i != null) continue;
            completeName = false;
        }
        if (completeName) {
            StringBuilder sb = new StringBuilder();
            for (String i : this.playerName) {
                sb.append(i);
            }
            String playerNameString = sb.toString();
            this.game.switchToFinalScreen(playerNameString, this.gameModel.achievementManager.calculateNewSatisfactionScore(this.gameModel.getSatisfactionScore()));
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.start.dispose();
    }
}

