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
      text.text = "Use WASD or number keys to control the camera";
      text.font = Renderer.font24White;
      text.setX(20);
      text.setY(160);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Use mouse to select towers, villains and spells";
      text.font = Renderer.font24White;
      text.setX(20);
      text.setY(120);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Use numbers from 1 to 9 to quick select spells";
      text.font = Renderer.font24White;
      text.setX(20);
      text.setY(80);
      texts.add(text);
    }

    {
      final Text text = new Text();
      text.text = "Press H to quick select villain";
      text.font = Renderer.font24White;
      text.setX(20);
      text.setY(40);
      texts.add(text);
    }
  }
}
