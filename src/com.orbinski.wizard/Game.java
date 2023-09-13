package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game
{
  final Tower tower;
  final List<Villain> villains;
  final List<Enemy> enemies;
  final List<Projectile> projectiles;

  Villain selectedVillain;

  Game()
  {
    tower = new Tower();
    villains = new ArrayList<>();
    enemies = new ArrayList<>();
    projectiles = new ArrayList<>();

    generateVillains();
    generateEnemies();
  }

  void update(final float delta)
  {
    tower.update(delta);

    if (!tower.hasTarget())
    {
      final Projectile projectile = tower.findAndFireAtTarget(enemies);

      if (projectile != null)
      {
        projectiles.add(projectile);
      }
    }

    for (int i = 0; i < villains.size(); i++)
    {
      final Villain villain = villains.get(i);

      if (!villain.dead && villain.inAction)
      {
        villain.update(delta);

        for (int z = 0; z < enemies.size(); z++)
        {
          final Enemy enemy = enemies.get(z);

          if (!enemy.dead && Entity.intersects(villain, enemy))
          {
            enemy.dead = true;
            break;
          }
        }
      }
    }

    for (int i = 0; i < enemies.size(); i++)
    {
      final Enemy enemy = enemies.get(i);

      if (!enemy.dead)
      {
        enemy.update(delta);

        if (Entity.intersects(tower, enemy))
        {
          enemy.dead = true;
        }
      }
    }

    for (int i = 0; i < projectiles.size(); i++)
    {
      final Projectile projectile = projectiles.get(i);

      if (!projectile.dead)
      {
        projectile.update(delta);

        if (Entity.intersects(projectile, projectile.target))
        {
          projectile.dead = true;
          projectile.target.dead = true;
        }
      }
    }
  }

  void generateVillains()
  {
    final Villain villain = new Villain();
    villain.setX(-10.0f);
    villain.setY(tower.getY() - tower.getHeightOffset());
    villain.inAction = true;
    villains.add(villain);
  }

  void generateEnemies()
  {
    final int count = 10;
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
      enemy.targetX = tower.getX();
      enemy.targetY = tower.getY() - tower.getHeightOffset();
      enemy.moving = true;
      enemies.add(enemy);
    }
  }

  void selectVillain(final float x, final float y)
  {
    for (int i = 0; i < villains.size(); i++)
    {
      final Villain villain = villains.get(i);

      if (!villain.dead && villain.inAction && Entity.contains(villain, x, y))
      {
        selectedVillain = villain;
        return;
      }
    }

    // Clear selection
    selectedVillain = null;
  }

  void moveVillain(final float x, final float y)
  {
    if (selectedVillain != null && selectedVillain.inAction && !Entity.contains(selectedVillain, x, y))
    {
      selectedVillain.targetX = x;
      selectedVillain.targetY = y;
      selectedVillain.moving = true;

      // Clear selection
      selectedVillain = null;
    }
  }

  void pickUpVillain()
  {
    if (selectedVillain != null && selectedVillain.inAction)
    {
      selectedVillain.inAction = false;

      // Clear selection
      selectedVillain = null;
    }
  }

  void resetVillain(final Villain villain)
  {
    villain.inAction = true;
    villain.setX(0.0f);
    villain.setY(0.0f);
  }

  void reset()
  {
    tower.target = null;
    projectiles.clear();
    enemies.clear();

    generateEnemies();
  }
}
