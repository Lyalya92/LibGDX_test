package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

import java.util.ArrayList;

public class GameScreen implements Screen {
    private Main game;
    private NinjaGirl ninjaGirl;
    public static boolean isOnGround;

    private SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private ShapeRenderer shapeRenderer;
    private MyPhysic myPhysic;
    private Body hero;
    private final Rectangle heroRect;

    private final int [] bg;
    private final int [] layers;

    private MyAnimation animation;
    private MyAnimation anmRun;
    private MyAnimation anmIdle;
    private MyAnimation anmJump;

    public static ArrayList<Body> deletedBodies;
    private Direction runDirection;

    private Sound sound_jump;

    public GameScreen(Main game, NinjaGirl ninjaGirl) {
        this.game = game;
        this.ninjaGirl = ninjaGirl;
        isOnGround = true;
        deletedBodies = new ArrayList<>();
        anmRun = ninjaGirl.anmRun();
        anmIdle = ninjaGirl.anmIdle();
        anmJump = ninjaGirl.anmJump();
        this.animation = anmIdle;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        img = new Texture("scene/BG.png");
        myPhysic = new MyPhysic();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        RectangleMapObject tmp = (RectangleMapObject)map.getLayers().get("setting").getObjects().get("hero");
        hero = myPhysic.addObject(tmp);
        hero.setFixedRotation(true);
        heroRect = tmp.getRectangle();

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

        sound_jump = Gdx.audio.newSound(Gdx.files.internal("sounds/sound_jump.mp3"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.position.x = hero.getPosition().x * MyPhysic.PPM;
        camera.position.y = hero.getPosition().y * MyPhysic.PPM;
        camera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        this.animation.setTime(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.zoom += 0.01;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && camera.zoom >0) camera.zoom -= 0.01;

        if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)))
        {
            hero.applyForceToCenter(new Vector2(-2.2f*100000,0), true);
            runDirection = Direction.LEFT;
            animation = anmRun;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)))
        {
            hero.applyForceToCenter(new Vector2(2.2f*100000,0), true);
            runDirection = Direction.RIGHT;
            animation = anmRun;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && isOnGround)
        {
            sound_jump.play();
            hero.applyForceToCenter(new Vector2(0,10.2f*2*100000), true);
            animation = anmJump;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            runDirection = Direction.STAND;
            animation = anmIdle;
        }

        if ((!animation.isRotated() && runDirection == Direction.LEFT)
                || (animation.isRotated() && runDirection == Direction.RIGHT)) animation.rotate();

        mapRenderer.setView(camera);
        mapRenderer.render(bg);

        myPhysic.step();
        myPhysic.debugDraw(camera);

        for (int i = 0; i < deletedBodies.size(); i++) {
            myPhysic.destroyBody(deletedBodies.get(i));
        }
        deletedBodies.clear();


        batch.begin();
        batch.draw(img, 0, 0);
        heroRect.x = Gdx.graphics.getWidth()/2 - heroRect.width / 2 / camera.zoom;
        heroRect.y = Gdx.graphics.getHeight()/2 - heroRect.height / 2 / camera.zoom;
        heroRect.width /= camera.zoom; heroRect.height /= camera.zoom;
        batch.draw(animation.getFrame(), heroRect.x, heroRect.y, heroRect.width, heroRect.height);
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
    sound_jump.dispose();
    }

}
