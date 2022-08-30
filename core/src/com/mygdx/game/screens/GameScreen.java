package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;

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
    private MyPhysic myPhysic;
    private Body body;
    private final Rectangle heroRect;

    private final int [] bg;
    private final int [] layers;

    private MyAnimation animation;
    private MyAnimation anmRun;
    private MyAnimation anmIdle;

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
        myPhysic = new MyPhysic();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camPosition = new Vector2();
        RectangleMapObject tmp = (RectangleMapObject)map.getLayers().get("setting").getObjects().get("hero");
        body = myPhysic.addObject(tmp);
        heroRect = tmp.getRectangle();


        mapSize = ((RectangleMapObject)map.getLayers().get("objects").getObjects().get("border"))
                .getRectangle();

        bg = new int[1];
        layers = new int[2];
        bg[0] = map.getLayers().getIndex("background");
        layers[0] = map.getLayers().getIndex("decoration_l1");
        layers[1] = map.getLayers().getIndex("decoration_l2");


        Array<RectangleMapObject> objects = map.getLayers().get("objects").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject obj: objects) {
            myPhysic.addObject(obj);
        }
        runDirection = Direction.STAND;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        camera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        this.animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.zoom += 0.01;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.zoom >0) camera.zoom -= 0.01;

        if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
                && (mapSize.x < body.getPosition().x-1))
        {
            body.applyForceToCenter(new Vector2(-1000000,0), true);
            runDirection = Direction.LEFT;
            animation = anmRun;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                && (body.getPosition().x < mapSize.x + mapSize.width-1)) {
            body.applyForceToCenter(new Vector2(1000000,0), true);
            runDirection = Direction.RIGHT;
            animation = anmRun;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            runDirection = Direction.STAND;
            animation = anmIdle;
        }

        if ((!animation.isRotated() && runDirection == Direction.LEFT)
                || (animation.isRotated() && runDirection == Direction.RIGHT)) animation.rotate();

        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, 0, 0);
        heroRect.x = body.getPosition().x;
        heroRect.y = body.getPosition().y;
        batch.draw(animation.getFrame(), heroRect.x - heroRect.width/2, heroRect.y - heroRect.height/2, heroRect.width, heroRect.height);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        mapRenderer.render(layers);

        myPhysic.step();
        myPhysic.debugDraw(camera);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MenuScreen(this.game, this.ninjaGirl));
        }

//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        for (RectangleMapObject obj: objects) {
//            Rectangle rect = obj.getRectangle();
//            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
//        }
//        shapeRenderer.end();
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
