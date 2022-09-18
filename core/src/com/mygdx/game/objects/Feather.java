package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Main;
import com.mygdx.game.MyAnimation;

import java.util.ArrayList;

public class Feather {
    private static ArrayList <Feather> feathers;
    private int color;
    private MyAnimation animation;
    private Body body;
    private Rectangle rect;

    public Feather(int color, Body body, Rectangle rect) {
        if (feathers == null) {
            feathers = new ArrayList<>();
        }
        this.color = color;
        switch (color) {
            case 1: this.animation = new MyAnimation(Main.atlasGirl, "Fgreen", Animation.PlayMode.LOOP, 1/8f); break;
            case 2: this.animation = new MyAnimation(Main.atlasGirl, "Fpink", Animation.PlayMode.LOOP,1/8f); break;
            default: this.animation = new MyAnimation(Main.atlasGirl, "Fgold", Animation.PlayMode.LOOP,1/8f); break;
        }
        this.body = body;
        this.rect = rect;
        feathers.add(this);
    }

    public static ArrayList<Feather> getFeatherList() {
        return feathers;
    }

    public Body getBody(){
        return this.body;
    }

    public Rectangle getRectangle(){
        return this.rect;
    }

    public MyAnimation getAnimation(){
        return this.animation;
    }

    public static void deleteFeather(Body body) {
        for (int i = 0; i < feathers.size(); i++) {
            if (feathers.get(i).getBody()== body) {
                feathers.remove(feathers.get(i));
                break;
            }
        }

    }

}
