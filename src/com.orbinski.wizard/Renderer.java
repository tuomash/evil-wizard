package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;

import static com.orbinski.wizard.Globals.*;

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

  final Texture knightTexture;
  final Texture minotaurTexture;
  final Texture towerTexture;
  final Texture orbTexture;
  final Texture tileTexture;
  final Texture jewelTexture;
  final Texture lightningBoltTexture;

  final BitmapFont font;

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
    knightTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "minotaur.png");
    minotaurTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "tower.png");
    towerTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "orb.jpg");
    orbTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "tileable_grass2.png");
    tileTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "jewel.png");
    jewelTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "lightning-bolt.png");
    lightningBoltTexture = new Texture(Gdx.files.absolute(file.getAbsolutePath()));

    file = new File(System.getProperty("user.dir") + File.separator + "hand_32.png");
    final Pixmap pm = new Pixmap(Gdx.files.absolute(file.getAbsolutePath()));
    Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
    pm.dispose();

    font = new BitmapFont(true);
    font.setColor(Color.RED);

    background = new Color(173.0f / 255.0f, 216.0f / 255.0f, 230.0f / 255.0f, 1.0f);

    game.loadTextureReferences(this);
  }

  void render()
  {
    ScreenUtils.clear(Color.BLACK);

    viewport.apply();
    shapeRenderer.setProjectionMatrix(camera.combined);
    spriteBatch.setProjectionMatrix(camera.combined);

    renderBackground();
    renderTower();
    renderJewels();
    renderEnemies();
    renderVillains();
    renderProjectiles();
    renderSpell();

    hudViewport.apply();
    hudShapeRenderer.setProjectionMatrix(hudCamera.combined);
    hudSpriteBatch.setProjectionMatrix(hudCamera.combined);
    renderHud();

    if (game.gameOver)
    {
      renderHudDimBackground();

      hudSpriteBatch.begin();
      font.draw(hudSpriteBatch, "GAME OVER", 100, 100);
      font.draw(hudSpriteBatch, "Press R to restart", 100, 120);
      hudSpriteBatch.end();
    }
  }

  void renderBackground()
  {
    float x = camera.position.x - WORLD_WIDTH / 2;
    float y = camera.position.y + WORLD_HEIGHT / 2;
    final float tileWidth = 8.0f;
    final float tileHeight = 8.0f;
    final int rows = (int) (WORLD_WIDTH / tileWidth) + 1;
    final int columns = (int) (WORLD_HEIGHT / tileHeight) + 3;

    spriteBatch.begin();

    for (int i = 0; i < columns; i++)
    {
      for (int z = 0; z < rows; z++)
      {
        spriteBatch.draw(tileTexture,
                         x,
                         y,
                         tileWidth,
                         tileHeight);

        x = x + tileWidth;
      }

      x = camera.position.x - WORLD_WIDTH / 2;
      y = y - tileWidth;
    }

    spriteBatch.end();
  }

  void renderTower()
  {
    renderEntity(game.tower, towerTexture);
    renderHealthBar(game.tower.getHealthBar());

    if (game.tower.selected)
    {
      renderEntityBorder(game.tower, Color.WHITE);
      renderCircle(game.tower.range.x,
                   game.tower.range.y,
                   game.tower.range.radius,
                   Color.RED);
    }
  }

  void renderVillains()
  {
    for (int i = 0; i < game.villains.size(); i++)
    {
      final Villain villain = game.villains.get(i);

      if (!villain.dead && villain.inAction)
      {
        renderEntity(villain, minotaurTexture);
        renderHealthBar(villain.getHealthBar());
        // renderEntityBorder(villain, Color.BLUE);

        if (game.selectedVillain == villain)
        {
          renderEntityBorder(villain, Color.WHITE);
        }
      }
    }
  }

  void renderJewels()
  {
    for (int i = 0; i < game.jewels.length; i++)
    {
      final Jewel jewel = game.jewels[i];
      renderEntity(jewel, jewelTexture);
    }
  }

  void renderEnemies()
  {
    for (int i = 0; i < game.enemies.size(); i++)
    {
      final Enemy enemy = game.enemies.get(i);

      if (!enemy.dead)
      {
        renderEntity(enemy, knightTexture);
        renderHealthBar(enemy.getHealthBar());
        // renderEntityBorder(enemy, Color.RED);
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
        renderEntity(projectile, orbTexture);
        // renderFilledEntity(projectile, Color.WHITE);
      }
    }
  }

  void renderSpell()
  {
    if (game.selectedSpell != null)
    {
      renderEntity(game.selectedSpell, game.selectedSpell.texture);
      // renderEntityBorder(game.selectedSpell, Color.RED);
      // renderCircle(game.selectedSpell.range.x,
      //             game.selectedSpell.range.y,
      //             game.selectedSpell.range.radius,
      //             Color.RED);
    }
  }

  void renderHealthBar(final HealthBar bar)
  {
    if (bar != null)
    {
      renderFilledQuad(bar.getX(),
                       bar.getY(),
                       bar.getWidth(),
                       bar.getHeight(),
                       HealthBar.DARK_RED);
      renderFilledQuad(bar.getX(),
                       bar.getY(),
                       bar.getGreenBarWidth(),
                       bar.getHeight(),
                       Color.GREEN);
    }
  }

  void renderHud()
  {
    for (int i = 0; i < game.villains.size(); i++)
    {
      final Villain villain = game.villains.get(i);

      if (!villain.inAction)
      {
        renderHudTexture(UserInterface.villainIcon.x,
                         UserInterface.villainIcon.y,
                         UserInterface.villainIcon.width,
                         UserInterface.villainIcon.height,
                         minotaurTexture);
      }
    }

    hudSpriteBatch.begin();
    font.draw(hudSpriteBatch, "Gold: " + game.gold, 5, 60);
    hudSpriteBatch.end();
  }

  void renderEntity(final Entity entity, final Texture texture)
  {
    if (entity != null)
    {
      spriteBatch.begin();
      spriteBatch.draw(texture,
                       entity.getBottomLeftCornerX(),
                       entity.getBottomLeftCornerY(),
                       entity.getWidth(),
                       entity.getHeight());
      spriteBatch.end();
    }
  }

  void renderEntityBorder(final Entity entity, final Color borderColor)
  {
    renderQuad(entity.getBottomLeftCornerX(),
               entity.getBottomLeftCornerY(),
               entity.getWidth(),
               entity.getHeight(),
               borderColor);
  }

  void renderFilledEntity(final Entity entity, final Color borderColor)
  {
    renderFilledQuad(entity.getBottomLeftCornerX(),
                     entity.getBottomLeftCornerY(),
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

  void renderCircle(final float x,
                    final float y,
                    final float radius,
                    final Color color)
  {
    renderCircle(x,
                 y,
                 radius,
                 color.r,
                 color.g,
                 color.b,
                 color.a,
                 ShapeRenderer.ShapeType.Line);
  }

  void renderCircle(final float x,
                    final float y,
                    final float radius,
                    final float red,
                    final float green,
                    final float blue,
                    final float alpha)
  {
    renderCircle(x,
                 y,
                 radius,
                 red,
                 green,
                 blue,
                 alpha,
                 ShapeRenderer.ShapeType.Line);
  }

  void renderCircle(final float x,
                    final float y,
                    final float radius,
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
    shapeRenderer.circle(x, y, radius);
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

  private void renderHudDimBackground()
  {
    renderHudFilledQuad(0,
                        0,
                        Globals.SCREEN_WIDTH,
                        Globals.SCREEN_HEIGHT,
                        Color.BLACK.r,
                        Color.BLACK.g,
                        Color.BLACK.b,
                        100 / 255.0f);
  }

  void renderHudTexture(final float x,
                        final float y,
                        final float width,
                        final float height,
                        final Texture texture)
  {
    hudSpriteBatch.begin();
    hudSpriteBatch.draw(texture,
                        x,
                        y,
                        width,
                        height);
    hudSpriteBatch.end();
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

    if (hudShapeRenderer != null)
    {
      hudShapeRenderer.dispose();
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
