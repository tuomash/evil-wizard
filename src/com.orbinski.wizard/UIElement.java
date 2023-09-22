package com.orbinski.wizard;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

abstract class UIElement
{
  private static final Rectangle elementA = new Rectangle();
  private static final Rectangle elementB = new Rectangle();

  static boolean overlaps(final UIElement a, final UIElement b)
  {
    if (a == null || b == null)
    {
      return false;
    }

    elementA.x = a.x;
    elementA.y = a.y;
    elementA.width = a.width;
    elementA.height = a.height;

    elementB.x = b.x;
    elementB.y = b.y;
    elementB.width = b.width;
    elementB.height = b.height;

    return elementA.overlaps(elementB);
  }

  static boolean contains(final UIElement a, final int x, final int y)
  {
    if (a == null)
    {
      return false;
    }

    elementA.x = a.x;
    elementA.y = a.y;
    elementA.width = a.width;
    elementA.height = a.height;

    return elementA.contains(x, y);
  }

  private Overlay overlay;

  private int x;
  private int y;
  private int width;
  private int height;
  boolean visible = true;
  Color backgroundColor;
  Texture texture;

  UIElement()
  {
  }

  UIElement(final int x, final int y, final Texture texture)
  {
    this.x = x;
    this.y = y;
    this.width = texture.getWidth();
    this.height = texture.getHeight();
    this.texture = texture;
  }

  boolean contains(final int x, final int y)
  {
    return contains(this, x, y);
  }

  int getX()
  {
    return x;
  }

  void setX(final int x)
  {
    this.x = x;
  }

  int getY()
  {
    return y;
  }

  void setY(final int y)
  {
    this.y = y;
  }

  int getWidth()
  {
    return width;
  }

  void setWidth(final int width)
  {
    this.width = width;
  }

  int getHeight()
  {
    return height;
  }

  void setHeight(final int height)
  {
    this.height = height;
  }

  Overlay getOverlay()
  {
    return overlay;
  }

  void setOverlay(final Overlay overlay)
  {
    this.overlay = overlay;

    if (overlay != null)
    {
      overlay.setX(x);
      overlay.setY(y);
      overlay.setWidth(width);
      overlay.setHeight(height);
    }
  }
}
