package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
    private float time;
    private Animation<TextureRegion> animation;


    public MyAnimation(TextureAtlas atlas, String animationType, Animation.PlayMode playMode, float frameDuration){
        time += Gdx.graphics.getDeltaTime();
        animation = new Animation(1/60f, atlas.findRegions(animationType));
        animation.setFrameDuration(frameDuration);
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

    }

}
