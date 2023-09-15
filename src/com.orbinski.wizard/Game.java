package com.orbinski.wizard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game
{
  final Tower tower;
  final List<Villain> villains;
  final List<Enemy> enemies;
  final List<Projectile> projectiles;
  final CameraState cameraState;

  Villain selectedVillain;
  int gold;
  boolean gameOver;

  Game()
  {
    tower = new Tower();
    villains = new ArrayList<>();
    enemies = new ArrayList<>();
    projectiles = new ArrayList<>();
    cameraState = new CameraState();
    gold = 500;

    generateVillains();
    generateEnemies();
  }

  void update(final float delta)
  {
    if (gameOver)
    {
      return;
    }

    tower.update(delta);

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

        if (villain.enemyTarget != null)
        {
          villain.doAttack();

          if (villain.enemyTarget.dead)
          {
            gold = gold + villain.enemyTarget.bounty;
            villain.enemyTarget.villainTarget = null;
            villain.enemyTarget = null;
          }
        }
        else
        {
          for (int z = 0; z < enemies.size(); z++)
          {
            final Enemy enemy = enemies.get(z);

            if (!enemy.dead && Entity.intersects(villain, enemy) && !villain.moving && villain.enemyTarget == null)
            {
              villain.enemyTarget = enemy;
              enemy.villainTarget = villain;
              enemy.moving = false;
              break;
            }
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

        if (enemy.villainTarget != null)
        {
          enemy.doAttack();

          if (enemy.villainTarget.dead)
          {
            enemy.villainTarget.inAction = false;
            enemy.villainTarget.enemyTarget = null;
            enemy.villainTarget = null;
            enemy.moving = true;
          }
        }

        if (Entity.intersects(tower, enemy))
        {
          tower.doEnemyAttack(enemy);

          if (tower.getHealth() <= 0)
          {
            gameOver = true;
          }
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
          projectile.target.doProjectileAttack(projectile);

          if (projectile.target.dead)
          {
            gold = gold + projectile.target.bounty;
          }

          projectile.dead = true;
          projectile.target = null;
        }
      }
    }

    if (cameraState.moving)
    {
      final float x = delta * cameraState.velocityX;
      final float y = delta * cameraState.velocityY;
      Renderer.staticViewport.getCamera().translate(x, y, 0.0f);
      cameraState.reset();
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
    final List<List<Point>> sides = new ArrayList<>();

    {
      final List<Point> leftSide = new ArrayList<>();
      sides.add(leftSide);

      for (int x = 100; x < 120; x++)
      {
        for (int y = 50; y > -50; y--)
        {
          final Point point = new Point();
          point.x = x;
          point.y = y;
          leftSide.add(point);
        }
      }
    }

    {
      final List<Point> rightSide = new ArrayList<>();
      sides.add(rightSide);

      for (int x = -100; x > -120; x--)
      {
        for (int y = 50; y > -50; y--)
        {
          final Point point = new Point();
          point.x = x;
          point.y = y;
          rightSide.add(point);
        }
      }
    }

    {
      final List<Point> topSide = new ArrayList<>();
      sides.add(topSide);

      for (int x = -125; x < 125; x++)
      {
        for (int y = 50; y < 70; y++)
        {
          final Point point = new Point();
          point.x = x;
          point.y = y;
          topSide.add(point);
        }
      }
    }

    {
      final List<Point> bottomSide = new ArrayList<>();
      sides.add(bottomSide);

      for (int x = -125; x < 125; x++)
      {
        for (int y = -50; y > -70; y--)
        {
          final Point point = new Point();
          point.x = x;
          point.y = y;
          bottomSide.add(point);
        }
      }
    }

    final int count = 100;
    final Random random = new Random();

    for (int i = 0; i <= count; i++)
    {
      final Enemy enemy = new Enemy();
      final List<Point> randomSide = sides.get(random.nextInt(sides.size()));
      final Point randomPoint = randomSide.get(random.nextInt(randomSide.size()));
      enemy.setX(randomPoint.x);
      enemy.setY(randomPoint.y);
      enemy.targetX = tower.getX();
      enemy.targetY = tower.getY() - tower.getHeightOffset();
      enemy.moving = true;
      enemies.add(enemy);
    }
  }

  boolean selectTower(final float x, final float y)
  {
    if (Entity.contains(tower, x, y))
    {
      tower.selected = true;
      return true;
    }

    return false;
  }

  boolean selectVillain(final float x, final float y)
  {
    for (int i = 0; i < villains.size(); i++)
    {
      final Villain villain = villains.get(i);

      if (!villain.dead && villain.inAction && Entity.contains(villain, x, y))
      {
        selectedVillain = villain;
        return true;
      }
    }

    // Clear selection
    selectedVillain = null;
    return false;
  }

  public void clearSelections()
  {
    tower.selected = false;
    selectedVillain = null;
  }

  void hotSelectVillain()
  {
    if (selectedVillain == null)
    {
      selectedVillain = villains.get(0);
    }
  }

  void moveVillain(final float x, final float y)
  {
    if (selectedVillain != null && selectedVillain.inAction && !Entity.contains(selectedVillain, x, y))
    {
      selectedVillain.targetX = x;
      selectedVillain.targetY = y;
      selectedVillain.moving = true;

      // Clear combat
      if (selectedVillain.enemyTarget != null)
      {
        selectedVillain.enemyTarget.moving = true;
        selectedVillain.enemyTarget.villainTarget = null;
        selectedVillain.enemyTarget = null;
      }

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

  void centerCamera()
  {
    Renderer.staticViewport.getCamera().position.x = 0.0f;
    Renderer.staticViewport.getCamera().position.y = 0.0f;
    Renderer.staticViewport.getCamera().update();
  }

  void resetVillain(final Villain villain)
  {
    villain.inAction = true;
    villain.setX(-10.0f);
    villain.setY(tower.getY() - tower.getHeightOffset());
  }

  void reset()
  {
    gameOver = false;
    tower.target = null;
    tower.reset();
    projectiles.clear();
    enemies.clear();
    gold = 500;

    villains.get(0).reset();
    generateEnemies();
  }
}
