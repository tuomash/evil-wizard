package com.orbinski.wizard;

class Villain extends Movable
{
  boolean inAction;
  Enemy inCombatWith;
  int damage;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;

  Villain()
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
    damage = 8;
    canAttack = true;
    rateOfAttack = 1.1f;
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
      inCombatWith.health = inCombatWith.health - damage;
      inCombatWith.getHealthBar().updateBar(inCombatWith.health, inCombatWith.maxHealth);
      elapsedSinceLastAttack = 0.0f;
      canAttack = false;

      if (inCombatWith.health <= 0)
      {
        inCombatWith.dead = true;
      }
    }
  }

  void reset()
  {
    dead = false;
    inAction = true;
    health = 100;
    maxHealth = 100;
    getHealthBar().updateBar(health, maxHealth);
  }
}
