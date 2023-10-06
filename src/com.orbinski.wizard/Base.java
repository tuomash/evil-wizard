package com.orbinski.wizard;

import com.badlogic.gdx.math.Circle;

import java.util.List;

class Base extends Entity
{
  Enemy target;
  Circle range;
  boolean selected;

  Base()
  {
    super(8.0f, 30.0f);
    setX(0.0f);
    setY(0.0f);
    range = new Circle(getX(), getY(), 40.0f);
  }

  void update(final float delta)
  {
  }

  void reset()
  {
  }
}
