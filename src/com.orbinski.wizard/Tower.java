package com.orbinski.wizard;

class Tower
{
  float y = 0.0f;
  float x = 0.0f;
  float topLeftCornerX;
  float topLeftCornerY;
  float centerX;
  float centerY;
  float width = 8.0f;
  float widthOffset = width / 2.0f;
  float height = 30.0f;
  float heightOffset = height / 2.0f;
  float centerWidth = 1.0f;
  float centerHeight = 1.0f;
  float centerWidthOffset = centerWidth / 2.0f;
  float centerHeightOffset = centerHeight / 2.0f;

  void update(final float delta)
  {
    topLeftCornerX = x - widthOffset;
    topLeftCornerY = y - heightOffset;
    centerX = x - centerWidthOffset;
    centerY = y - centerHeightOffset;
  }
}
