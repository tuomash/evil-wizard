package com.orbinski.wizard;

class Movable extends Entity
{
  float targetX;
  float targetY;
  float velocityX;
  float velocityY;
  boolean moving;
  boolean dead;

  public Movable()
  {
  }

  public Movable(final float width, final float height)
  {
    super(width, height);
  }

  void update(final float delta)
  {
    if (dead)
    {
      return;
    }

    if (moving)
    {
      final float distance = MathUtils.distance(getX(), getY(), targetX, targetY);
      final float toTargetX = (targetX - getX()) / distance;
      final float toTargetY = (targetY - getY()) / distance;

      setX(getX() + (toTargetX * (velocityX * delta)));
      setY(getY() + (toTargetY * (velocityY * delta)));
    }
  }
}
