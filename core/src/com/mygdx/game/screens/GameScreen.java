package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Direction;
import com.mygdx.game.Main;
import com.mygdx.game.MyAnimation;
import com.mygdx.game.NinjaGirl;

public class GameScreen implements Screen {
    private Main game;
    private NinjaGirl ninjaGirl;

    private SpriteBatch batch;
    Texture img;

    private final float FRAME_SHIFT = 2;
    private MyAnimation animation;
    private MyAnimation anmRun;
    private MyAnimation anmIdle;

    private float x, y;
    private Direction runDirection;

    public GameScreen(Main game, NinjaGirl ninjaGirl) {
        this.game = game;
        this.ninjaGirl = ninjaGirl;
        anmRun = ninjaGirl.anmRun();
        anmIdle = ninjaGirl.anmIdle();
        this.animation = anmIdle;
        batch = new SpriteBatch();
        img = new Texture("scene/tropical-forest.png");
        runDirection = Direction.STAND;
        x = Gdx.graphics.getWidth()/2 - animation.getFrame().getRegionWidth()/2;
        y = 70;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        this.animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            runDirection = Direction.LEFT;
            animation = anmRun;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            runDirection = Direction.RIGHT;
            animation = anmRun;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            runDirection = Direction.STAND;
            animation = anmIdle;
        }

        if ((!animation.isRotated() && runDirection == Direction.LEFT)
                || (animation.isRotated() && runDirection == Direction.RIGHT)) animation.rotate();

        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(animation.getFrame(), x, y);
        x += FRAME_SHIFT * runDirection.getValue();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(this.game, this.ninjaGirl));
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
    anmRun.dispose();
    anmIdle.dispose();
    }
}
