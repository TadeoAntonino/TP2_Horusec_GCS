package com.chess.game.utils;

import com.badlogic.gdx.audio.Sound;
import com.chess.game.resources.Sounds;

public class SoundPlayer {

    public static void play(String soundName){
        Sound sound = LocalAssetManager.AssetManager.get(soundName, Sound.class);
        sound.play();
    }
}
