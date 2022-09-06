package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.screens.GameScreen;


public class MyContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData()!=null && b.getUserData()!=null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if (tmpA.equals("hero") && tmpB.equals("feather")) {
                GameScreen.deletedBodies.add(b.getBody());
            }
            if (tmpB.equals("hero") && tmpA.equals("feather")) {
                GameScreen.deletedBodies.add(a.getBody());
            }

            if ((tmpA.equals("bottom") && tmpB.equals("ground")) || (tmpB.equals("bottom") && tmpA.equals("ground"))) {
                GameScreen.isOnGround = true;
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData()!=null && b.getUserData()!=null) {
            String tmpA = (String) a.getUserData();
            String tmpB = (String) b.getUserData();

            if ((tmpA.equals("bottom") && tmpB.equals("ground")) || (tmpB.equals("bottom") && tmpA.equals("ground"))) {
                GameScreen.isOnGround = false;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
