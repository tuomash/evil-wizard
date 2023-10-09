package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.List;

class Help
{
  final List<Text> texts;

  Help()
  {
    texts = new ArrayList<>();

    add("Your objective is to get to max mana before the time ends.", 20, 500);
    add("Build towers for constant DPS. Towers reduce mana regen.", 20, 460);
    add("Build mana fountains for increased mana regen.", 20, 420);
    add("Cast spells to inflict massive damage. Spells cost mana.", 20, 380);
    add("Enemies take away mana when they reach your base.", 20, 340);

    add("Press D to delete tower or fountain", 20, 200);
    add("Press SPACE to pause the game", 20, 160);
    add("Press N to decrease game speed and M to increase it", 20, 120);
    add("Use mouse to select base, towers, fountains, and spells", 20, 80);
    add("Use numbers from 1 to 9 to quick select spells", 20, 40);
  }

  private void add(final String text, final int x, final int y)
  {
    final Text textObj = new Text();
    textObj.text = text;
    textObj.font = Renderer.font24White;
    textObj.setX(x);
    textObj.setY(y);
    texts.add(textObj);
  }
}
