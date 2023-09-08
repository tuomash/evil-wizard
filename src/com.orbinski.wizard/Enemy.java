package com.orbinski.wizard;

class Enemy extends Entity
{
  float targetX;
  float targetY;
  float velocityX;
  float velocityY;
  boolean moving;
  boolean dead;

  Enemy()
  {
    super(2.5f, 2.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 8.0f;
    velocityY = 8.0f;
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
