package com.orbinski.wizard;

class Villain extends Movable
{
  boolean inAction;

  public Villain()
  {
    super(4.0f, 4.0f);
    setX(0.0f);
    setY(0.0f);
    velocityX = 16.0f;
    velocityY = 16.0f;
    health = 100;
    maxHealth = 100;
    setHealthBar(new HealthBar(-getWidthOffset(),
                               getHeightOffset() + 0.5f,
                               getWidth(),
                               0.5f,
                               health,
                               maxHealth));
  }
}
