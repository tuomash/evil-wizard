package com.orbinski.wizard;

class Hero extends Movable
{
  public Hero()
  {
    super(4.0f, 4.0f);
    setX(0.0f);
    setY(0.0f);
    velocityX = 16.0f;
    velocityY = 16.0f;
  }
}
