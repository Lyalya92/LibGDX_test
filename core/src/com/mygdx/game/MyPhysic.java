package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.handlers.MyContactListener;
import com.mygdx.game.objects.Feather;

public class MyPhysic {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    public static final float PPM = 100;

    public MyPhysic() {
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
    }

    public Body addObject(RectangleMapObject object) {
        Rectangle rect = object.getRectangle();
        String type = (String) object.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        if (type.equals("StaticBody")) def.type = BodyDef.BodyType.StaticBody;
        if (type.equals("DynamicBody")) def.type = BodyDef.BodyType.DynamicBody;

        def.position.set((rect.x + rect.width/2)/PPM, (rect.y + rect.height/2)/PPM);
        def.gravityScale = (float) object.getProperties().get("gravityScale");

        polygonShape.setAsBox(rect.width/2/PPM, rect.height/2/PPM);

        fdef.shape = polygonShape;
        fdef.friction = 0.2f; // шершавость поверхности. 0 - абсолютно гладкий, больше 7 уже разницы нет
        fdef.density = 1; //плотность
        if (object.getProperties().get("restitution") != null)
            fdef.restitution = (float) object.getProperties().get("restitution"); // прыгучесть

        Body body;
        body = world.createBody(def);
        String name = object.getName();
        body.createFixture(fdef).setUserData(name);
        if (name!=null && name.equals("hero")){
            polygonShape.setAsBox(rect.width/12/PPM, rect.height/12/PPM, new Vector2(0,-rect.height/2/PPM), 0);
            body.createFixture(fdef).setUserData("bottom");
            body.getFixtureList().get(body.getFixtureList().size-1).setSensor(true);
        }
        if (name!=null && name.equals("feather")) {
            new Feather((int) (Math.random()*3), body, rect);
        }

        polygonShape.dispose();
        return body;
    }

    public void setGravity (Vector2 gravity) {
        world.setGravity(gravity);
    }

    public  void step() {
        world.step(1/60.0f, 3, 3);
    }

    public  void debugDraw(OrthographicCamera cam) {
        debugRenderer.render(world, cam.combined);
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
    }
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
