package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.io.File;

class Audio
{
  private static float fadeOutElapsed = 0.0f;
  private static float fadeOutPer = 0.1f;

  static boolean mute = false;
  static boolean fadeOut = false;
  static float sfxVolume = 1.0f;
  static float musicVolume = 0.2f;

  static Sound lightningBolt;
  static Sound grease;
  static Sound minotaurGrowl;

  static Music preparation;
  static Music battle;

  static void load()
  {
    lightningBolt = loadSound("lightning-bolt.wav");
    grease = loadSound("grease.wav");
    minotaurGrowl = loadSound("minotaur-growl.wav");

    preparation = loadMusic("preparation.mp3");
    battle = loadMusic("battle.mp3");
  }

  static void update(final float delta)
  {
    if (!mute && fadeOut && battle != null && battle.isPlaying())
    {
      fadeOutElapsed = fadeOutElapsed + delta;

      if (fadeOutElapsed > fadeOutPer)
      {
        fadeOutElapsed = 0.0f;

        float battleVolume = battle.getVolume();
        battleVolume = battleVolume - 0.01f;

        if (battleVolume < 0.0f)
        {
          battleVolume = 0.0f;
          battle.stop();
          playPreparation();
          fadeOut = false;
        }

        battle.setVolume(battleVolume);
      }
    }
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
      sound.play(sfxVolume);
    }
  }

  static void playPreparation()
  {
    if (!mute && preparation != null && !preparation.isPlaying())
    {
      preparation.setVolume(musicVolume);
      preparation.setLooping(true);
      preparation.play();
    }
  }

  static void stopPreparation()
  {
    if (preparation != null && preparation.isPlaying())
    {
      preparation.stop();
    }
  }

  static void playBattle()
  {
    if (!mute && battle != null && !battle.isPlaying())
    {
      battle.setVolume(musicVolume);
      battle.setLooping(true);
      battle.play();
    }
  }

  static void stopBattle()
  {
    if (!mute && battle != null && battle.isPlaying())
    {
      battle.stop();
    }
  }
}
