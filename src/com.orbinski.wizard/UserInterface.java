package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;

class UserInterface
{
  static Help help;

  static Icon hotBarIconMinotaur;

  static Icon hotBarIconLightningSpell;
  static Icon hotBarIconGreaseSpell;

  static TextButton startButton;

  static void create()
  {
    help = new Help();

    hotBarIconMinotaur = new Icon(300, 60, Renderer.uiMinotaurIconTexture);
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

    startButton = new TextButton();
    startButton.textOffsetX = 20;
    startButton.textOffsetY = 40;
    startButton.setX(800);
    startButton.setY(60);
    startButton.setWidth(160);
    startButton.setHeight(64);
    startButton.backgroundColor = Color.BLACK;
    startButton.text = "START";
    startButton.font = Renderer.font24White;
    startButton.visible = true;
  }
}
