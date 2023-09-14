package com.orbinski.wizard;

class Projectile extends Movable
{
  Movable target;
  int damage;

  Projectile()
  {
    super(0.5f, 0.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 55.0f;
    velocityY = 55.0f;
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
