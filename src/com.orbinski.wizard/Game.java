package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game
{
  final Player player;
  final Tower tower;
  final List<Villain> villains;
  final List<Enemy> enemies;
  final List<Projectile> projectiles;

  Villain selectedVillain;

  Game()
  {
    player = new Player();
    tower = new Tower();
    villains = new ArrayList<>();
    enemies = new ArrayList<>();
    projectiles = new ArrayList<>();

    generateHeroes();
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
      final Villain hero = villains.get(i);

      if (!hero.dead)
      {
        hero.update(delta);

        for (int z = 0; z < enemies.size(); z++)
        {
          final Enemy enemy = enemies.get(z);

          if (!enemy.dead && Entity.intersects(hero, enemy))
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

  void generateHeroes()
  {
    final Villain hero = new Villain();
    hero.setX(0.0f);
    hero.setY(0.0f);
    villains.add(hero);
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

      if (!villain.dead && Entity.contains(villain, x, y))
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
    if (selectedVillain != null && !Entity.contains(selectedVillain, x, y))
    {
      selectedVillain.targetX = x;
      selectedVillain.targetY = y;
      selectedVillain.moving = true;
    }
  }
}
