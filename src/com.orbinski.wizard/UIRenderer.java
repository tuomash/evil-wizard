package com.orbinski.wizard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;

class UIRenderer
{
  static Viewport viewportRef;

  static BitmapFont font24White;
  static BitmapFont font16White;
  static BitmapFont font12Yellow;

  final Game game;
  final OrthographicCamera camera;
  final ScreenViewport viewport;
  final ShapeRenderer shapeRenderer;
  final SpriteBatch spriteBatch;

  UIRenderer(final Game game)
  {
    this.game = game;

    camera = new OrthographicCamera();
    camera.update();

    viewport = new ScreenViewport(camera);
    viewportRef = viewport;

    spriteBatch = new SpriteBatch();
    spriteBatch.setProjectionMatrix(camera.combined);

    shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.setAutoShapeType(true);

    font24White = new BitmapFont(true);

    final File file = new File(System.getProperty("user.dir")
                                   + File.separator
                                   + "graphics"
                                   + File.separator
                                   + "lunchds.ttf");
    final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(file.getAbsolutePath()));

    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 24;
    parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
    font24White = generator.generateFont(parameter);

    parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 16;
    parameter.color = Color.WHITE;
    font16White = generator.generateFont(parameter);

    parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 18;
    parameter.color = Color.YELLOW;
    font12Yellow = generator.generateFont(parameter);
  }

  void render()
  {
    viewport.apply();
    shapeRenderer.setProjectionMatrix(camera.combined);
    spriteBatch.setProjectionMatrix(camera.combined);

    if (game.gameOver)
    {
      renderDimBackground();

      spriteBatch.begin();
      font24White.draw(spriteBatch, "GAME OVER", 100, 400);
      font24White.draw(spriteBatch, "Press R to restart", 100, 320);
      spriteBatch.end();
    }
    else if (game.victory)
    {
      renderDimBackground();

      spriteBatch.begin();
      font24White.draw(spriteBatch, "CONGRATULATIONS! YOU HAVE DESTROYED THE FORCES OF GOOD.", 100, 400);
      font24White.draw(spriteBatch, "Press R to restart", 100, 320);
      spriteBatch.end();
    }

    if (!game.help)
    {
      if (game.paused)
      {
        renderDimBackground();
      }

      renderHud();

    }
    else
    {
      renderHelp();
    }
  }

  void renderHelp()
  {
    for (int i = 0; i < UserInterface.help.texts.size(); i++)
    {
      final Text text = UserInterface.help.texts.get(i);
      renderText(text);
    }
  }

  void renderHud()
  {
    spriteBatch.begin();

    if (game.paused)
    {
      font24White.draw(spriteBatch, "PAUSED", 5, 200);
    }

    font24White.draw(spriteBatch, "Speed " + game.getSpeed() + "x", 5, 160);
    font24White.draw(spriteBatch, "Wave " + game.waves.waveNumber + " / " + game.waves.maxWaves, 5, 120);
    font24White.draw(spriteBatch, "Gold: " + game.gold, 5, 80);

    for (int i = 0; i < game.textEffects.size(); i++)
    {
      final TextEffect effect = game.textEffects.get(i);
      effect.font.draw(spriteBatch, effect.text, effect.screen.x, effect.screen.y);
    }

    spriteBatch.end();

    renderUIElement(UserInterface.hotBarIconMinotaur);
    renderUIElement(UserInterface.hotBarIconLightningSpell);
    renderUIElement(UserInterface.hotBarIconGreaseSpell);
    renderTextButton(UserInterface.nextWaveButton);
  }

  void renderOverlay(final Overlay overlay)
  {
    if (overlay != null && overlay.visible)
    {
      renderFilledQuad(overlay.getX(),
                       overlay.getY(),
                       overlay.getWidth(),
                       overlay.getHeight(),
                       overlay.color);
    }
  }

  void renderText(final Text text)
  {
    if (text != null && text.visible && text.font != null && text.text != null && !text.text.isEmpty())
    {
      renderUIElement(text);
      spriteBatch.begin();
      text.font.draw(spriteBatch,
                     text.text,
                     text.getX(),
                     text.getY());
      spriteBatch.end();
    }
  }

  void renderTextButton(final TextButton button)
  {
    if (button != null && button.visible)
    {
      renderUIElement(button);
      spriteBatch.begin();
      button.font.draw(spriteBatch,
                       UserInterface.nextWaveButton.text,
                       UserInterface.nextWaveButton.getTextX(),
                       UserInterface.nextWaveButton.getTextY());
      spriteBatch.end();
    }
  }

  void renderUIElement(final UIElement element)
  {
    if (element != null)
    {
      if (element.backgroundColor != null)
      {
        renderFilledQuad(element.getX(),
                         element.getY(),
                         element.getWidth(),
                         element.getHeight(),
                         element.backgroundColor);
      }

      if (element.texture != null)
      {
        spriteBatch.begin();
        spriteBatch.draw(element.texture, element.getX(), element.getY(), element.getWidth(), element.getHeight());
        spriteBatch.end();
      }

      if (element.getOverlay() != null)
      {
        renderOverlay(element.getOverlay());
      }
    }
  }


  private void renderDimBackground()
  {
    renderFilledQuad(0,
                     0,
                     Globals.SCREEN_WIDTH,
                     Globals.SCREEN_HEIGHT,
                     Color.BLACK.r,
                     Color.BLACK.g,
                     Color.BLACK.b,
                     100 / 255.0f);
  }

  void renderTexture(final float x,
                     final float y,
                     final float width,
                     final float height,
                     final Texture texture)
  {
    spriteBatch.begin();
    spriteBatch.draw(texture,
                     x,
                     y,
                     width,
                     height);
    spriteBatch.end();
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

  void dispose()
  {
    if (spriteBatch != null)
    {
      spriteBatch.dispose();
    }

    if (shapeRenderer != null)
    {
      shapeRenderer.dispose();
    }
  }

  public static Vector2 unproject(final Vector2 screen)
  {
    if (viewportRef != null)
    {
      return viewportRef.unproject(screen);
    }

    return null;
  }
}
