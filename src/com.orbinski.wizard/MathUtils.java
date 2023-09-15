package com.orbinski.wizard;

import static com.orbinski.wizard.Globals.random;

class MathUtils
{
  static float distance(final float x1,
                        final float y1,
                        final float x2,
                        final float y2)
  {
    final float distX = x2 - x1;
    final float distY = y2 - y1;
    return (float) Math.sqrt(distX * distX + distY * distY);
  }

  static int random(final int low, final int high)
  {
    return random.nextInt(high - low) + low;
  }
}
