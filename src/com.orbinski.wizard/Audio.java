package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

class Audio
{
  static boolean mute = false;
  static float volume = 1.0f;
  static Sound lightningBolt;
  static Music preparation;
  static Music battle;

  static void load()
  {
    lightningBolt = loadSound("lightning-bolt.wav");
    preparation = loadMusic("preparation.mp3");
  }

  private static Sound loadSound(final String fileName)
  {
    final File file = new File(System.getProperty("user.dir")
                                   + File.separator
                                   + "sfx"
                                   + File.separator
                                   + fileName);
    return Gdx.audio.newSound(Gdx.files.absolute(file.getAbsolutePath()));
  }

  private static Music loadMusic(final String fileName)
  {
    final File file = new File(System.getProperty("user.dir")
                                   + File.separator
                                   + "music"
                                   + File.separator
                                   + fileName);
    return Gdx.audio.newMusic(Gdx.files.absolute(file.getAbsolutePath()));
  }

  static void playSound(final Sound sound)
  {
    if (!mute && sound != null)
    {
      sound.play(volume);
    }
  }

  static void playPreparation()
  {
    if (!mute && preparation != null)
    {
      preparation.setVolume(volume);
      preparation.setLooping(true);
      preparation.play();
    }
  }
}
