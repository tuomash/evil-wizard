package com.orbinski.wizard;

class HealthBar extends Entity
{
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
