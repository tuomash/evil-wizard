package com.orbinski.wizard;

import java.util.List;

class Tower extends Entity
{
  Enemy target;

  public Tower()
  {
    super(8.0f, 30.0f);
    setX(0.0f);
    setY(0.0f);
  }

  void update(final float delta)
  {
  }

  boolean hasTarget()
  {
    return target != null && !target.dead;
  }

  Projectile findAndFireAtTarget(final List<Enemy> enemies)
  {
    if (!hasTarget() && !enemies.isEmpty())
    {
      for (int i = 0; i < enemies.size(); i++)
      {
        final Enemy enemy = enemies.get(i);

        if (!enemy.dead)
        {
          target = enemy;
          final Projectile projectile = new Projectile();
          projectile.setX(getX());
          projectile.setY(getY());
          projectile.moving = true;
          projectile.target = target;
          projectile.targetX = target.getX();
          projectile.targetY = target.getY();
          return projectile;
        }
      }
    }

    return null;
  }
}
