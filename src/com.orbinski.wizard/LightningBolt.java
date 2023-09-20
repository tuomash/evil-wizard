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
  }

  @Override
  void attack2(final Game game)
  {
    for (int i = 0; i < game.enemies.size(); i++)
    {
      final Enemy enemy = game.enemies.get(i);

      if (Entity.overlaps(range, enemy))
      {
        enemy.updateHealth(-getDamage().calculate());
      }
    }
  }

  @Override
  void loadTextureReference(final Renderer renderer)
  {
    texture = renderer.lightningBoltTexture;
  }
}
