package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

class OilGrease extends Spell
{
  OilGrease()
  {
    super(30.0f, 30.0f);
    range = new Circle(getX(), getY(), 15.0f);
    showRange = false;
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
    effect.setX(getX());
    effect.setY(getY());
    effect.texture = this.texture;
    game.addAreaEffect(effect);
  }

  @Override
  void loadTextureReference(final Renderer renderer)
  {
    texture = renderer.oilGreaseTexture;
  }
}
