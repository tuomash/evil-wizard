package com.orbinski.wizard;

class LightningBolt extends Spell
{
  LightningBolt()
  {
    super();

    applyHeightOffset = false;
  }

  @Override
  void loadTextureReference(final Renderer renderer)
  {
    texture = renderer.lightningBoltTexture;
  }
}
