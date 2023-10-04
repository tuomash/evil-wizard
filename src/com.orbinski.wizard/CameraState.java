package com.orbinski.wizard;

class CameraState
{
  private static final float SPEED = 50.0f;

  boolean moving;
  float velocityX;
  float velocityY;

  void moveUp()
  {
    moving = true;
    velocityY = SPEED;
  }

  void moveLeft()
  {
    moving = true;
    velocityX = -SPEED;
  }

  void moveRight()
  {
    moving = true;
    velocityX = SPEED;
  }

  void moveDown()
  {
    moving = true;
    velocityY = -SPEED;
  }

  void reset()
  {
    moving = false;
    velocityX = 0.0f;
    velocityY = 0.0f;
  }
}
