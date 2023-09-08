package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game
{
  final Player player;
  final Tower tower;
  final List<Enemy> enemies;

  Game()
  {
    player = new Player();
    tower = new Tower();
    enemies = new ArrayList<>();

    generateEnemies();
  }

  void generateEnemies()
  {
    final int count = 500;
    final Random random = new Random();
    final List<Float> xCoordinates = new ArrayList<>();
    final List<Float> yCoordinates = new ArrayList<>();

    for (int i = -100; i > -120; i--)
    {
      xCoordinates.add((float) i);
    }

    for (int i = 100; i < 120; i++)
    {
      xCoordinates.add((float) i);
    }

    for (int i = 50; i > -50; i--)
    {
      yCoordinates.add((float) i);
    }

    for (int i = 0; i < count; i++)
    {
      final Enemy enemy = new Enemy();
      enemy.setX(xCoordinates.get(random.nextInt(xCoordinates.size())));
      enemy.setY(yCoordinates.get(random.nextInt(yCoordinates.size())));
      enemy.velocityX = 2.5f;
      enemy.velocityY = 2.5f;
      enemy.targetX = tower.x;
      enemy.targetY = tower.y;
      enemy.moving = true;
      enemies.add(enemy);
    }
  }

  void update(final float delta)
  {
    player.update(delta);
    tower.update(delta);

    for (int i = 0; i < enemies.size(); i++)
    {
      enemies.get(i).update(delta);
    }
  }
}
