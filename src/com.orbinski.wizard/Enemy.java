package com.orbinski.wizard;

class Enemy extends Movable
{
  Enemy()
  {
    super(2.5f, 2.5f);
    setX(-1000.0f);
    setY(-1000.0f);
    velocityX = 2.25f;
    velocityY = 2.25f;
    gold = 2;
  }
}
