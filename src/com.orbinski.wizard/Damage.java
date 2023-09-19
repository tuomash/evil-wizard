package com.orbinski.wizard;

import static com.orbinski.wizard.Globals.*;

class Damage
{
  private int min;
  private int max;

  Damage()
  {
    set(1, 5);
  }

  public Damage(final int min, final int max)
  {
    set(min, max);
  }

  private void setMin(final int min)
  {
    if (min > 0 && min <= max)
    {
      this.min = min;
    }
  }

  private void setMax(final int max)
  {
    if (max > 0 && max >= min)
    {
      this.max = max;
    }
  }

  void set(final int min, final int max)
  {
    setMax(max);
    setMin(min);
  }

  int calculate()
  {
    return random.nextInt(max + 1 - min) + min;
  }
}
