package com.orbinski.wizard;

class ManaFountain extends Entity
{
  static final int GOLD_COST = 500;

  float elapsedSinceLastManaRegen;
  float rateOfManaRegen;
  int manaRegen;
  boolean selected;
  boolean defaultFountain;

  ManaFountain(final float x, final float y)
  {
    super(4.0f, 4.0f);
    setX(x);
    setY(y);
    rateOfManaRegen = 1.0f;
    manaRegen = 40;
  }

  void update(final float delta, final Game game)
  {
    elapsedSinceLastManaRegen = elapsedSinceLastManaRegen + delta;

    if (elapsedSinceLastManaRegen >= rateOfManaRegen)
    {
      elapsedSinceLastManaRegen = 0.0f;
      game.increaseMana(manaRegen);
    }
  }

  void move(final float x, final float y)
  {
    setX(x);
    setY(y);
  }
}
