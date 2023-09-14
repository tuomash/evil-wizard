package com.orbinski.wizard;

class Movable extends Entity
{
  float targetX;
  float targetY;
  float velocityX;
  float velocityY;
  boolean moving;
  boolean dead;
  int gold;

  public Movable()
  {
  }

  public Movable(final float width, final float height)
  {
    super(width, height);
  }

  void update(final float delta)
  {
    if (dead)
    {
      return;
    }

    if (moving)
    {
      final float distance = MathUtils.distance(getX(), getY(), targetX, targetY);

      if (distance < 0.5)
      {
        moving = false;
      }

      final float toTargetX = (targetX - getX()) / distance;
      final float toTargetY = (targetY - getY()) / distance;

      setX(getX() + (toTargetX * (velocityX * delta)));
      setY(getY() + (toTargetY * (velocityY * delta)));
    }
  }

  void doProjectileAttack(final Projectile projectile)
  {
    health = health - projectile.damage;
    getHealthBar().updateBar(health, maxHealth);

    if (health <= 0)
    {
      dead = true;
    }
  }
}
