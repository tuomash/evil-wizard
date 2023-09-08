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
      if (targetX > getX())
      {
        setX(getX() + velocityX * delta);
      }
      else if (targetX < getX())
      {
        setX(getX() - velocityX * delta);
      }

      if (targetY > getY())
      {
        setY(getY() + velocityY * delta);
      }
      else if (targetY < getY())
      {
        setY(getY() - velocityY * delta);
      }
    }
  }
}
