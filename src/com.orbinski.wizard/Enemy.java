package com.orbinski.wizard;

class Enemy extends Movable
{
  int towerDamage;

  Enemy()
  {
    super(2.5f, 2.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 2.25f;
    velocityY = 2.25f;
    gold = 2;
    towerDamage = 5;
    health = 10;
    maxHealth = 10;
    setHealthBar(new HealthBar(-getWidthOffset(),
                               getHeightOffset() + 0.25f,
                               getWidth(),
                               0.25f,
                               health,
                               maxHealth));
  }
}
