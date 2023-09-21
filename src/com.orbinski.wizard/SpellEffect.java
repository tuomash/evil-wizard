package com.orbinski.wizard;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

class SpellEffect extends Entity
{
  private final float aliveFor;

  private boolean soundEffectPlayed;
  private float elapsed;

  boolean finished;
  Texture texture;
  Sound soundEffect;

  public SpellEffect(final float width, final float height, final float aliveFor)
  {
    super(width, height);
    this.aliveFor = aliveFor;
  }

  void update(final float delta)
  {
    if (finished)
    {
      return;
    }

    elapsed = elapsed + delta;

    if (soundEffect != null && !soundEffectPlayed)
    {
      soundEffect.play();
      soundEffectPlayed = true;
    }

    if (elapsed >= aliveFor)
    {
      finished = true;
    }
  }
}
