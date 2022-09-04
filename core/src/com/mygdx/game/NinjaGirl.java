package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;

public class NinjaGirl {


    public MyAnimation anmRun() {
        return new MyAnimation(Main.atlasGirl, "Run_", Animation.PlayMode.LOOP);
    }

    public MyAnimation anmIdle() {
        return new MyAnimation(Main.atlasGirl, "Idle_", Animation.PlayMode.LOOP);
    }

    public MyAnimation anmGlide() {
        return new MyAnimation(Main.atlasGirl, "Glide", Animation.PlayMode.LOOP);
    }
    public MyAnimation anmJump() {
        return new MyAnimation(Main.atlasGirl, "Jump_", Animation.PlayMode.LOOP);
    }
}
