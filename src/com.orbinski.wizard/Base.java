package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

import java.util.List;

class Base extends Entity
{
  Enemy target;
  Circle range;
  boolean selected;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;

  Base()
  {
    super(8.0f, 30.0f);
    setX(0.0f);
    setY(0.0f);
    range = new Circle(getX(), getY(), 40.0f);
    setHealth(1000);
    setMaxHealth(1000);
    setHealthBar(new HealthBar(-getWidthOffset(),
                               getHeightOffset() + 1.0f,
                               getWidth(),
                               1.0f,
                               getHealth(),
                               getMaxHealth()));
    canAttack = true;
    rateOfAttack = 1.1f;
  }

  void update(final float delta)
  {
    elapsedSinceLastAttack = elapsedSinceLastAttack + delta;

    if (elapsedSinceLastAttack >= rateOfAttack)
    {
      canAttack = true;
    }
  }

  boolean hasTarget()
  {
    return target != null && !target.dead;
  }

  Projectile findAndFireAtTarget(final List<Enemy> enemies)
  {
    if (!canAttack)
    {
      return null;
    }

    if (!hasTarget() && !enemies.isEmpty())
    {
      float closestDistance = -1.0f;
      Enemy closestEnemy = null;

      for (int i = 0; i < enemies.size(); i++)
      {
        final Enemy enemy = enemies.get(i);

        if (!enemy.dead && range.contains(enemy.getX(), enemy.getY()))
        {
          final float distance = MathUtils.distance(getX(), getY(), enemy.getX(), enemy.getY());

          if (closestDistance < 0.0f)
          {
            closestDistance = distance;
            closestEnemy = enemy;
          }
          else if (distance < closestDistance)
          {
            closestDistance = distance;
            closestEnemy = enemy;
          }
        }
      }

      if (closestEnemy != null)
      {
        target = closestEnemy;
      }
    }

    if (hasTarget())
    {
      elapsedSinceLastAttack = 0.0f;
      canAttack = false;

      final Projectile projectile = new Projectile();
      projectile.setX(getX());
      projectile.setY(getY());
      projectile.moving = true;
      projectile.target = target;
      projectile.targetX = target.getX();
      projectile.targetY = target.getY();
      projectile.setDamage(4, 6);
      return projectile;
    }

    return null;
  }

  void doEnemyAttack(final Enemy enemy)
  {
    decreaseHealth(enemy.baseDamage);
    enemy.dead = true;
    Audio.playSound(Audio.baseDamage);
  }

  void reset()
  {
    setHealth(1000);
    setMaxHealth(1000);
    canAttack = true;
  }
}
