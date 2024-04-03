package com.orbinski.wizard;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import static com.orbinski.wizard.Globals.*;

class Listener implements ApplicationListener
{
  Game game;
  Controller controller;
  Renderer renderer;
  UIRenderer uiRenderer;
  int updates;

  @Override
  public void create()
  {
    Audio.load();

    game = new Game();
    controller = new Controller(game);
    renderer = new Renderer(game);
    uiRenderer = new UIRenderer(game);
    UserInterface.create();
    game.loadTextureReferences();
    game.loadUIReferences();
  }

  @Override
  public void resize(final int width, final int height)
  {
    renderer.viewport.update(width, height);
    uiRenderer.viewport.update(width, height, true);
    UserInterface.layout(width, height);
    SCREEN_WIDTH = width;
    SCREEN_HEIGHT = height;
  }

  @Override
  public void render()
  {
    float frameTime = Gdx.graphics.getDeltaTime();
    controller.update();

    while (frameTime > 0.0f)
    {
      final float delta = Math.min(frameTime, TIME_STEP_SECONDS);
      game.update(delta);
      frameTime = frameTime - delta;
      updates++;

      if (updates >= MAX_UPDATES)
      {
        break;
      }
    }

    // Only do speed updates if the game is not lagging
    if (updates < MAX_UPDATES && game.getSpeed() > 1)
    {
      // When speed is set to 1x, don't run extra updates
      // 1x = regular update which is done above
      for (int i = 2; i <= game.getSpeed(); i++)
      {
        game.update(TIME_STEP_SECONDS);
        updates++;

        if (updates >= MAX_UPDATES)
        {
          break;
        }
      }
    }

    updates = 0;
    renderer.render();
    uiRenderer.render();
  }

  @Override
  public void pause()
  {
  }

  @Override
  public void resume()
  {
  }

  @Override
  public void dispose()
  {
    renderer.dispose();
    uiRenderer.dispose();
  }
}
