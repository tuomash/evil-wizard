package com.orbinski.wizard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.orbinski.wizard.Globals.*;

class Game
{
  static final int MAX_JEWELS = 10;

  final Tower tower;
  final Jewel[] jewels;
  final List<Villain> villains;
  final List<Enemy> enemies;
  final List<Projectile> projectiles;
  final List<Spell> spells;
  final CameraState cameraState;

  Spell selectedSpell;
  Villain selectedVillain;
  int gold;
  float elapsedSinceLastJewel;
  float rateOfJewels;
  boolean gameOver;

  Game()
  {
    tower = new Tower();
    jewels = new Jewel[MAX_JEWELS];
    villains = new ArrayList<>();
    enemies = new ArrayList<>();
    spells = new ArrayList<>();
    projectiles = new ArrayList<>();
    cameraState = new CameraState();
    gold = 500;

    generateVillains();
    generateEnemies();
    createSpells();
    randomizeRateOfJewels();
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

    elapsedSinceLastJewel = elapsedSinceLastJewel + delta;

    if (elapsedSinceLastJewel >= rateOfJewels && canAddJewel() != -1)
    {
      final Jewel jewel = new Jewel();
      jewel.setX(MathUtils.random(-55, 55));
      jewel.setY(MathUtils.random(-55, 55));
      addJewel(jewel);
      elapsedSinceLastJewel = 0.0f;
      randomizeRateOfJewels();
    }

    for (int i = 0; i < jewels.length; i++)
    {
      final Jewel jewel = jewels[i];

      if (jewel != null)
      {
        jewel.update(delta);

        if (jewel.hasElapsed())
        {
          jewels[i] = null;
        }
      }
    }

    for (int i = 0; i < villains.size(); i++)
    {
      final Villain villain = villains.get(i);
      villain.update(delta);

      if (!villain.inAction)
      {
        continue;
      }

      for (int z = 0; z < jewels.length; z++)
      {
        final Jewel jewel = jewels[z];

        if (jewel != null && Entity.overlaps(villain, jewel))
        {
          gold = gold + jewel.bounty;
          jewels[z] = null;
        }
      }

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

          if (!enemy.dead && Entity.overlaps(villain, enemy) && !villain.moving && villain.enemyTarget == null)
          {
            villain.enemyTarget = enemy;
            enemy.villainTarget = villain;
            enemy.moving = false;
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

        if (Entity.overlaps(tower, enemy))
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

        if (Entity.overlaps(projectile, projectile.target))
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

    for (int i = 0; i < spells.size(); i++)
    {
      final Spell spell = spells.get(i);
      spell.update(delta);
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

  void loadTextureReferences(final Renderer renderer)
  {
    for (int i = 0; i < spells.size(); i++)
    {
      final Spell spell = spells.get(i);
      spell.loadTextureReference(renderer);
    }
  }

  void createSpells()
  {
    final Spell spell = new LightningBolt();
    spells.add(spell);
  }

  int canAddJewel()
  {
    for (int i = 0; i < jewels.length; i++)
    {
      final Jewel reference = jewels[i];

      if (reference == null)
      {
        return i;
      }
    }

    return -1;
  }

  void addJewel(final Jewel jewel)
  {
    for (int i = 0; i < jewels.length; i++)
    {
      final Jewel reference = jewels[i];

      if (reference == null)
      {
        jewels[i] = jewel;
        break;
      }
    }
  }

  void clearJewels()
  {
    Arrays.fill(jewels, null);
  }

  void randomizeRateOfJewels()
  {
    rateOfJewels = random.nextInt(16) + 5;
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

  boolean selectSpell()
  {
    if (selectedSpell == null)
    {
      selectedSpell = spells.get(0);
      return true;
    }

    return false;
  }

  boolean unselectSpell()
  {
    selectedSpell = null;
    return true;
  }

  void moveSpell(final float x, final float y)
  {
    if (selectedSpell != null)
    {
      selectedSpell.move(x, y);
    }
  }

  void shootSpell()
  {
    if (selectedSpell != null)
    {
      selectedSpell.doAttack(enemies);
    }
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
    selectedSpell = null;
    selectedVillain = null;
  }

  void hotSelectVillain()
  {
    clearSelections();

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

  void placeVillain(final Villain villain)
  {
    villain.inAction = true;
    villain.setX(-10.0f);
    villain.setY(tower.getY() - tower.getHeightOffset());
  }

  void centerCamera()
  {
    Renderer.staticViewport.getCamera().position.x = 0.0f;
    Renderer.staticViewport.getCamera().position.y = 0.0f;
    Renderer.staticViewport.getCamera().update();
  }

  void reset()
  {
    gameOver = false;
    tower.target = null;
    tower.reset();
    clearJewels();
    projectiles.clear();
    enemies.clear();
    gold = 500;
    elapsedSinceLastJewel = 0.0f;

    villains.get(0).reset();
    generateEnemies();
  }
}
