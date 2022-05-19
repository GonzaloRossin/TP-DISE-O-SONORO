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
import com.mygdx.game.GarageCars;
import com.mygdx.game.screens.GarageScreen;

public class Player extends GameActor {

    private float bodyWidth;
    private float bodyHeight;
    private static final String USER_DATA = "PLAYER";
    private Texture texture;

    public Player(Stage stage, World world, Vector2 position,String texturePath,AssetManager manager) {
        super(stage,world);
        this.texture = (Texture)manager.get(texturePath,Texture.class);
        this.bodyWidth = texture.getWidth() / Constants.PPM.getValue();
        this.bodyHeight = texture.getHeight() / Constants.PPM.getValue();
        //Creating body.
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(position);
        setBody(world.createBody(def));
        //Creating fixture.
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bodyWidth /2f, bodyHeight /2f);
        setFixture(getBody().createFixture(shape,1));
        getFixture().setUserData(USER_DATA);
        shape.dispose();
        setSize(Constants.PPM.getValue() * bodyWidth,
                Constants.PPM.getValue() * bodyHeight);
        setPosition((getBody().getPosition().x - bodyWidth /2f)* Constants.PPM.getValue(),
                (getBody().getPosition().y - bodyHeight /2f)* Constants.PPM.getValue());
        stage.addActor(this);
    }
    public static String getUserData(){
        return USER_DATA;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((getBody().getPosition().x - bodyWidth /2) * Constants.PPM.getValue(),
                (getBody().getPosition().y - bodyHeight /2) * Constants.PPM.getValue());
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        return;

    }
}
