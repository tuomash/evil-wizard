package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

class LightningBolt extends Spell
{
  LightningBolt()
  {
    super(8.0f, 30.0f);
    range = new Circle(getX(), getY(), 5.0f);
    setDamage(6, 10);
    canAttack = true;
    rateOfAttack = 2.5f;
    applyHeightOffset = false;
  }

  @Override
  void update(final float delta)
  {
    super.update(delta);

    if (canAttack)
    {
      UserInterface.lightningSpellIcon.getOverlay().visible = false;
    }
    else
    {
      float percentage = elapsedSinceLastAttack / rateOfAttack;
      percentage = 1.0f - percentage;

      if (percentage < 0.0f)
      {
        percentage = 0.0f;
      }

      UserInterface.lightningSpellIcon.getOverlay().percentage = percentage;
      UserInterface.lightningSpellIcon.getOverlay().visible = true;
    }
  }

  @Override
  void attack2(final Game game)
  {
    for (int i = 0; i < game.enemies.size(); i++)
    {
      final Enemy enemy = game.enemies.get(i);

      if (Entity.overlaps(range, enemy))
      {
        enemy.decreaseHealth(getDamage().calculate());
      }
    }

    final SpellEffect spellEffect = new SpellEffect(getWidth(), getHeight(), 0.4f);
    spellEffect.applyHeightOffset = false;
    spellEffect.setX(getX());
    spellEffect.setY(getY());
    spellEffect.texture = Renderer.lightningBoltTexture;
    spellEffect.soundEffect = Audio.lightningBolt;
    game.spellEffects.add(spellEffect);
  }

  @Override
  void loadTextureReference(final Renderer renderer)
  {
    texture = Renderer.lightningBoltTexture;
  }
}
