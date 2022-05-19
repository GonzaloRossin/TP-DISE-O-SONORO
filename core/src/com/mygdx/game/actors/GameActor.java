package com.mygdx.game.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameActor extends Actor {

    protected Stage stage;
    protected World world;
    protected Body body;
    protected Fixture fixture;

    public GameActor(Stage stage, World world){
        this.stage = stage;
        this.world = world;
        this.setBody(null);
        this.setFixture(null);
    }

    public Body getBody(){return body;};

    public void setBody(Body body) {
        this.body = body;
    }

    public Fixture getFixture(){return fixture;};

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public void destroy(){
        if(body != null){
            body.destroyFixture(fixture);
            world.destroyBody(body);
        }
    }

}
