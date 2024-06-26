package com.orbinski.wizard;

class Villain extends Entity
{
  boolean inAction;
  Enemy enemyTarget;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;
  float elapsedSinceLastHeal;
  float rateOfHealing;
  int regen;
  float elapsedDeath;
  float rateOfRespawn;
  Icon hotBarIcon;

  Villain()
  {
    super(6.5f, 6.5f);
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
    setDamage(7, 9);
    canAttack = true;
    rateOfAttack = 1.1f;
    rateOfHealing = 1.3f;
    regen = 2;
    rateOfRespawn = 8.0f;
    moveSound = Audio.minotaurGrowl;
  }

  @Override
  void update(final float delta)
  {
    super.update(delta);

    if (!inAction)
    {
      elapsedDeath = elapsedDeath + delta;

      if (elapsedDeath >= rateOfRespawn)
      {
        reset();
        setX(-10.0f);
        setY(0.0f);
      }
    }
    else if (enemyTarget != null)
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
        increaseHealth(regen);
        elapsedSinceLastHeal = 0.0f;
      }
    }

    if (hotBarIcon != null)
    {
      if (inAction)
      {
        hotBarIcon.getOverlay().visible = false;
      }
      else
      {
        float percentage = elapsedDeath / rateOfRespawn;
        percentage = 1.0f - percentage;

        if (percentage < 0.0f)
        {
          percentage = 0.0f;
        }

        hotBarIcon.getOverlay().updateWidth(percentage);
        hotBarIcon.getOverlay().visible = true;
      }
    }
  }

  void doAttack()
  {
    if (enemyTarget != null && canAttack)
    {
      enemyTarget.decreaseHealth(getDamage().calculate());
      elapsedSinceLastAttack = 0.0f;
      canAttack = false;
    }
  }

  void reset()
  {
    dead = false;
    inAction = true;
    canAttack = true;
    elapsedSinceLastAttack = 0.0f;
    elapsedSinceLastHeal = 0.0f;
    elapsedDeath = 0.0f;
    setHealth(100);
    setMaxHealth(100);
  }

  void loadUIReferences()
  {
    hotBarIcon = UserInterface.hotBarIconMinotaur;
  }
}
