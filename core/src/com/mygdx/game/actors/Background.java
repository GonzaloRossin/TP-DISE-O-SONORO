package com.mygdx.game.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {

    private Texture background;
    private int srcY;
    private float loopSpeed;

    public Background(AssetManager manager){
        this.background = manager.get("backgrounds/background.png",Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.srcY = 0;
        this.loopSpeed = 0;
    }

    public void setSpeed(float loopSpeed){
        this.loopSpeed = loopSpeed;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(background, getX(), getY(),(int)getX(),srcY, (int)background.getWidth(), (int)background.getHeight());
        srcY -= loopSpeed;
        if(Math.abs(srcY) > background.getHeight())
            srcY += background.getHeight() ;
        return;

    }
}
