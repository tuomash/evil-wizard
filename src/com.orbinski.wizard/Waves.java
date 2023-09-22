package com.orbinski.wizard;

import java.awt.*;
import java.util.ArrayList;

import static com.orbinski.wizard.Globals.random;

class Waves
{
  final Game game;
  final java.util.List<Point> leftSide = new ArrayList<>();
  final java.util.List<Point> rightSide = new ArrayList<>();
  final java.util.List<Point> topSide = new ArrayList<>();
  final java.util.List<Point> bottomSide = new ArrayList<>();
  final int maxWaves;

  int waveNumber;

  Waves(final Game game)
  {
    this.game = game;
    waveNumber = 0;
    maxWaves = 10;

    {
      for (int x = -20; x < 20; x++)
      {
        for (int y = 100; y < 150; y++)
        {
          final Point point = new Point();
          point.x = x;
          point.y = y;
          topSide.add(point);
        }
      }
    }

    {
      for (int x = -20; x < 20; x++)
      {
        for (int y = -100; y > -150; y--)
        {
          final Point point = new Point();
          point.x = x;
          point.y = y;
          bottomSide.add(point);
        }
      }

      {
        for (int x = -100; x > -150; x--)
        {
          for (int y = 20; y > -20; y--)
          {
            final Point point = new Point();
            point.x = x;
            point.y = y;
            leftSide.add(point);
          }
        }
      }

      {
        for (int x = 100; x < 150; x++)
        {
          for (int y = 20; y > -20; y--)
          {
            final Point point = new Point();
            point.x = x;
            point.y = y;
            rightSide.add(point);
          }
        }
      }
    }
  }

  void nextWave()
  {
    if (waveNumber == maxWaves || !game.allEnemiesDead)
    {
      return;
    }

    waveNumber++;
    game.allEnemiesDead = false;

    if (waveNumber > maxWaves)
    {
      waveNumber = maxWaves;
    }

    generateEnemies();
  }

  private void generateEnemies()
  {
    game.enemies.clear();

    final int topSideCount;
    final int bottomSideCount;
    final int leftSideCount;
    final int rightSideCount;

    switch (waveNumber)
    {
      case 1:
      {
        topSideCount = 0;
        bottomSideCount = 0;
        leftSideCount = 0;
        rightSideCount = 10;

        break;
      }

      case 2:
      {
        topSideCount = 0;
        bottomSideCount = 0;
        leftSideCount = 10;
        rightSideCount = 10;

        break;
      }

      case 3:
      {
        topSideCount = 10;
        bottomSideCount = 0;
        leftSideCount = 10;
        rightSideCount = 10;

        break;
      }

      case 4:
      {
        topSideCount = 10;
        bottomSideCount = 10;
        leftSideCount = 10;
        rightSideCount = 10;

        break;
      }

      case 5:
      {
        topSideCount = 15;
        bottomSideCount = 15;
        leftSideCount = 15;
        rightSideCount = 15;

        break;
      }

      case 6:
      {
        topSideCount = 20;
        bottomSideCount = 20;
        leftSideCount = 20;
        rightSideCount = 20;

        break;
      }

      case 7:
      {
        topSideCount = 25;
        bottomSideCount = 25;
        leftSideCount = 25;
        rightSideCount = 25;

        break;
      }

      case 8:
      {
        topSideCount = 30;
        bottomSideCount = 30;
        leftSideCount = 30;
        rightSideCount = 30;

        break;
      }

      case 9:
      {
        topSideCount = 40;
        bottomSideCount = 40;
        leftSideCount = 40;
        rightSideCount = 40;

        break;
      }

      case 10:
      {
        topSideCount = 45;
        bottomSideCount = 45;
        leftSideCount = 45;
        rightSideCount = 45;

        break;
      }

      default:
      {
        topSideCount = 5;
        bottomSideCount = 5;
        leftSideCount = 5;
        rightSideCount = 5;

        break;
      }
    }

    generate(topSide, topSideCount);
    generate(bottomSide, bottomSideCount);
    generate(leftSide, leftSideCount);
    generate(rightSide, rightSideCount);
  }

  private void generate(final java.util.List<Point> side, final int count)
  {
    for (int i = 0; i < count; i++)
    {
      final Enemy enemy = new Enemy();
      final Point randomPoint = side.get(random.nextInt(side.size()));
      enemy.setX(randomPoint.x);
      enemy.setY(randomPoint.y);
      enemy.targetX = game.tower.getX();
      enemy.targetY = game.tower.getY() - game.tower.getHeightOffset();
      enemy.moving = true;
      game.enemies.add(enemy);
    }
  }

  void reset()
  {
    waveNumber = 0;
  }
}
