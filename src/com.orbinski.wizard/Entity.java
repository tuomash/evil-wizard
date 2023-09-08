package com.orbinski.wizard;

abstract class Entity
{
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
