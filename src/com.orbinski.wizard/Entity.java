package com.orbinski.wizard;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

abstract class Entity
{
  private static final Rectangle entityA = new Rectangle();
  private static final Rectangle entityB = new Rectangle();

  static boolean overlaps(final Entity a, final Entity b)
  {
    if (a == null || b == null)
    {
      return false;
    }

    entityA.x = a.bottomLeftCornerX;
    entityA.y = a.bottomLeftCornerY;
    entityA.width = a.width;
    entityA.height = a.height;

    entityB.x = b.bottomLeftCornerX;
    entityB.y = b.bottomLeftCornerY;
    entityB.width = b.width;
    entityB.height = b.height;

    return entityA.overlaps(entityB);
  }

  static boolean contains(final Entity a, final float x, final float y)
  {
    if (a == null)
    {
      return false;
    }

    entityA.x = a.bottomLeftCornerX;
    entityA.y = a.bottomLeftCornerY;
    entityA.width = a.width;
    entityA.height = a.height;

    return entityA.contains(x, y);
  }

  static boolean overlaps(final Circle circle, final Entity a)
  {
    if (circle == null || a == null)
    {
      return false;
    }

    entityA.x = a.bottomLeftCornerX;
    entityA.y = a.bottomLeftCornerY;
    entityA.width = a.width;
    entityA.height = a.height;

    return Intersector.overlaps(circle, entityA);
  }

  private float x;
  private float y;
  private float prevX;
  private float prevY;
  private float bottomLeftCornerX;
  private float bottomLeftCornerY;
  private float width;
  private float widthOffset;
  private float height;
  private float heightOffset;
  private int health;
  private int maxHealth;
  private HealthBar healthBar;
  private Damage damage;

  boolean applyWidthOffset = true;
  boolean applyHeightOffset = true;
  float targetX;
  float targetY;
  float velocityX;
  float velocityY;
  boolean showBorder = false;
  boolean visible = true;
  boolean moving;
  boolean dead;
  boolean slowdown;
  int bounty;
  Sound moveSound;
  Sound deathSound;

  Entity()
  {
    setWidth(2.5f);
    setHeight(2.5f);
  }

  Entity(final float width, final float height)
  {
    setWidth(width);
    setHeight(height);
  }

  Entity(final float x, final float y, final float width, final float height)
  {
    setX(x);
    setY(y);
    setWidth(width);
    setHeight(height);
  }

  void update(final float delta)
  {
    if (dead)
    {
      return;
    }

    if (moving)
    {
      final float distanceToTarget = MathUtils.distance(getX(), getY(), targetX, targetY);

      if (distanceToTarget < 0.5)
      {
        moving = false;
      }

      final float toTargetX = (targetX - getX()) / distanceToTarget;
      final float toTargetY = (targetY - getY()) / distanceToTarget;

      float velocityX = this.velocityX;
      float velocityY = this.velocityY;

      if (slowdown)
      {
        velocityX = velocityX * 0.5f;
        velocityY = velocityY * 0.5f;
      }

      final float distanceX = velocityX * delta;
      final float distanceY = velocityY * delta;

      setX(getX() + (toTargetX * distanceX));
      setY(getY() + (toTargetY * distanceY));
    }
  }

  void doProjectileAttack(final Projectile projectile)
  {
    updateHealth(-projectile.getDamage().calculate());
  }

  float getX()
  {
    return x;
  }

  void setX(final float x)
  {
    prevX = this.x;
    this.x = x;

    if (applyWidthOffset)
    {
      bottomLeftCornerX = x - widthOffset;
    }
    else
    {
      bottomLeftCornerX = x;
    }

    if (healthBar != null)
    {
      healthBar.setX(getX() + healthBar.getOffsetX());
    }
  }

  float getY()
  {
    return y;
  }

  void setY(final float y)
  {
    prevY = this.y;
    this.y = y;

    if (applyHeightOffset)
    {
      bottomLeftCornerY = y - heightOffset;
    }
    else
    {
      bottomLeftCornerY = y;
    }

    if (healthBar != null)
    {
      healthBar.setY(getY() + healthBar.getOffsetY());
    }
  }

  float getPrevX()
  {
    return prevX;
  }

  float getPrevY()
  {
    return prevY;
  }

  float getBottomLeftCornerX()
  {
    return bottomLeftCornerX;
  }

  float getBottomLeftCornerY()
  {
    return bottomLeftCornerY;
  }

  float getWidth()
  {
    return width;
  }

  void setWidth(final float width)
  {
    this.width = width;
    widthOffset = width / 2.0f;
  }

  float getWidthOffset()
  {
    return widthOffset;
  }

  float getHeight()
  {
    return height;
  }

  void setHeight(final float height)
  {
    this.height = height;
    heightOffset = height / 2.0f;
  }

  float getHeightOffset()
  {
    return heightOffset;
  }

  public int getHealth()
  {
    return health;
  }

  public void setHealth(final int health)
  {
    this.health = health;

    if (healthBar != null)
    {
      healthBar.updateBar(health, maxHealth);
    }
  }

  void increaseHealth(final int amount)
  {
    updateHealth(amount);
  }

  void decreaseHealth(final int amount)
  {
    updateHealth(-amount);
  }

  private void updateHealth(final int amount)
  {
    health = health + amount;

    if (healthBar != null)
    {
      healthBar.updateBar(health, maxHealth);
    }

    if (health <= 0)
    {
      dead = true;
      Audio.playSound(deathSound);
    }
  }

  int getMaxHealth()
  {
    return maxHealth;
  }

  void setMaxHealth(final int maxHealth)
  {
    this.maxHealth = maxHealth;

    if (healthBar != null)
    {
      healthBar.updateBar(health, maxHealth);
    }
  }

  HealthBar getHealthBar()
  {
    return healthBar;
  }

  void setHealthBar(final HealthBar healthBar)
  {
    this.healthBar = healthBar;
    healthBar.setX(getX() + healthBar.getOffsetX());
    healthBar.setY(getY() + healthBar.getOffsetY());
  }

  public Damage getDamage()
  {
    return damage;
  }

  void setDamage(final int min, final int max)
  {
    if (damage == null)
    {
      damage = new Damage(min, max);
    }
    else
    {
      damage.set(min, max);
    }
  }
}
