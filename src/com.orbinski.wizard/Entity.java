package com.orbinski.wizard;

import com.badlogic.gdx.math.Rectangle;

abstract class Entity
{
  private static final Rectangle entityA = new Rectangle();
  private static final Rectangle entityB = new Rectangle();

  static boolean intersects(final Entity a, final Entity b)
  {
    if (a == null || b == null)
    {
      return false;
    }

    entityA.x = a.bottomLeftCornerX;
    entityA.y = a.bottomLeftCornerY;
    entityA.width = a.width;
    entityA.height = a.height;

    entityB.x = b.bottomLeftCornerX;
    entityB.y = b.bottomLeftCornerY;
    entityB.width = b.width;
    entityB.height = b.height;

    return entityA.overlaps(entityB);
  }

  static boolean contains(final Entity a, final float x, final float y)
  {
    if (a == null)
    {
      return false;
    }

    entityA.x = a.bottomLeftCornerX;
    entityA.y = a.bottomLeftCornerY;
    entityA.width = a.width;
    entityA.height = a.height;

    return entityA.contains(x, y);
  }

  private float x;
  private float y;
  private float prevX;
  private float prevY;
  private float bottomLeftCornerX;
  private float bottomLeftCornerY;
  private float width;
  private float widthOffset;
  private float height;
  private float heightOffset;
  int health;
  int maxHealth;
  private HealthBar healthBar;

  Entity()
  {
    setWidth(2.5f);
    setHeight(2.5f);
  }

  Entity(final float width, final float height)
  {
    setWidth(width);
    setHeight(height);
  }

  Entity(final float x, final float y, final float width, final float height)
  {
    setX(x);
    setY(y);
    setWidth(width);
    setHeight(height);
  }

  float getX()
  {
    return x;
  }

  void setX(final float x)
  {
    prevX = this.x;
    this.x = x;
    bottomLeftCornerX = x - widthOffset;

    if (healthBar != null)
    {
      healthBar.setX(getX() + healthBar.getOffsetX());
    }
  }

  float getY()
  {
    return y;
  }

  void setY(final float y)
  {
    prevY = this.y;
    this.y = y;
    bottomLeftCornerY = y - heightOffset;

    if (healthBar != null)
    {
      healthBar.setY(getY() + healthBar.getOffsetY());
    }
  }

  float getPrevX()
  {
    return prevX;
  }

  float getPrevY()
  {
    return prevY;
  }

  float getBottomLeftCornerX()
  {
    return bottomLeftCornerX;
  }

  float getBottomLeftCornerY()
  {
    return bottomLeftCornerY;
  }

  float getWidth()
  {
    return width;
  }

  void setWidth(final float width)
  {
    this.width = width;
    widthOffset = width / 2.0f;
  }

  float getWidthOffset()
  {
    return widthOffset;
  }

  float getHeight()
  {
    return height;
  }

  void setHeight(final float height)
  {
    this.height = height;
    heightOffset = height / 2.0f;
  }

  float getHeightOffset()
  {
    return heightOffset;
  }

  HealthBar getHealthBar()
  {
    return healthBar;
  }

  void setHealthBar(final HealthBar healthBar)
  {
    this.healthBar = healthBar;
    healthBar.setX(getX() + healthBar.getOffsetX());
    healthBar.setY(getY() + healthBar.getOffsetY());
  }
}
