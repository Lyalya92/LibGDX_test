package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Direction;
import com.mygdx.game.Main;
import com.mygdx.game.MyAnimation;
import com.mygdx.game.NinjaGirl;

public class GameScreen implements Screen {
    private Main game;
    private NinjaGirl ninjaGirl;

    private SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Vector2 camPosition;
    private Rectangle mapSize;
    private ShapeRenderer shapeRenderer;


    private final float FRAME_SHIFT = 3;
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
        shapeRenderer = new ShapeRenderer();
        img = new Texture("scene/BG.png");

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camPosition = new Vector2();

        ((RectangleMapObject)map.getLayers().get("objects").getObjects().get("camera"))
                .getRectangle().getPosition(camPosition);

        camera.position.x = camPosition.x;
        camera.position.y = camPosition.y;

        mapSize = ((RectangleMapObject)map.getLayers().get("objects").getObjects().get("border"))
                .getRectangle();

        runDirection = Direction.STAND;
        y = 190;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        this.animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.zoom += 0.01;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.zoom >0) camera.zoom -= 0.01;

        if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
               && (mapSize.x < camera.position.x-1)) {
            runDirection = Direction.LEFT;
            animation = anmRun;
            camera.position.x -= FRAME_SHIFT;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                && (camera.position.x < mapSize.x + mapSize.width-1)) {
            runDirection = Direction.RIGHT;
            animation = anmRun;
            camera.position.x += FRAME_SHIFT;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            runDirection = Direction.STAND;
            animation = anmIdle;
        }

        if ((!animation.isRotated() && runDirection == Direction.LEFT)
                || (animation.isRotated() && runDirection == Direction.RIGHT)) animation.rotate();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(animation.getFrame(), camera.position.x, y);
        x += FRAME_SHIFT * runDirection.getValue();
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(this.game, this.ninjaGirl));
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(mapSize.x, mapSize.y, mapSize.width, mapSize.height);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
    map.dispose();
    shapeRenderer.dispose();
    }
}
