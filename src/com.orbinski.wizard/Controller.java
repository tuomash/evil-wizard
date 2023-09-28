package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

class Controller
{
  final Game game;
  private final Vector2 mouseScreen = new Vector2();
  private final Vector2 hudMouseScreen = new Vector2();

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
    hudMouseScreen.x = Gdx.input.getX();
    hudMouseScreen.y = Gdx.input.getY();

    final Vector2 result = Renderer.unproject(mouseScreen);
    final Vector2 hudResult = Renderer.hudUnproject(hudMouseScreen);

    if (result == null || hudResult == null)
    {
      return;
    }

    if (game.selectedSpell != null)
    {
      game.moveSpell(result.x, result.y);
    }

    if (Gdx.input.justTouched())
    {
      if (UserInterface.hotBarIconMinotaur.contains((int) hudResult.x, (int) hudResult.y))
      {
        game.selectVillain();
      }
      else if (UserInterface.hotBarIconLightningSpell.contains((int) hudResult.x, (int) hudResult.y))
      {
        game.selectSpell(0);
      }
      else if (UserInterface.hotBarIconGreaseSpell.contains((int) hudResult.x, (int) hudResult.y))
      {
        game.selectSpell(1);
      }
      else if (UserInterface.nextWaveButton.contains((int) hudResult.x, (int) hudResult.y))
      {
        game.nextWave();
      }
      else
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

    if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
    {
      game.help = !game.help;
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
    {
      if (game.selectSpell(0))
      {
        game.moveSpell(result.x, result.y);
      }
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
    {
      if (game.selectSpell(1))
      {
        game.moveSpell(result.x, result.y);
      }
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
      game.selectVillain();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.R))
    {
      game.reset();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.N))
    {
      game.decreaseSpeed();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.M))
    {
      game.increaseSpeed();
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
    {
      game.paused = !game.paused;
    }
    else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
    {
      game.clearSelections();

      if (game.help)
      {
        game.help = false;
      }
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
