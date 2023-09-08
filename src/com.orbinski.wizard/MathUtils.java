package com.orbinski.wizard;

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
}
