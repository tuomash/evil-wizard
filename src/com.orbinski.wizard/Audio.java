package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.io.File;

class Audio
{
  static Sound lightningBolt;

  static void load()
  {
    lightningBolt = loadSound("lightning-bolt.wav");
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
}
