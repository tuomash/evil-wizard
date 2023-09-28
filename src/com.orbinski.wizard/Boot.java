package com.orbinski.wizard;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

class Boot
{
  public static void main(final String[] args)
  {
    final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setMaximized(true);
    config.setTitle("Evil Wizard");
    config.setForegroundFPS(60);
    config.setIdleFPS(30);

    if (args.length > 0 && args[0].equalsIgnoreCase("mute"))
    {
      Audio.mute = true;
    }

    try
    {
      new Lwjgl3Application(new Listener(), config)
      {
        @Override
        public void exit()
        {
          super.exit();
        }
      };
    }
    catch (final Exception e)
    {
      e.printStackTrace();
    }
  }
}
