package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Main;
import com.mygdx.game.MyAnimation;

public class NinjaGirl {
    private Body body;
    private Rectangle rectangle;
    private int hp;
    private int score;
    private MyAnimation animation;

    public NinjaGirl () {
        this.hp = 100;
        this.score = 0;
    }
    public void setBody(Body body) {
        this.body = body;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Body getBody() {
        return body;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public MyAnimation anmRun() {
        return new MyAnimation(Main.atlasGirl, "Run_", Animation.PlayMode.LOOP, 1/15f);
    }

    public MyAnimation anmIdle() {
        return new MyAnimation(Main.atlasGirl, "Idle_", Animation.PlayMode.LOOP, 1/15f);
    }

    public MyAnimation anmGlide() {
        return new MyAnimation(Main.atlasGirl, "Glide", Animation.PlayMode.LOOP, 1/15f);
    }

    public MyAnimation anmJump() {
        return new MyAnimation(Main.atlasGirl, "Jump_", Animation.PlayMode.NORMAL, 1/15f);
    }
}
