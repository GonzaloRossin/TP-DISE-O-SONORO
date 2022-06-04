package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {

    public enum MusicTrack {
        MainMenu("music/background_music.wav"),
        GarageMenu("music/background_music.wav"),
        EasyLevel("music/easy_diff_music.mp3"),
        MediumLevel("music/medium_diff_music.mp3"),
        HardLevel("music/hard_diff_music.mp3");

        public final String path;

        MusicTrack(String path) {
            this.path = path;
        }
    }

    private static final MusicPlayer singletonPlayer = new MusicPlayer();

    private Music currentMusic = null;
    private boolean playing = false;

    private MusicPlayer() { }

    public static MusicPlayer getMusicPlayer() {
        return singletonPlayer;
    }

    public void setTrack(MusicTrack track) {
        this.setTrack(track, 1f);
    }
    public void setTrack(MusicTrack track, float volume) {
        if(this.currentMusic != null) {
            this.disposeTrack();
        }
        this.currentMusic = Gdx.audio.newMusic(Gdx.files.internal(track.path));
        this.currentMusic.setLooping(true);
        this.currentMusic.setVolume(volume);
        this.playing = false;
    }
    public void play() {
        if(this.currentMusic == null || this.playing) {
            return;
        }
        this.currentMusic.play();
        this.playing = true;
    }

    public void stop() {
        if(this.currentMusic == null) {
            return;
        }
        this.currentMusic.stop();
        this.playing = false;
    }

    public void disposeTrack() {
        if(this.currentMusic == null) {
            return;
        }
        this.currentMusic.dispose();
        this.currentMusic = null;
    }

}
