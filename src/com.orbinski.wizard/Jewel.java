package com.orbinski.wizard;

class Jewel extends Entity
{
  final float aliveFor;
  final float startWarning;
  final float blinkLength;

  float elapsed;
  float blinkElapsed;

  Jewel()
  {
    super(1.0f, 2.0f);
    bounty = 50;
    aliveFor = 20.0f;
    startWarning = aliveFor * 0.75f;
    blinkLength = 0.4f;
  }

  void update(final float delta)
  {
    elapsed = elapsed + delta;

    if (elapsed >= startWarning)
    {
      blinkElapsed = blinkElapsed + delta;

      if (blinkElapsed >= blinkLength)
      {
        visible = !visible;
        blinkElapsed = 0.0f;
      }
    }
  }

  boolean hasElapsed()
  {
    return elapsed >= aliveFor;
  }
}
