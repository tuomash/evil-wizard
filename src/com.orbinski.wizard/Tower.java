package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

import java.util.List;

class Tower extends Entity
{
  Enemy target;
  Circle range;
  boolean selected;

  public Tower()
  {
    super(8.0f, 30.0f);
    setX(0.0f);
    setY(0.0f);
    range = new Circle(getX(), getY(), 40.0f);
    health = 1000;
    maxHealth = 1000;
    setHealthBar(new HealthBar(-getWidthOffset(),
                               getHeightOffset() + 1.0f,
                               getWidth(),
                               1.0f,
                               health,
                               maxHealth));
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

    return null;
  }

  void doEnemyAttack(final Enemy enemy)
  {
    health = health - enemy.towerDamage;
    getHealthBar().updateBar(health, maxHealth);
    enemy.dead = true;
  }
}
