package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

class Controller
{
  final Game game;
  private final Vector3 mouseScreen = new Vector3();

  Controller(final Game game)
  {
    this.game = game;
  }

  void update()
  {
    if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
    {
      final Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();

      if (Gdx.graphics.isFullscreen())
      {
        // System.out.println("w: " + currentMode.width + ", h:" + currentMode.height);
        Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
      }
      else
      {
        // TODO: doesn't work properly when going from windowed (not maximized) to full screen
        Gdx.graphics.setFullscreenMode(currentMode);
      }
    }

    handlePlayerControls();
  }

  void handlePlayerControls()
  {
    mouseScreen.x = Gdx.input.getX();
    mouseScreen.y = Gdx.input.getY();

    if (Gdx.input.justTouched())
    {
      if (!game.villains.get(0).inAction && UserInterface.villainIcon.contains(mouseScreen.x, mouseScreen.y))
      {
        game.resetVillain(game.villains.get(0));
      }
      else
      {
        final Vector3 result = Renderer.unproject(mouseScreen);

        if (result != null)
        {
          if (game.selectedVillain == null)
          {
            game.selectVillain(result.x, result.y);
          }
          else
          {
            game.moveVillain(result.x, result.y);
          }
        }
      }
    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.E))
    {
      if (game.selectedVillain != null)
      {
        game.pickUpVillain();
      }
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.H))
    {
      game.hotSelectVillain();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.R))
    {
      game.reset();
    }

    // Camera controls

    if (Gdx.input.isKeyPressed(Input.Keys.W))
    {
      game.cameraState.moveUp();
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.S))
    {
      game.cameraState.moveDown();
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A))
    {
      game.cameraState.moveLeft();
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.D))
    {
      game.cameraState.moveRight();
    }
  }
}
