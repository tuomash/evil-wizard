package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

class AreaEffect extends Entity
{
  final Circle area;
  float elapsed;
  final float aliveFor;

  AreaEffect(final Circle area, final float width, final float height)
  {
    super(width, height);
    this.area = area;
    aliveFor = 5.0f;
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
