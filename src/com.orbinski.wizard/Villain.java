package com.orbinski.wizard;

class Villain extends Movable
{
  boolean inAction;
  Enemy enemyTarget;
  int damage;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;
  float elapsedSinceLastHeal;
  float rateOfHealing;
  int regen;

  Villain()
  {
    super(4.0f, 4.0f);
    setX(0.0f);
    setY(0.0f);
    velocityX = 16.0f;
    velocityY = 16.0f;
    setHealth(100);
    setMaxHealth(100);
    setHealthBar(new HealthBar(-getWidthOffset(),
                               getHeightOffset() + 0.5f,
                               getWidth(),
                               0.5f,
                               getHealth(),
                               getMaxHealth()));
    damage = 8;
    canAttack = true;
    rateOfAttack = 1.1f;
    rateOfHealing = 1.3f;
    regen = 2;
  }

  @Override
  void update(final float delta)
  {
    super.update(delta);

    if (enemyTarget != null)
    {
      elapsedSinceLastAttack = elapsedSinceLastAttack + delta;

      if (elapsedSinceLastAttack >= rateOfAttack)
      {
        canAttack = true;
      }
    }
    else if (getHealth() < getMaxHealth())
    {
      elapsedSinceLastHeal = elapsedSinceLastHeal + delta;

      if (elapsedSinceLastHeal >= rateOfHealing)
      {
        updateHealth(regen);
        elapsedSinceLastHeal = 0.0f;
      }
    }
  }

  void doAttack()
  {
    if (enemyTarget != null && canAttack)
    {
      enemyTarget.updateHealth(-damage);
      elapsedSinceLastAttack = 0.0f;
      canAttack = false;

      if (enemyTarget.getHealth() <= 0)
      {
        enemyTarget.dead = true;
      }
    }
  }

  void reset()
  {
    dead = false;
    inAction = true;
    canAttack = true;
    setHealth(100);
    setMaxHealth(100);
  }
}
