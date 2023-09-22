package com.orbinski.wizard;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

class TextButton extends UIElement
{
  private int textX;
  private int textY;

  String text;
  BitmapFont font;
  int textOffsetX;
  int textOffsetY;

  @Override
  void setX(final int x)
  {
    super.setX(x);
    setTextX(x);
  }

  @Override
  void setY(final int y)
  {
    super.setY(y);
    setTextY(y);
  }

  int getTextX()
  {
    return textX;
  }

  void setTextX(final int textX)
  {
    this.textX = textX + textOffsetX;
  }

  int getTextY()
  {
    return textY;
  }

  void setTextY(final int textY)
  {
    this.textY = textY + textOffsetY;
  }
}
