package com.orbinski.wizard;

class Enemy extends Movable
{
  int towerDamage;
  Villain inCombatWith;
  int damage;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;

  Enemy()
  {
    super(2.5f, 2.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 2.25f;
    velocityY = 2.25f;
    bounty = 2;
    towerDamage = 5;
    setHealth(10);
    setMaxHealth(10);
    setHealthBar(new HealthBar(-getWidthOffset(),
                               getHeightOffset() + 0.25f,
                               getWidth(),
                               0.25f,
                               getHealth(),
                               getMaxHealth()));
    damage = 2;
    canAttack = true;
    rateOfAttack = 0.9f;
  }

  @Override
  void update(final float delta)
  {
    super.update(delta);

    elapsedSinceLastAttack = elapsedSinceLastAttack + delta;

    if (elapsedSinceLastAttack >= rateOfAttack)
    {
      canAttack = true;
    }
  }

  void doAttack()
  {
    if (inCombatWith != null && canAttack)
    {
      inCombatWith.updateHealth(-damage);
      elapsedSinceLastAttack = 0.0f;
      canAttack = false;

      if (inCombatWith.getHealth() <= 0)
      {
        inCombatWith.dead = true;
      }
    }
  }
}
