package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class SoundsPlayer {

    public enum SoundEffect {
        CarStart1("sounds/car_start_1.wav"),
        CarStart2("sounds/car_start_2.wav"),
        CarStart3("sounds/car_start_3.wav"),
        CarStart4("sounds/car_start_4.wav"),
        CarStart5("sounds/car_start_5.wav"),
        CarStart6("sounds/car_start_6.wav"),
        CarStart7("sounds/car_start_7.wav"),
        CarStart8("sounds/car_start_8.wav"),
        BikeStart("sounds/bike_start.wav"),
        CarCrash("sounds/car_crash.wav"),
        TireScreech1("sounds/tire_screech_1.wav"),
        TireScreech2("sounds/tire_screech_2.wav"),
        TireScreech3("sounds/tire_screech_3.wav"),
        TireScreech4("sounds/tire_screech_4.wav"),
        CollectCoin("sounds/collect_coin.mp3");

        public final String path;
        public final Sound sound;

        SoundEffect(String path) {
            this.path = path;
            this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
        }
    }

    private static final SoundsPlayer singletonPlayer = new SoundsPlayer();

    private SoundsPlayer() {}

    public static SoundsPlayer getSoundsPlayer() {
        return singletonPlayer;
    }

    public void playSoundEffect(SoundEffect effect) {
        this.playSoundEffect(effect, 1f);
    }

    public void playSoundEffect(SoundEffect effect, float volume) {
        effect.sound.play(volume);
    }


    public void stopSoundEffect(SoundEffect effect) {
        effect.sound.stop();
    }
    public void stopAllSounds() {
        for(SoundEffect sfx : SoundEffect.values()) {
            sfx.sound.stop();
        }
    }
}
