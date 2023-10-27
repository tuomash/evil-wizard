package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;

class HealthBar extends Entity
{
  static Color DARK_RED = new Color(139.0f / 255.0f, 0.0f, 0.0f, 1.0f);

  private float offsetX;
  private float offsetY;
  private float healthPercentage;
  private float greenBarWidth;

  HealthBar(final float offsetX,
            final float offsetY,
            final float width,
            final float height,
            final int health,
            final int maxHealth)
  {
    super(width, height);
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.greenBarWidth = width;
    setHealth(health);
    setMaxHealth(maxHealth);

    updateBar(health, maxHealth);
  }

  void updateBar(final int health, final int maxHealth)
  {
    healthPercentage = (float) health / maxHealth;
    greenBarWidth = getWidth() * healthPercentage;
  }

  void reset()
  {
    healthPercentage = 1.0f;
    greenBarWidth = getWidth();
  }

  float getOffsetX()
  {
    return offsetX;
  }

  float getOffsetY()
  {
    return offsetY;
  }

  float getGreenBarWidth()
  {
    return greenBarWidth;
  }
}
