package com.orbinski.wizard;

import java.util.ArrayList;
import java.util.List;

class Help
{
  final List<Text> texts;

  Help()
  {
    texts = new ArrayList<>();

    {
      final Text text = new Text();
      text.text = "Press SPACE to pause the game";
      text.font = UIRenderer.font24White;
      text.setX(20);
      text.setY(240);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Press N to decrease game speed and M to increase it";
      text.font = UIRenderer.font24White;
      text.setX(20);
      text.setY(200);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Use WASD or number keys to control the camera";
      text.font = UIRenderer.font24White;
      text.setX(20);
      text.setY(160);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Use mouse to select towers, villains and spells";
      text.font = UIRenderer.font24White;
      text.setX(20);
      text.setY(120);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Press 1 and 2 to quick select spells";
      text.font = UIRenderer.font24White;
      text.setX(20);
      text.setY(80);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Press 3 or H to quick select villain";
      text.font = UIRenderer.font24White;
      text.setX(20);
      text.setY(40);
      texts.add(text);
    }
  }
}
