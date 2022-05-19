package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MainGame;

public abstract class BaseScreen implements Screen {

    protected MainGame game;

    public BaseScreen(MainGame game){
        this.game = game;
    }

    public void setGame(MainGame game) {
        this.game = game;
    }
    public MainGame getGame() {
        return game;
    }
}
