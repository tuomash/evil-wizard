package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

class AreaEffect extends Entity
{
  final Circle area;
  final float aliveFor;
  float elapsed;
  Texture texture;

  AreaEffect(final Circle area,
             final float width,
             final float height,
             final float aliveFor)
  {
    super(width, height);
    this.area = area;
    this.aliveFor = aliveFor;
  }

  void update(final float delta)
  {
    if (!dead)
    {
      elapsed = elapsed + delta;

      if (elapsed >= aliveFor)
      {
        dead = true;
      }
    }
  }

  void apply(final Entity entity)
  {
    if (dead)
    {
      return;
    }

    if (Entity.overlaps(area, entity))
    {
      entity.slowdown = true;
    }
  }
}
