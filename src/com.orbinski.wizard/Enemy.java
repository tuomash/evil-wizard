package com.orbinski.wizard;

class Enemy extends Movable
{
  Enemy()
  {
    super(2.5f, 2.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 8.0f;
    velocityY = 8.0f;
  }
}
