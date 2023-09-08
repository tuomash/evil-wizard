package com.orbinski.wizard;

class Enemy
{
  private float y;
  private float x;
  private float prevX;
  private float prevY;
  float targetX;
  float targetY;
  float velocityX = 0.0f;
  float velocityY = 0.0f;
  float topLeftCornerX;
  float topLeftCornerY;
  float width = 2.5f;
  float widthOffset = width / 2.0f;
  float height = 2.5f;
  float heightOffset = height / 2.0f;
  boolean moving;

  Enemy()
  {
    setX(-1000.0f);
    setY(-1000.0f);
  }

  void update(final float delta)
  {
    if (moving)
    {
      if (targetX > x)
      {
        setX(x + (velocityX * delta));
      }
      else if (targetX < x)
      {
        setX(x - (velocityX * delta));
      }

      if (targetY > y)
      {
        setY(y + (velocityY * delta));
      }
      else if (targetY < y)
      {
        setY(y - (velocityY * delta));
      }
    }
  }

  float getX()
  {
    return x;
  }

  void setX(final float x)
  {
    prevX = this.x;
    this.x = x;
    topLeftCornerX = x - widthOffset;
  }

  float getY()
  {
    return y;
  }

  void setY(final float y)
  {
    prevY = this.y;
    this.y = y;
    topLeftCornerY = y - heightOffset;
  }

  float getPrevX()
  {
    return prevX;
  }

  float getPrevY()
  {
    return prevY;
  }
}
