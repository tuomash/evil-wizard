package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;

import java.awt.*;

class UserInterface
{
  static final Rectangle villainIcon = new Rectangle();

  static Icon hotBarIconLightningSpell;
  static Icon hotBarIconGreaseSpell;

  static TextButton nextWaveButton;

  static
  {
    villainIcon.x = 5;
    villainIcon.y = 5;
    villainIcon.width = 50;
    villainIcon.height = 50;
  }

  static void create()
  {
    hotBarIconLightningSpell = new Icon(300, 60, Renderer.uiLightningBoltIconTexture);
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
    nextWaveButton.setX(600);
    nextWaveButton.setY(60);
    nextWaveButton.setWidth(160);
    nextWaveButton.setHeight(64);
    nextWaveButton.backgroundColor = Color.BLACK;
    nextWaveButton.text = "NEXT WAVE";
    nextWaveButton.font = Renderer.font24White;
    nextWaveButton.visible = false;
  }
}
