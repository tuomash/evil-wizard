package com.orbinski.wizard;

class Enemy extends Movable
{
  Enemy()
  {
    super(2.5f, 2.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 7.0f;
    velocityY = 7.0f;
  }
}
