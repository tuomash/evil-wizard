package com.orbinski.wizard;

class CameraState
{
  boolean moving;
  float velocityX;
  float velocityY;

  void moveUp()
  {
    moving = true;
    velocityY = 35.0f;
  }

  void moveLeft()
  {
    moving = true;
    velocityX = -35.0f;
  }

  void moveRight()
  {
    moving = true;
    velocityX = 35.0f;
  }

  void moveDown()
  {
    moving = true;
    velocityY = -35.0f;
  }

  void reset()
  {
    moving = false;
    velocityX = 0.0f;
    velocityY = 0.0f;
  }
}
