package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.screens.StartScreen;

public class Main extends Game {
    private NinjaGirl ninjaGirl;
    public static TextureAtlas atlasGirl;
    public static TextureAtlas atlasTitles;

    @Override
    public void create() {
        ninjaGirl = new NinjaGirl();
        atlasGirl = new TextureAtlas("atlas/ninja_girl.atlas");
        atlasTitles = new TextureAtlas("atlas/titles_and_menu.atlas");
        setScreen(new StartScreen(this, ninjaGirl));
    }

    @Override
    public void dispose() {
        super.dispose();
        atlasGirl.dispose();
        atlasTitles.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
