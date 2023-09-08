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

  // Interpolated variables
  private float ipX;
  private float ipY;
  private float ipTopLeftCornerX;
  private float ipTopLeftCornerY;

  public Entity()
  {
    setWidth(2.5f);
    setHeight(2.5f);
  }

  public Entity(final float width, final float height)
  {
    this.width = width;
    this.height = height;
  }

  void interpolate(final float alpha)
  {
    ipX = x * alpha + prevX * (1.0f - alpha);
    ipY = y * alpha + prevY * (1.0f - alpha);
    ipTopLeftCornerX = x - widthOffset;
    ipTopLeftCornerY = y - heightOffset;
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

  public float getWidth()
  {
    return width;
  }

  public void setWidth(final float width)
  {
    this.width = width;
    widthOffset = width / 2.0f;
  }

  public float getWidthOffset()
  {
    return widthOffset;
  }

  public float getHeight()
  {
    return height;
  }

  public void setHeight(final float height)
  {
    this.height = height;
    heightOffset = height / 2.0f;
  }

  public float getHeightOffset()
  {
    return heightOffset;
  }

  public float getIpX()
  {
    return ipX;
  }

  public float getIpY()
  {
    return ipY;
  }

  public float getIpTopLeftCornerX()
  {
    return ipTopLeftCornerX;
  }

  public float getIpTopLeftCornerY()
  {
    return ipTopLeftCornerY;
  }
}
