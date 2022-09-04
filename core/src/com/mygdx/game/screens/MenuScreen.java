package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.NinjaGirl;

public class MenuScreen implements Screen {
    private Main game;
    private NinjaGirl ninjaGirl;
    private SpriteBatch batch;
    private Texture img, imgMenu;
    private Rectangle startRect;
    private final Music music;

    public MenuScreen(Main game, NinjaGirl ninjaGirl) {
        this.game = game;
        this.ninjaGirl = ninjaGirl;
        batch = new SpriteBatch();
        img = new Texture("scene/tropical-forest.png");
        imgMenu = new Texture("scene/menu.png");
        startRect = new Rectangle(515, 198, 164, 50);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/ChecksForFree.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(imgMenu, 500, 70);
        batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (startRect.contains(x,y)) {
                dispose();
                game.setScreen(new GameScreen(this.game, this.ninjaGirl));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        img.dispose();
        imgMenu.dispose();
        imgMenu.dispose();
        music.dispose();
    }
}
