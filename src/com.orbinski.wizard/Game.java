package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.orbinski.wizard.Globals.random;

class Game
{
  static final int MAX_JEWELS = 10;

  final Base base;
  final Jewel[] jewels;
  final List<Tree> trees;
  final List<Tower> towers;
  final List<ManaFountain> fountains;
  final List<Villain> villains;
  final List<Enemy> enemies;
  final List<Projectile> projectiles;
  final List<Spell> spells;
  final List<AreaEffect> areaEffects;
  final List<SpellEffect> spellEffects;
  final List<TextEffect> textEffects;
  final Waves waves;
  final CameraState cameraState;

  private int speed = 1;
  private int gold = 1000;
  private int mana = 10000;
  private int manaRegen;

  Spell selectedSpell;
  int selectedSpellIndex;
  Villain selectedVillain;
  Tower newTower;
  ManaFountain newFountain;

  int requiredMana = 50000;
  float timeLimitElapsed = 0.0f;
  int minutes = 15;
  int seconds = 0;
  int regenSecondsTotal;
  int regenMinutes;
  int regenSeconds;
  float elapsedSinceLastJewel;
  float rateOfJewels;
  boolean gameOver;
  boolean victory;
  boolean allEnemiesDead = true;
  boolean help;
  boolean paused;
  boolean started;

  Game()
  {
    base = new Base();
    jewels = new Jewel[MAX_JEWELS];
    trees = new ArrayList<>();
    towers = new ArrayList<>();
    fountains = new ArrayList<>();
    villains = new ArrayList<>();
    enemies = new ArrayList<>();
    projectiles = new ArrayList<>();
    spells = new ArrayList<>();
    areaEffects = new ArrayList<>();
    spellEffects = new ArrayList<>();
    textEffects = new ArrayList<>();
    cameraState = new CameraState();
    waves = new Waves(this);

    generateTrees();
    createDefaultManaFountain();
    generateVillains();
    createSpells();
    randomizeRateOfJewels();

    Audio.playPreparation();
  }

  void update(final float delta)
  {
    Audio.update(delta);

    if (gameOver || victory)
    {
      return;
    }
    // Allow camera movement for paused and not started states
    else if (paused || !started)
    {
      updateCameraState(delta);
      return;
    }

    timeLimitElapsed = timeLimitElapsed + delta;

    if (timeLimitElapsed >= 1.0f)
    {
      timeLimitElapsed = 0.0f;

      if (seconds == 0 && minutes >= 1)
      {
        seconds = 59;
        minutes = minutes - 1;
      }
      else if (seconds > 0)
      {
        seconds = seconds - 1;
      }
    }

    final int manaLeft = requiredMana - mana;

    if (manaRegen > 0)
    {
      regenSecondsTotal = manaLeft / manaRegen;
      regenMinutes = regenSecondsTotal / 60;
      regenSeconds = regenSecondsTotal % 60;
    }

    if (mana == requiredMana)
    {
      victory = true;
    }
    else if (minutes == 0 && seconds == 0)
    {
      gameOver = true;
      Audio.fadeOut = true;
    }

    base.update(delta);

    for (int i = 0; i < towers.size(); i++)
    {
      final Tower tower = towers.get(i);
      tower.update(delta, this);
      final Projectile projectile = tower.findAndFireAtTarget(enemies);

      if (projectile != null)
      {
        projectiles.add(projectile);
        Audio.playSound(Audio.orb);
      }
    }

    for (int i = 0; i < fountains.size(); i++)
    {
      final ManaFountain fountain = fountains.get(i);
      fountain.update(delta, this);
    }

    // Only create new jewels when there is an active wave
    /*
    if (!allEnemiesDead)
    {
      elapsedSinceLastJewel = elapsedSinceLastJewel + delta;

      if (elapsedSinceLastJewel >= rateOfJewels && canAddJewel() != -1)
      {
        final Jewel jewel = new Jewel();
        jewel.setX(MathUtils.random(-100, 100));
        jewel.setY(MathUtils.random(-25, 25));
        addJewel(jewel);
        elapsedSinceLastJewel = 0.0f;
        randomizeRateOfJewels();
      }
    }
     */

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

    for (int i = 0; i < areaEffects.size(); i++)
    {
      final AreaEffect effect = areaEffects.get(i);
      effect.update(delta);
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
          textEffects.add(new TextEffect(jewel.getX(), jewel.getY(), 1.9f, jewel.bounty + "G", Renderer.font12Yellow));
          Audio.playSound(Audio.jewelPickup);
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

    boolean allEnemiesDead = true;

    for (int i = 0; i < enemies.size(); i++)
    {
      final Enemy enemy = enemies.get(i);

      if (!enemy.dead)
      {
        allEnemiesDead = false;
        enemy.slowdown = false;

        for (int z = 0; z < areaEffects.size(); z++)
        {
          final AreaEffect effect = areaEffects.get(z);
          effect.apply(enemy);
        }

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

        if (Entity.overlaps(base, enemy))
        {
          enemy.dead = true;
          mana = mana - enemy.manaDamage;

          if (mana < 0)
          {
            mana = 0;
          }

          Audio.playSound(Audio.manaDamage);
        }
      }
    }

    if (allEnemiesDead && !this.allEnemiesDead && !enemies.isEmpty())
    {
      this.allEnemiesDead = true;

      if (waves.isFinished())
      {
        victory = true;
      }
      else
      {
        Audio.fadeOut = true;
        UserInterface.startButton.visible = true;
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
            increaseGold(projectile.target.bounty);
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

    int spellEffectIndexToRemove = -1;

    for (int i = 0; i < spellEffects.size(); i++)
    {
      final SpellEffect effect = spellEffects.get(i);
      effect.update(delta);

      if (effect.finished)
      {
        spellEffectIndexToRemove = i;
      }
    }

    if (spellEffectIndexToRemove != -1)
    {
      spellEffects.remove(spellEffectIndexToRemove);
    }

    int textEffectIndexToRemove = -1;

    for (int i = 0; i < textEffects.size(); i++)
    {
      final TextEffect effect = textEffects.get(i);
      effect.update(delta);

      if (effect.finished)
      {
        textEffectIndexToRemove = i;
      }
    }

    if (textEffectIndexToRemove != -1)
    {
      textEffects.remove(textEffectIndexToRemove);
    }

    updateCameraState(delta);
  }

  private void updateCameraState(final float delta)
  {
    if (cameraState.moving)
    {
      final float x = delta * cameraState.velocityX;
      final float y = delta * cameraState.velocityY;
      Renderer.gameViewportRef.getCamera().translate(x, y, 0.0f);
      cameraState.reset();
    }
  }

  void generateTrees()
  {
    // Top left side of the base
    for (int x = -100; x < -25; x = x + 4)
    {
      for (int y = 100; y > 25; y = y - 4)
      {
        final Tree tree = new Tree();
        tree.setX(x);
        tree.setY(y);
        trees.add(tree);
      }
    }

    // Top right side of the base
    for (int x = 25; x < 125; x = x + 4)
    {
      for (int y = 100; y > 25; y = y - 4)
      {
        final Tree tree = new Tree();
        tree.setX(x);
        tree.setY(y);
        trees.add(tree);
      }
    }

    // Bottom left side of the base
    for (int x = -100; x < -25; x = x + 4)
    {
      for (int y = -25; y > -100; y = y - 4)
      {
        final Tree tree = new Tree();
        tree.setX(x);
        tree.setY(y);
        trees.add(tree);
      }
    }

    // Bottom right side of the base
    for (int x = 25; x < 125; x = x + 4)
    {
      for (int y = -25; y > -100; y = y - 4)
      {
        final Tree tree = new Tree();
        tree.setX(x);
        tree.setY(y);
        trees.add(tree);
      }
    }
  }

  void createDefaultManaFountain()
  {
    final ManaFountain fountain = new ManaFountain(0.0f, -20.0f);
    fountains.add(fountain);
    calculateManaRegen(fountain);
  }

  void generateVillains()
  {
    /*
    final Villain villain = new Villain();
    villain.setX(-10.0f);
    villain.setY(base.getY() - base.getHeightOffset());
    villain.inAction = true;
    villains.add(villain);
    */
  }

  void nextWave()
  {
    if (!paused)
    {
      Audio.stopPreparation();
      Audio.playBattle();
      waves.nextWave();
    }
  }

  void loadTextureReferences()
  {
    for (int i = 0; i < spells.size(); i++)
    {
      final Spell spell = spells.get(i);
      spell.loadTextureReferences();
    }
  }

  void loadUIReferences()
  {
    for (int i = 0; i < villains.size(); i++)
    {
      final Villain villain = villains.get(i);
      villain.loadUIReferences();
    }

    for (int i = 0; i < spells.size(); i++)
    {
      final Spell spell = spells.get(i);
      spell.loadUIReferences();
    }
  }

  void createSpells()
  {
    selectedSpellIndex = -1;

    final Spell lightningBolt = new LightningBolt();
    spells.add(lightningBolt);

    /*
    final Spell grease = new Grease();
    spells.add(grease);
     */
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
    rateOfJewels = random.nextInt(30) + 15;
  }

  boolean selectBase(final float x, final float y)
  {
    if (Entity.contains(base, x, y))
    {
      clearSelections();
      base.selected = true;
      return true;
    }

    return false;
  }

  boolean selectTower(final float x, final float y)
  {
    for (int i = 0; i < towers.size(); i++)
    {
      final Tower tower = towers.get(i);

      if (Entity.contains(tower, x, y))
      {
        clearSelections();
        tower.selected = true;
        return true;
      }
    }

    return false;
  }

  boolean selectSpell(final int index)
  {
    if (selectedSpellIndex != index)
    {
      clearSelections();
      selectedSpell = spells.get(index);
      selectedSpellIndex = index;
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

  void castSpell()
  {
    if (!paused && selectedSpell != null)
    {
      selectedSpell.attack(this);
      decreaseMana(selectedSpell.manaCost);
      clearSelections();
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

  void selectVillain()
  {
    clearSelections();

    if (selectedVillain == null)
    {
      final Villain villain = villains.get(0);

      if (!villain.dead && villain.inAction)
      {
        selectedVillain = villains.get(0);
      }
    }
  }

  void moveVillain(final float x, final float y)
  {
    if (!paused && selectedVillain != null && selectedVillain.inAction && !Entity.contains(selectedVillain, x, y))
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

      Audio.playSound(selectedVillain.moveSound);

      // Clear selection
      selectedVillain = null;
    }
  }

  void pickUpVillain()
  {
    if (!paused && selectedVillain != null && selectedVillain.inAction)
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
    villain.setY(base.getY() - base.getHeightOffset());
  }

  void createNewTower(final float x, final float y)
  {
    if (newTower == null && gold >= Tower.GOLD_COST)
    {
      newTower = new Tower(x, y);
    }
  }

  void moveNewTower(final float x, final float y)
  {
    if (newTower != null)
    {
      newTower.move(x, y);
    }
  }

  void placeNewTower(final float x, final float y)
  {
    if (newTower != null && gold >= Tower.GOLD_COST)
    {
      for (int i = 0; i < towers.size(); i++)
      {
        final Tower existing = towers.get(i);

        if (Entity.overlaps(existing, newTower))
        {
          return;
        }
      }

      newTower.move(x, y);
      decreaseGold(Tower.GOLD_COST);
      towers.add(newTower);
      calculateManaRegen(newTower);
      clearSelections();
    }
  }

  void createNewFountain(final float x, final float y)
  {
    if (newFountain == null && gold >= ManaFountain.GOLD_COST)
    {
      newFountain = new ManaFountain(x, y);
    }
  }

  void moveNewFountain(final float x, final float y)
  {
    if (newFountain != null)
    {
      newFountain.move(x, y);
    }
  }

  void placeNewFountain(final float x, final float y)
  {
    if (newFountain != null && gold >= ManaFountain.GOLD_COST)
    {
      for (int i = 0; i < fountains.size(); i++)
      {
        final ManaFountain existing = fountains.get(i);

        if (Entity.overlaps(existing, newFountain))
        {
          return;
        }
      }

      newFountain.move(x, y);
      decreaseGold(ManaFountain.GOLD_COST);
      fountains.add(newFountain);
      calculateManaRegen(newFountain);
      clearSelections();
    }
  }

  public int getManaRegen()
  {
    return manaRegen;
  }

  private void calculateManaRegen(final ManaFountain fountain)
  {
    manaRegen = manaRegen + fountain.manaRegen;
  }

  private void calculateManaRegen(final Tower tower)
  {
    manaRegen = manaRegen - tower.manaCost;

    if (manaRegen < 0)
    {
      manaRegen = 0;
    }
  }

  void clearSelections()
  {
    base.selected = false;
    selectedSpell = null;
    selectedSpellIndex = -1;
    selectedVillain = null;
    newTower = null;
    newFountain = null;

    for (int i = 0; i < towers.size(); i++)
    {
      towers.get(i).selected = false;
    }

    for (int i = 0; i < fountains.size(); i++)
    {
      fountains.get(i).selected = false;
    }
  }

  void addAreaEffect(final AreaEffect effect)
  {
    areaEffects.add(effect);
  }

  void centerCamera()
  {
    Renderer.gameViewportRef.getCamera().position.x = 0.0f;
    Renderer.gameViewportRef.getCamera().position.y = 0.0f;
    Renderer.gameViewportRef.getCamera().update();
  }

  int getSpeed()
  {
    return speed;
  }

  void increaseSpeed()
  {
    speed++;

    if (speed > 10)
    {
      speed = 10;
    }
  }

  void decreaseSpeed()
  {
    speed--;

    if (speed < 1)
    {
      speed = 1;
    }
  }

  public int getGold()
  {
    return gold;
  }

  void increaseGold(final int gold)
  {
    this.gold = this.gold + gold;
  }

  void decreaseGold(final int gold)
  {
    this.gold = this.gold - gold;

    if (this.gold < 0)
    {
      this.gold = 0;
    }
  }

  int getMana()
  {
    return mana;
  }

  void increaseMana(final int mana)
  {
    this.mana = this.mana + mana;

    if (this.mana > requiredMana)
    {
      this.mana = requiredMana;
    }
  }

  void decreaseMana(final int mana)
  {
    this.mana = this.mana - mana;

    if (this.mana < 0)
    {
      this.mana = 0;
    }
  }

  void reset()
  {
    gameOver = false;
    victory = false;
    started = false;
    base.target = null;
    base.reset();
    clearJewels();
    projectiles.clear();
    enemies.clear();
    gold = 1000;
    mana = 10000;
    elapsedSinceLastJewel = 0.0f;
    timeLimitElapsed = 0.0f;
    minutes = 15;
    seconds = 0;

    // villains.get(0).reset();
    towers.clear();
    fountains.clear();
    manaRegen = 0;
    createDefaultManaFountain();

    waves.reset();
    allEnemiesDead = true;
    UserInterface.startButton.visible = true;
  }
}
