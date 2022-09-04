package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;
import com.mygdx.game.MyAnimation;
import com.mygdx.game.NinjaGirl;

public class StartScreen implements Screen {
    private Main game;
    private NinjaGirl ninjaGirl;
    private MyAnimation animation;
    private MyAnimation anmIdle;
    private MyAnimation anmGlide;

    private final float X_END_ANM_POINT = 550;
    private final float Y_END_ANM_POINT = 70;

    TextureRegion [] regTitles;
    private Texture img;
    private SpriteBatch batch;
    float x,y;
    private float shift = 1;

    private final Music music;

    public StartScreen(Main game, NinjaGirl ninjaGirl) {
        this.game = game;
        this.ninjaGirl = ninjaGirl;
        anmIdle = ninjaGirl.anmIdle();
        anmGlide = ninjaGirl.anmGlide();
        animation = anmGlide;

        x = X_END_ANM_POINT;
        y = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        img = new Texture("scene/tropical-forest.png");
        regTitles = new TextureRegion[3];
        for (int i = 1; i <=3; i++) {
            regTitles[i-1] = Main.atlasTitles.findRegion("StartScreen_",i);
        }

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
        animation.setTime(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(img, 0, 0);
        y -= shift;
        if (y < Y_END_ANM_POINT) {
            animation = anmIdle;
            shift = 0;
            batch.draw(regTitles[0], 360, 480);
            batch.draw(regTitles[1],460, 440);
            batch.draw(regTitles[2],500, 5);
        }
        batch.draw(animation.getFrame(), x, y);
        y -= shift;
        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            dispose();
            game.setScreen(new MenuScreen(game, ninjaGirl));
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
        animation.dispose();
        anmGlide.dispose();
        anmIdle.dispose();
        music.dispose();
    }
}
