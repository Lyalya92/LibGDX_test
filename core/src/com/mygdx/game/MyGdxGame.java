package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	private final float FRAME_SHIFT = 2;
	private SpriteBatch batch;
	private MyAnimation anmRun;
	private MyAnimation anmStand;
	private MyAnimation animation;
	private float x, y;
	private Direction runDirection;

	@Override
	public void create () {
		batch = new SpriteBatch();
		anmRun = new MyAnimation("ninja_girl.atlas", "Run_", Animation.PlayMode.LOOP);
		anmStand = new MyAnimation("ninja_girl.atlas", "Idle_", Animation.PlayMode.LOOP);
		runDirection = Direction.STAND;
		animation = anmStand;
		x = Gdx.graphics.getWidth()/2 - animation.getFrame().getRegionWidth()/2;
		y = 100;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		animation.setTime(Gdx.graphics.getDeltaTime());

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
			animation = anmStand;
		}

		if ((!animation.isRotated() && runDirection == Direction.LEFT)
			|| (animation.isRotated() && runDirection == Direction.RIGHT)) animation.rotate();

		batch.begin();
		batch.draw(animation.getFrame(), x, y);
		x += FRAME_SHIFT * runDirection.getValue();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		anmRun.dispose();
		anmStand.dispose();
		animation.dispose();
	}
}
