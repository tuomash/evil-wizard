package com.orbinski.wizard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

class TextEffect extends Entity
{
  private final float aliveFor;
  final String text;
  final BitmapFont font;
  final Vector2 world;
  final Vector2 screen;

  private float elapsed;
  boolean finished;

  public TextEffect(final float x,
                    final float y,
                    final float aliveFor,
                    final String text,
                    final BitmapFont font)
  {
    this.aliveFor = aliveFor;
    this.text = text;
    this.font = font;
    world = new Vector2();
    screen = new Vector2();
    setX(x);
    setY(y);
  }

  void update(final float delta)
  {
    if (finished)
    {
      return;
    }

    elapsed = elapsed + delta;
    setY(getY() + 0.03f);
    world.x = getX();
    world.y = getY();
    final Vector2 screen = Renderer.project(world);

    if (screen != null)
    {
      this.screen.x = screen.x;
      this.screen.y = screen.y;
    }

    if (elapsed >= aliveFor)
    {
      finished = true;
    }
  }
}
