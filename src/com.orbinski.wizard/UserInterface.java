package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;

import java.awt.*;

class UserInterface
{
  static final Rectangle villainIcon = new Rectangle();

  static Icon lightningSpellIcon;
  static Icon greaseSpellIcon;

  static
  {
    villainIcon.x = 5;
    villainIcon.y = 5;
    villainIcon.width = 50;
    villainIcon.height = 50;
  }

  static void create()
  {
    lightningSpellIcon = new Icon(300, 60, Renderer.uiLightningBoltIconTexture);
    lightningSpellIcon.backgroundColor = Color.BLACK;
    lightningSpellIcon.setOverlay(new Overlay());

    greaseSpellIcon = new Icon(lightningSpellIcon.x + lightningSpellIcon.width + 10,
                               lightningSpellIcon.y,
                               Renderer.uiGreaseIconTexture);
    greaseSpellIcon.backgroundColor = Color.WHITE;
    greaseSpellIcon.setOverlay(new Overlay());
  }
}
