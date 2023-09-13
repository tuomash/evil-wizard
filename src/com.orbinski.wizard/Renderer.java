package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;

import static com.orbinski.wizard.Globals.WORLD_HEIGHT;
import static com.orbinski.wizard.Globals.WORLD_WIDTH;

class Renderer
{
  static Viewport staticViewport;

  final Game game;
  final OrthographicCamera camera;
  final Viewport viewport;
  final ShapeRenderer shapeRenderer;
  final SpriteBatch spriteBatch;
  final OrthographicCamera hudCamera;
  final ScreenViewport hudViewport;
  final ShapeRenderer hudShapeRenderer;
  final SpriteBatch hudSpriteBatch;
  final Color background;

  final Texture knight;
  final Texture minotaur;
  final Texture tower;
  final Texture orb;

  Renderer(final Game game)
  {
    this.game = game;

    camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
    camera.update();

    viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
    staticViewport = viewport;

    shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.setAutoShapeType(true);

    spriteBatch = new SpriteBatch();
    spriteBatch.setProjectionMatrix(camera.combined);

    hudCamera = new OrthographicCamera();
    hudCamera.setToOrtho(true);
    hudCamera.update();

    hudViewport = new ScreenViewport(hudCamera);

    hudSpriteBatch = new SpriteBatch();
    hudSpriteBatch.setProjectionMatrix(hudCamera.combined);

    hudShapeRenderer = new ShapeRenderer();
    hudShapeRenderer.setProjectionMatrix(hudCamera.combined);
    hudShapeRenderer.setAutoShapeType(true);

    File file = new File(System.getProperty("user.dir") + File.separator + "knight.png");
    knight = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "minotaur.png");
    minotaur = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "tower.png");
    tower = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "orb.jpg");
    orb = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    background = new Color(173.0f / 255.0f, 216.0f / 255.0f, 230.0f / 255.0f, 1.0f);
  }

  void render()
  {
    ScreenUtils.clear(Color.BLACK);

    renderTower();
    renderVillains();
    renderEnemies();
    renderProjectiles();
  }

  void renderTower()
  {
    renderEntity(game.tower, tower);
    // renderEntityBorder(game.tower, Color.GREEN);
  }

  void renderVillains()
  {
    for (int i = 0; i < game.villains.size(); i++)
    {
      final Villain villain = game.villains.get(i);

      if (!villain.dead)
      {
        if (villain.inAction)
        {
          renderEntity(villain, minotaur);
          renderEntityBorder(villain, Color.BLUE);

          if (game.selectedVillain == villain)
          {
            renderEntityBorder(villain, Color.WHITE);
          }
        }
        else
        {
          renderHudFilledQuad(UserInterface.villainIcon.x,
                              UserInterface.villainIcon.y,
                              UserInterface.villainIcon.width,
                              UserInterface.villainIcon.height,
                              Color.BLUE);
        }
      }
    }
  }

  void renderEnemies()
  {
    for (int i = 0; i < game.enemies.size(); i++)
    {
      final Enemy enemy = game.enemies.get(i);

      if (!enemy.dead)
      {
        renderEntity(enemy, knight);
        renderEntityBorder(enemy, Color.RED);
      }
    }
  }

  void renderProjectiles()
  {
    for (int i = 0; i < game.projectiles.size(); i++)
    {
      final Projectile projectile = game.projectiles.get(i);

      if (!projectile.dead)
      {
        renderEntity(projectile, orb);
        // renderFilledEntity(projectile, Color.WHITE);
      }
    }
  }

  void renderEntity(final Entity entity, final Texture texture)
  {
    spriteBatch.begin();
    spriteBatch.draw(texture,
                     entity.getTopLeftCornerX(),
                     entity.getTopLeftCornerY(),
                     entity.getWidth(),
                     entity.getHeight());
    spriteBatch.end();
  }

  void renderEntityBorder(final Entity entity, final Color borderColor)
  {
    renderQuad(entity.getTopLeftCornerX(),
               entity.getTopLeftCornerY(),
               entity.getWidth(),
               entity.getHeight(),
               borderColor);
  }

  void renderFilledEntity(final Entity entity, final Color borderColor)
  {
    renderFilledQuad(entity.getTopLeftCornerX(),
                     entity.getTopLeftCornerY(),
                     entity.getWidth(),
                     entity.getHeight(),
                     borderColor);
  }

  void renderFilledQuad(final float x,
                        final float y,
                        final float width,
                        final float height,
                        final Color color)
  {
    renderFilledQuad(x,
                     y,
                     width,
                     height,
                     color.r,
                     color.g,
                     color.b,
                     color.a);
  }

  void renderFilledQuad(final float x,
                        final float y,
                        final float width,
                        final float height,
                        final float red,
                        final float green,
                        final float blue,
                        final float alpha)
  {
    renderQuad(x,
               y,
               width,
               height,
               red,
               green,
               blue,
               alpha,
               ShapeRenderer.ShapeType.Filled);
  }

  void renderQuad(final float x,
                  final float y,
                  final float width,
                  final float height,
                  final Color color)
  {
    renderQuad(x,
               y,
               width,
               height,
               color.r,
               color.g,
               color.b,
               color.a);
  }

  void renderQuad(final float x,
                  final float y,
                  final float width,
                  final float height,
                  final float red,
                  final float green,
                  final float blue,
                  final float alpha)
  {
    renderQuad(x,
               y,
               width,
               height,
               red,
               green,
               blue,
               alpha,
               ShapeRenderer.ShapeType.Line);
  }

  void renderQuad(final float x,
                  final float y,
                  final float width,
                  final float height,
                  final float red,
                  final float green,
                  final float blue,
                  final float alpha,
                  final ShapeRenderer.ShapeType type)
  {
    Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(type);
    shapeRenderer.setColor(red, green, blue, alpha);
    shapeRenderer.rect(x, y, width, height);
    shapeRenderer.end();

    Gdx.gl.glDisable(GL20.GL_BLEND);
  }

  void renderLine(final float x1,
                  final float y1,
                  final float x2,
                  final float y2,
                  final Color color)
  {
    renderLine(x1,
               y1,
               x2,
               y2,
               color.r,
               color.g,
               color.b,
               color.a);
  }

  void renderLine(final float x1,
                  final float y1,
                  final float x2,
                  final float y2,
                  final float red,
                  final float green,
                  final float blue,
                  final float alpha)
  {
    Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(red, green, blue, alpha);
    shapeRenderer.line(x1, y1, x2, y2);
    shapeRenderer.end();

    Gdx.gl.glDisable(GL20.GL_BLEND);
  }

  void renderHudQuad(final float x,
                     final float y,
                     final float width,
                     final float height,
                     final Color color)
  {
    renderHudQuad(x,
                  y,
                  width,
                  height,
                  color.r,
                  color.g,
                  color.b,
                  color.a);
  }

  void renderHudQuad(final float x,
                     final float y,
                     final float width,
                     final float height,
                     final float red,
                     final float green,
                     final float blue,
                     final float alpha)
  {
    renderHudQuad(x,
                  y,
                  width,
                  height,
                  red,
                  green,
                  blue,
                  alpha,
                  ShapeRenderer.ShapeType.Line);
  }

  void renderHudFilledQuad(final float x,
                           final float y,
                           final float width,
                           final float height,
                           final Color color)
  {
    renderHudFilledQuad(x,
                        y,
                        width,
                        height,
                        color.r,
                        color.g,
                        color.b,
                        color.a);
  }

  void renderHudFilledQuad(final float x,
                           final float y,
                           final float width,
                           final float height,
                           final float red,
                           final float green,
                           final float blue,
                           final float alpha)
  {
    renderHudQuad(x,
                  y,
                  width,
                  height,
                  red,
                  green,
                  blue,
                  alpha,
                  ShapeRenderer.ShapeType.Filled);
  }


  void renderHudQuad(final float x,
                     final float y,
                     final float width,
                     final float height,
                     final float red,
                     final float green,
                     final float blue,
                     final float alpha,
                     final ShapeRenderer.ShapeType type)
  {
    Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    hudShapeRenderer.setProjectionMatrix(hudCamera.combined);
    hudShapeRenderer.begin(type);
    hudShapeRenderer.setColor(red, green, blue, alpha);
    hudShapeRenderer.rect(x, y, width, height);
    hudShapeRenderer.end();

    Gdx.gl.glDisable(GL20.GL_BLEND);
  }

  void dispose()
  {
    if (shapeRenderer != null)
    {
      shapeRenderer.dispose();
    }

    if (spriteBatch != null)
    {
      spriteBatch.dispose();
    }

    if (hudSpriteBatch != null)
    {
      hudSpriteBatch.dispose();
    }
  }

  public static Vector3 unproject(final Vector3 screen)
  {
    if (staticViewport != null)
    {
      return staticViewport.unproject(screen);
    }

    return null;
  }
}
