package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;

class Overlay extends UIElement
{
  static Color DARK_RED = new Color(139.0f / 255.0f, 0.0f, 0.0f, 1.0f);

  Color color;
  float percentage = 1.0f;

  public Overlay()
  {
    color = new Color(DARK_RED.r, DARK_RED.g, DARK_RED.b, 120 / 255.0f);
    visible = false;
  }
}
