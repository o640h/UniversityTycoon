/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.universityTycoon.GameModel;
import io.github.universityTycoon.PlayerInputHandler;
import io.github.universityTycoon.SavedScore;
import io.github.universityTycoon.ScreenManager;

public class FinalScreen
implements Screen {
    SpriteBatch batch;
    PlayerInputHandler input;
    FitViewport viewport;
    Rectangle playAgainButton;
    Rectangle exitButton;
    Vector2 mousePos;
    boolean mouseDown;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/title.mp3"));
    Texture background;
    Texture logo;
    Texture start;
    ShapeRenderer sr;
    final ScreenManager game;

    public FinalScreen(ScreenManager main) {
        this.game = main;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.viewport = new FitViewport(16.0f, 9.0f);
        this.input = new PlayerInputHandler();
        this.music.setVolume(0.3f);
        this.music.setLooping(true);
        this.music.play();
        this.playAgainButton = new Rectangle();
        this.exitButton = new Rectangle();
        this.mousePos = new Vector2(0.0f, 0.0f);
        this.background = new Texture(Gdx.files.internal("images/title_page.png"));
        this.logo = new Texture(Gdx.files.internal("images/logo.png"));
        this.start = new Texture(Gdx.files.internal("images/start.png"));
        this.sr = new ShapeRenderer();
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
        if (this.input.getKeyJustPressed(62)) {
            this.music.stop();
            this.game.switchToMainScreen();
        }
        if (this.input.getKeyJustPressed(111)) {
            this.music.stop();
            this.dispose();
            Gdx.app.exit();
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
        if (this.mouseDown && this.playAgainButton.contains(touch.x, touch.y)) {
            this.music.stop();
            this.game.switchToMainScreen();
        }
        if (this.mouseDown && this.exitButton.contains(touch.x, touch.y)) {
            this.music.stop();
            this.dispose();
            Gdx.app.exit();
        }
    }

    private void draw() {
        this.batch.begin();
        ScreenUtils.clear(Color.BLACK);
        this.playAgainButton.set(6.05f, 1.0f, 4.0f, 0.55f);
        this.exitButton.set(6.05f, 0.4f, 4.0f, 0.55f);
        this.viewport.apply();
        this.batch.setProjectionMatrix(this.viewport.getCamera().combined);
        this.batch.draw(this.background, 0.0f, 0.0f, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
        this.batch.end();
        this.sr.setProjectionMatrix(this.viewport.getCamera().combined);
        this.sr.begin(ShapeRenderer.ShapeType.Filled);
        this.sr.setColor(0.32941177f, 0.47058824f, 0.49019608f, 1.0f);
        this.sr.rect(5.9f, 1.65f, 4.2f, 2.5f);
        this.sr.end();
        this.batch.begin();
        this.batch.draw(this.logo, 6.0f, 4.5f, 4.0f, 4.0f);
        this.batch.draw(this.start, 6.05f, 1.0f, 4.0f, 0.55f);
        this.batch.draw(this.start, 6.05f, 0.4f, 4.0f, 0.55f);
        GameModel.blackFont.draw((Batch)this.batch, "Leaderboard:", 6.0f, 4.0f);
        for (SavedScore i : this.game.leaderboard) {
            GameModel.blackFont.draw((Batch)this.batch, Integer.toString(this.game.leaderboard.indexOf(i) + 1) + ". " + i.getName() + ": " + i.getScore() + "%", 6.0f, 4.0f - 0.4f * (float)(this.game.leaderboard.indexOf(i) + 1));
        }
        this.batch.end();
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
        this.logo.dispose();
        this.start.dispose();
    }
}

