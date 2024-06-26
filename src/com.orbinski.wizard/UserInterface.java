package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;

class UserInterface
{
  static Help help;

  static Icon hotBarIconMinotaur;

  static Icon hotBarIconLightningSpell;
  static Icon hotBarIconGreaseSpell;

  static TextButton nextWaveButton;

  static void create()
  {
    help = new Help();

    hotBarIconMinotaur = new Icon(300, 60, Renderer.uiTrollIconTexture);
    hotBarIconMinotaur.backgroundColor = Color.BLACK;
    hotBarIconMinotaur.setOverlay(new Overlay());

    hotBarIconLightningSpell = new Icon(500, 60, Renderer.uiLightningBoltIconTexture);
    hotBarIconLightningSpell.backgroundColor = Color.BLACK;
    hotBarIconLightningSpell.setOverlay(new Overlay());

    hotBarIconGreaseSpell = new Icon(hotBarIconLightningSpell.getX() + hotBarIconLightningSpell.getWidth() + 10,
                                     hotBarIconLightningSpell.getY(),
                                     Renderer.uiGreaseIconTexture);
    hotBarIconGreaseSpell.backgroundColor = Color.WHITE;
    hotBarIconGreaseSpell.setOverlay(new Overlay());

    nextWaveButton = new TextButton();
    nextWaveButton.textOffsetX = 20;
    nextWaveButton.textOffsetY = 40;
    nextWaveButton.setX(800);
    nextWaveButton.setY(60);
    nextWaveButton.setWidth(160);
    nextWaveButton.setHeight(64);
    nextWaveButton.backgroundColor = Color.BLACK;
    nextWaveButton.text = "NEXT WAVE";
    nextWaveButton.font = UIRenderer.font24White;
    nextWaveButton.visible = true;
  }

  static void layout(final int width, final int height)
  {
    if (help != null)
    {
      int x = 20;
      int y = height - 20;

      for (int i = 0; i < help.texts.size(); i++)
      {
        final Text text = help.texts.get(i);
        text.setX(x);
        text.setY(y);

        y = y - 34;
      }
    }
  }
}
