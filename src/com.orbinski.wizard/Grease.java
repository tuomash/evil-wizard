package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

class Grease extends Spell
{
  Grease()
  {
    super(30.0f, 30.0f);
    range = new Circle(getX(), getY(), 15.0f);
    canAttack = true;
    rateOfAttack = 4.5f;
  }

  @Override
  void update(final float delta)
  {
    super.update(delta);

    if (canAttack)
    {
      UserInterface.greaseSpellIcon.getOverlay().visible = false;
    }
    else
    {
      float percentage = elapsedSinceLastAttack / rateOfAttack;
      percentage = 1.0f - percentage;

      if (percentage < 0.0f)
      {
        percentage = 0.0f;
      }

      UserInterface.greaseSpellIcon.getOverlay().percentage = percentage;
      UserInterface.greaseSpellIcon.getOverlay().visible = true;
    }
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
    texture = renderer.greaseTexture;
  }
}
