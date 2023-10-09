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

  boolean started;
  float totalElapsed;
  float elapsedWave;

  Waves(final Game game)
  {
    this.game = game;

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
          for (int y = 25; y > -25; y--)
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

  void update(final float delta)
  {
    if (!started)
    {
      return;
    }

    totalElapsed = totalElapsed + delta;
    elapsedWave = elapsedWave + delta;

    if (elapsedWave > 1.0f)
    {
      elapsedWave = 0.0f;

      if (totalElapsed < 30.0f)
      {
        generate(leftSide, 2);
      }
      else if (totalElapsed > 70.0f && totalElapsed < 100.0f)
      {
        generate(leftSide, 4);
      }
      else if (totalElapsed > 115.0f && totalElapsed < 140.0f)
      {
        generate(leftSide, 8);
      }
      else if (totalElapsed > 155.0f && totalElapsed < 185.0f)
      {
        generate(leftSide, 10);
      }
      else if (totalElapsed > 200.0f && totalElapsed < 230.0f)
      {
        generate(leftSide, 12);
      }
    }
  }

  void start()
  {
    if (started)
    {
      return;
    }

    started = true;
    UserInterface.startButton.visible = false;
  }

  private void generate(final java.util.List<Point> side, final int count)
  {
    for (int i = 0; i < count; i++)
    {
      final Enemy enemy = new Enemy();
      final Point randomPoint = side.get(random.nextInt(side.size()));
      enemy.setX(randomPoint.x);
      enemy.setY(randomPoint.y);
      enemy.targetX = game.base.getX();
      enemy.targetY = game.base.getY() - game.base.getHeightOffset();
      enemy.moving = true;
      game.enemies.add(enemy);
    }
  }

  void reset()
  {
    started = false;
    totalElapsed = 0.0f;
    elapsedWave = 0.0f;
  }
}
