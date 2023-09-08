package com.orbinski.wizard;

import java.awt.geom.Rectangle2D;

abstract class Entity
{
  private static final Rectangle2D.Float entityA = new Rectangle2D.Float();
  private static final Rectangle2D.Float entityB = new Rectangle2D.Float();

  static boolean intersects(final Entity a, final Entity b)
  {
    if (a == null || b == null)
    {
      return false;
    }

    entityA.x = a.topLeftCornerX;
    entityA.y = a.topLeftCornerY;
    entityA.width = a.width;
    entityA.height = a.height;

    entityB.x = b.topLeftCornerX;
    entityB.y = b.topLeftCornerY;
    entityB.width = b.width;
    entityB.height = b.height;

    return entityA.intersects(entityB);
  }

  private float x;
  private float y;
  private float prevX;
  private float prevY;
  private float topLeftCornerX;
  private float topLeftCornerY;
  private float width;
  private float widthOffset;
  private float height;
  private float heightOffset;

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

  float getTopLeftCornerX()
  {
    return topLeftCornerX;
  }

  float getTopLeftCornerY()
  {
    return topLeftCornerY;
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
}
