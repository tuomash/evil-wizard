package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game
{
  final List<Entity> entities;
  final Player player;
  final Tower tower;
  final List<Enemy> enemies;

  Game()
  {
    entities = new ArrayList<>();
    player = new Player();
    tower = new Tower();
    entities.add(tower);
    enemies = new ArrayList<>();

    generateEnemies();
  }

  void update(final float delta)
  {
    tower.update(delta);

    for (int i = 0; i < enemies.size(); i++)
    {
      enemies.get(i).update(delta);
    }
  }

  void interpolate(final float alpha)
  {
    for (int i = 0; i < entities.size(); i++)
    {
      entities.get(i).interpolate(alpha);
    }
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
      enemy.targetX = tower.getX();
      enemy.targetY = tower.getY();
      enemy.moving = true;
      addEnemy(enemy);
    }
  }

  void addEnemy(final Enemy enemy)
  {
    entities.add(enemy);
    enemies.add(enemy);
  }
}
