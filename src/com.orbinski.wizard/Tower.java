package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

import java.util.List;

class Tower extends Entity
{
  static final int GOLD_COST = 250;

  Enemy target;
  Circle range;
  boolean selected;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;
  float elapsedSinceLastManaCost;
  float rateOfManaCost;
  int manaCost;

  Tower(final float x, final float y)
  {
    super(6.0f, 10.0f);
    range = new Circle(getX(), getY(), 20.0f);
    setX(x);
    setY(y);
    canAttack = true;
    rateOfAttack = 1.1f;
    rateOfManaCost = 1.0f;
    manaCost = 1;
  }

  void update(final float delta, final Game game)
  {
    elapsedSinceLastAttack = elapsedSinceLastAttack + delta;

    if (elapsedSinceLastAttack >= rateOfAttack)
    {
      canAttack = true;
    }

    elapsedSinceLastManaCost = elapsedSinceLastManaCost + delta;

    if (elapsedSinceLastManaCost >= rateOfManaCost)
    {
      elapsedSinceLastManaCost = 0.0f;
      game.decreaseMana(manaCost);
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
      projectile.setDamage(6, 8);
      return projectile;
    }

    return null;
  }

  void doEnemyAttack(final Enemy enemy)
  {
    decreaseHealth(enemy.manaDamage);
    enemy.dead = true;
    Audio.playSound(Audio.manaDamage);
  }

  void move(final float x, final float y)
  {
    setX(x);
    setY(y);
  }

  @Override
  void setX(final float x)
  {
    super.setX(x);

    if (range != null)
    {
      range.setX(x);
    }
  }

  @Override
  void setY(final float y)
  {
    super.setY(y);

    if (range != null)
    {
      range.setY(y);
    }
  }

  void reset()
  {
    elapsedSinceLastAttack = 0.0f;
    elapsedSinceLastManaCost = 0.0f;
    canAttack = true;
  }
}
