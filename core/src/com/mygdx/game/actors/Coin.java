package com.mygdx.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Coin extends Actor {

    private static final String TEXTURE_PATH = "coin.png";
    private static final float PX_SIZE = 65;
    private Texture texture;

    public Coin(Stage stage, Vector2 position, AssetManager manager){
        this.texture = manager.get(TEXTURE_PATH,Texture.class);
        setSize(PX_SIZE,PX_SIZE);
        setPosition(position.x,position.y);
        stage.addActor(this);
    }
    public static float getSize() {
        return PX_SIZE;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        return;
    }
}
