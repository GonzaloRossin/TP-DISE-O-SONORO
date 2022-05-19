package com.mygdx.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;

import java.util.Random;

public class Car extends GameActor {

    public static final int CAR_NUMBER = 20;
    private static final String USER_DATA = "BLOCK";
    private float height;
    private float width;
    private Texture texture;

    public Car(Stage stage, World world, float x, AssetManager manager) {
        super(stage,world);
        Random rand = new Random();
        int randInt = rand.nextInt(CAR_NUMBER + 1);

        String path = "cars/car" + randInt + ".png";
        this.texture = (Texture)manager.get(path,Texture.class);
        this.height = texture.getHeight() / Constants.PPM.getValue();
        this.width = texture.getWidth() / Constants.PPM.getValue();
        //Creating body.
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        float randOffsetY = (rand.nextFloat() * 0.25f) * (rand.nextBoolean()? 1f : -1f);
        def.position.set(new Vector2(x,Constants.VIEWPORT_HEIGHT.getValue() / Constants.PPM.getValue() + height + randOffsetY));
        setBody(world.createBody(def));
        //Creating fixture.
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((texture.getWidth()/Constants.PPM.getValue())/2f,(texture.getHeight()/Constants.PPM.getValue())/2f);
        setFixture(getBody().createFixture(shape,1));
        getFixture().setUserData(USER_DATA);
        shape.dispose();
        setSize(Constants.PPM.getValue() * width,
                Constants.PPM.getValue() * height);
        setPosition((getBody().getPosition().x - width/2f)* Constants.PPM.getValue(),
                (getBody().getPosition().y - height/2f)* Constants.PPM.getValue());
        stage.addActor(this);
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }
    public void setBodyPosition(Vector2 bodyPosition){
        getBody().setTransform(bodyPosition,body.getAngle());
    }
    public float getBodyWidth() {
        return width;
    }
    public static String getUserData(){
        return USER_DATA;
    }
    public void updateSpeed(float newSpeed){
        getBody().setLinearVelocity(0,-newSpeed);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((getBody().getPosition().x - width /2f)* Constants.PPM.getValue(),
                (getBody().getPosition().y - height/2f)* Constants.PPM.getValue());
        batch.draw(texture,getX(),getY());
        return;

    }
}
