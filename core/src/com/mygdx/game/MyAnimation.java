package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
    private TextureAtlas atlas;
    private float time;
    private Animation<TextureRegion> animation;

    public MyAnimation(String name, String animationType, Animation.PlayMode playMode){
        time += Gdx.graphics.getDeltaTime();
        atlas = new TextureAtlas(name);
        TextureRegion[] region = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            region [i] = atlas.findRegion(animationType, i);
        }
        animation = new Animation(1/60f, region );
        animation.setFrameDuration(1/15f);
        animation.setPlayMode(playMode);
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        animation.setPlayMode(playMode);
    }
    public void setTime (float time) {
        this.time += time;
    }
    public void resetTime() {
        this.time = 0;
    }
    public TextureRegion getFrame() {
        return animation.getKeyFrame(time);
    }
    public boolean isAnimationOver() {
        return animation.isAnimationFinished(time);
    }
    public boolean isRotated() {
        return this.getFrame().isFlipX();
    }
    public void rotate() {
        this.getFrame().flip(true, false);
    }

    public void dispose(){
        atlas.dispose();
    }

}
