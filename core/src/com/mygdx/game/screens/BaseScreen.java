package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MainGame;
import com.mygdx.game.MusicPlayer;
import com.mygdx.game.SoundsPlayer;

public abstract class BaseScreen implements Screen {

    protected MainGame game;
    protected final MusicPlayer musicPlayer = MusicPlayer.getMusicPlayer();
    protected final SoundsPlayer soundsPlayer = SoundsPlayer.getSoundsPlayer();

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
