package com.orbinski.wizard;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import static com.orbinski.wizard.Globals.*;

class Listener implements ApplicationListener
{
  Game game;
  Controller controller;
  Renderer renderer;
  float accumulator;
  int updates;

  @Override
  public void create()
  {
    game = new Game();
    controller = new Controller(game);
    renderer = new Renderer(game);
  }

  @Override
  public void resize(final int width, final int height)
  {
    renderer.viewport.update(width, height);
  }

  @Override
  public void render()
  {
    float delta = Gdx.graphics.getDeltaTime();

    if (delta > MAX_TIME_STEP_SECONDS)
    {
      delta = MAX_TIME_STEP_SECONDS;
    }

    accumulator = accumulator + delta;

    controller.update();

    while (accumulator > TIME_STEP_SECONDS)
    {
      game.update(delta);
      accumulator = accumulator - TIME_STEP_SECONDS;
      updates++;

      if (updates >= MAX_UPDATES)
      {
        break;
      }
    }

    final float alpha = accumulator / TIME_STEP_SECONDS;
    updates = 0;
    renderer.render(alpha);
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
  }
}
