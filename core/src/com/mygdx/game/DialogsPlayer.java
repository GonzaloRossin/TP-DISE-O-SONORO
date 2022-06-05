package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DialogsPlayer {

    public enum Dialog {
        PoliceComm1("dialogs/police-comm-1.wav"),
        PoliceComm2("dialogs/police-comm-2.wav"),
        PoliceComm3("dialogs/police-comm-3.wav"),
        PoliceComm4("dialogs/police-comm-4.wav"),
        PoliceComm5("dialogs/police-comm-5.wav"),
        PoliceComm6("dialogs/police-comm-6.wav"),
        PoliceComm7("dialogs/police-comm-7.wav"),
        PoliceComm8("dialogs/police-comm-8.wav"),
        PoliceComm9("dialogs/police-comm-9.wav"),
        PoliceComm10("dialogs/police-comm-10.wav"),
        PoliceComm11("dialogs/police-comm-11.wav"),
        PoliceComm12("dialogs/police-comm-12.wav"),
        PoliceComm13("dialogs/police-comm-13.wav");

        private final String path;
        private final Sound sound;

        Dialog(String path) {
            this.path = path;
            this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
        }

        public String getPath() { return path; }
        public Sound getSound() { return sound; }
    }

    private static DialogsPlayer singletonPlayer = null;

    private DialogsPlayer() { }

    public static DialogsPlayer getDialogsPlayer() {
        if(singletonPlayer == null) {
            singletonPlayer = new DialogsPlayer();
        }
        return singletonPlayer;
    }

    public Dialog getRandomComm() {
        Random rand = new Random();
        List<Dialog> dialogs = new ArrayList<Dialog>(Arrays.asList(Dialog.values()));
        int index = (int)Math.round(rand.nextDouble() * (dialogs.size() - 1));
        return dialogs.get(index);
    }

    public void play(Dialog dialog) {
        this.play(dialog, 1f);
    }

    public void play(Dialog dialog, float volume) {
        dialog.getSound().play(volume);
    }

    public void stopAllDialogs() {
        for(Dialog dialog : Dialog.values()) {
            dialog.getSound().stop();
        }
    }
}
