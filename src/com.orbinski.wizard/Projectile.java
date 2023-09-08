package com.orbinski.wizard;

class Projectile extends Movable
{
  Movable target;

  Projectile()
  {
    super(0.5f, 0.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 30.0f;
    velocityY = 30.0f;
  }

  @Override
  void update(final float delta)
  {
    if (target != null)
    {
      targetX = target.getX();
      targetY = target.getY();
    }

    super.update(delta);
  }
}
