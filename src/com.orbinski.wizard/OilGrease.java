package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

class OilGrease extends Spell
{
  OilGrease()
  {
    super(10.0f, 10.0f);
    range = new Circle(getX(), getY(), 15.0f);
    showRange = true;
    canAttack = true;
    rateOfAttack = 4.5f;
  }

  @Override
  void update(final float delta)
  {
    super.update(delta);
  }

  @Override
  void attack2(final Game game)
  {
    final AreaEffect effect = new AreaEffect(new Circle(range), getWidth(), getHeight());
    game.addAreaEffect(effect);
  }

  @Override
  void loadTextureReference(final Renderer renderer)
  {
  }
}
