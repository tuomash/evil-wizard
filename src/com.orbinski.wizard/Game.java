package com.orbinski.wizard;

class Game
{
  final Player player;

  Game()
  {
    player = new Player();
  }

  void update(float delta)
  {
    player.update(delta);
  }
}
