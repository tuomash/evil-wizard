package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;

import static com.orbinski.wizard.Globals.WORLD_HEIGHT;
import static com.orbinski.wizard.Globals.WORLD_WIDTH;

class Renderer
{
  static Viewport gameViewportRef;

  static Texture knightTexture;
  static Texture trollTexture;
  static Texture towerTexture;
  static Texture orbTexture;
  static Texture tileTexture;
  static Texture jewelTexture;
  static Texture lightningBoltTexture;
  static Texture greaseTexture;
  static Texture treeTexture;

  static Texture uiTrollIconTexture;
  static Texture uiLightningBoltIconTexture;
  static Texture uiGreaseIconTexture;

  final Game game;
  final OrthographicCamera camera;
  final Viewport viewport;
  final ShapeRenderer shapeRenderer;
  final SpriteBatch spriteBatch;

  final Color background;

  Renderer(final Game game)
  {
    this.game = game;

    camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
    camera.update();

    viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
    gameViewportRef = viewport;

    shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.setAutoShapeType(true);

    spriteBatch = new SpriteBatch();
    spriteBatch.setProjectionMatrix(camera.combined);

    knightTexture = loadTexture("knight.png");
    trollTexture = loadTexture("troll_1.png");
    towerTexture = loadTexture("tower.png");
    orbTexture = loadTexture("orb.png");
    tileTexture = loadTexture("tileable_grass_00.png");
    jewelTexture = loadTexture("jewel.png");
    lightningBoltTexture = loadTexture("lightning-bolt.png");
    greaseTexture = loadTexture("grease2.png");
    treeTexture = loadTexture("tree.png");

    uiTrollIconTexture = loadTexture("ui-villain-troll-icon.png");
    uiLightningBoltIconTexture = loadTexture("ui-spell-lightning-bolt-icon.png");
    uiGreaseIconTexture = loadTexture("ui-spell-grease-icon-2.png");

    background = new Color(173.0f / 255.0f, 216.0f / 255.0f, 230.0f / 255.0f, 1.0f);
  }

  private Texture loadTexture(final String fileName)
  {
    final File file = new File(System.getProperty("user.dir")
                                   + File.separator
                                   + "graphics"
                                   + File.separator
                                   + fileName);
    return new Texture(Gdx.files.absolute(file.getAbsolutePath()));
  }

  void render()
  {
    ScreenUtils.clear(Color.BLACK);

    viewport.apply();
    shapeRenderer.setProjectionMatrix(camera.combined);
    spriteBatch.setProjectionMatrix(camera.combined);

    if (!game.help)
    {
      renderBackground();
      renderAreaEffects();
      renderTrees();
      renderTower();
      renderJewels();
      renderEnemies();
      renderVillains();
      renderProjectiles();
      renderSpell();
      renderSpellEffects();
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

  void renderTrees()
  {
    for (int i = 0; i < game.trees.size(); i++)
    {
      final Tree tree = game.trees.get(i);
      renderEntity(tree, treeTexture);
    }
  }

  void renderAreaEffects()
  {
    for (int i = 0; i < game.areaEffects.size(); i++)
    {
      final AreaEffect effect = game.areaEffects.get(i);

      if (!effect.dead)
      {
        renderEntity(effect, effect.texture);
        renderCircle(effect.area.x,
                     effect.area.y,
                     effect.area.radius,
                     Color.BLACK);
      }
    }
  }

  void renderVillains()
  {
    for (int i = 0; i < game.villains.size(); i++)
    {
      final Villain villain = game.villains.get(i);

      if (!villain.dead && villain.inAction)
      {
        renderEntity(villain, trollTexture);
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
      // TODO: render spell icon
      // if (game.selectedSpell.texture != null)
      // {
      // renderEntity(game.selectedSpell, game.selectedSpell.texture);
      // }

      if (game.selectedSpell.showBorder)
      {
        renderEntityBorder(game.selectedSpell, Color.RED);
      }

      if (game.selectedSpell.showRange)
      {
        renderCircle(game.selectedSpell.range.x,
                     game.selectedSpell.range.y,
                     game.selectedSpell.range.radius,
                     Color.RED);
      }
    }
  }

  void renderSpellEffects()
  {
    for (int i = 0; i < game.spellEffects.size(); i++)
    {
      final SpellEffect effect = game.spellEffects.get(i);
      renderEntity(effect, effect.texture);
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

  void renderEntity(final Entity entity, final Texture texture)
  {
    if (entity != null && entity.visible && texture != null)
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
  }

  public static Vector2 unproject(final Vector2 screen)
  {
    if (gameViewportRef != null)
    {
      return gameViewportRef.unproject(screen);
    }

    return null;
  }

  public static Vector2 project(final Vector2 world)
  {
    if (gameViewportRef != null)
    {
      return gameViewportRef.project(world);
    }

    return null;
  }
}
