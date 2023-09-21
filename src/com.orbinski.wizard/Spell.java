package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

abstract class Spell extends Entity
{
  Circle range;
  boolean showRange;
  boolean canAttack;
  float elapsedSinceLastAttack;
  float rateOfAttack;
  Texture texture;

  public Spell(final float width, final float height)
  {
    super(width, height);
    range = new Circle(getX(), getY(), 5.0f);
    setDamage(1, 5);
    showRange = true;
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

  void attack(final Game game)
  {
    if (canAttack)
    {
      attack2(game);
      elapsedSinceLastAttack = 0.0f;
      canAttack = false;
    }
  }

  abstract void attack2(final Game game);

  abstract void loadTextureReference(final Renderer renderer);
}
