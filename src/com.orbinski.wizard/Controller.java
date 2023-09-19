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

    if (game.selectedSpell != null)
    {
      final Vector3 result = Renderer.unproject(mouseScreen);

      if (result != null)
      {
        game.moveSpell(result.x, result.y);
      }
    }

    if (Gdx.input.justTouched())
    {
      if (!game.villains.get(0).inAction && UserInterface.villainIcon.contains(mouseScreen.x, mouseScreen.y))
      {
        game.placeVillain(game.villains.get(0));
      }
      else
      {
        final Vector3 result = Renderer.unproject(mouseScreen);

        if (result != null)
        {
          if (game.selectedSpell != null)
          {
            game.shootSpell();
          }
          else if (game.selectedVillain != null)
          {
            if (game.selectTower(result.x, result.y))
            {
              game.selectedVillain = null;
            }
            else
            {
              game.moveVillain(result.x, result.y);
            }
          }
          else
          {
            game.clearSelections();

            if (game.selectVillain(result.x, result.y))
            {
            }
            else if (game.selectTower(result.x, result.y))
            {
            }
          }
        }
      }

    }

    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
    {
      game.selectSpell();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.C))
    {
      game.centerCamera();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.E))
    {
      game.pickUpVillain();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.H))
    {
      game.hotSelectVillain();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.R))
    {
      game.reset();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
    {
      game.clearSelections();
    }

    // Camera controls

    if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
    {
      game.cameraState.moveUp();
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
    {
      game.cameraState.moveDown();
    }

    if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
    {
      game.cameraState.moveLeft();
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
    {
      game.cameraState.moveRight();
    }
  }
}
