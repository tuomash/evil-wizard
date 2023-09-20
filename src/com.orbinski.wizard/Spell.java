package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import java.util.List;

class Spell extends Entity
{
  Circle range;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;
  Texture texture;

  public Spell()
  {
    super(8.0f, 30.0f);
    applyHeightOffset = false;
    range = new Circle(getX(), getY(), 5.0f);
    setDamage(6, 10);
    canAttack = true;
    rateOfAttack = 2.5f;
  }

  void update(final float delta)
  {
    elapsedSinceLastAttack = elapsedSinceLastAttack + delta;

    if (elapsedSinceLastAttack >= rateOfAttack)
    {
      canAttack = true;
    }
  }

  void move(final float x, final float y)
  {
    setX(x);
    setY(y);
    range.x = x;
    range.y = y;
  }

  void doAttack(final List<Enemy> enemies)
  {
    if (canAttack)
    {
      for (int i = 0; i < enemies.size(); i++)
      {
        final Enemy enemy = enemies.get(i);

        if (Entity.overlaps(range, enemy))
        {
          enemy.updateHealth(-getDamage().calculate());
        }
      }

      elapsedSinceLastAttack = 0.0f;
      canAttack = false;
    }
  }
}
