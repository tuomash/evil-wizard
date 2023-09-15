package com.orbinski.wizard;

class Jewel extends Entity
{
  int bounty;
  float elapsed;
  float aliveFor;

  Jewel()
  {
    super(1.0f, 2.0f);
    bounty = 50;
    aliveFor = 20.0f;
  }

  void update(final float delta)
  {
    elapsed = elapsed + delta;
  }

  boolean hasElapsed()
  {
    return elapsed >= aliveFor;
  }
}
