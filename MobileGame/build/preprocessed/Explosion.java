import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Explosion extends Sprite
{
  private int explosionFrame;

  public Explosion(Image image, int X, int Y)
  {
    super(image, image.getHeight(), image.getHeight());
    setFrame(explosionFrame = 0);
    setPosition(X, Y);
  }

  public boolean updateExplosion()
  {
    boolean destroy = false;
    if (explosionFrame < 6)
    {
      setFrame(explosionFrame);
      ++explosionFrame;
    }
    else
    {

      destroy = true;
    }
    return destroy;
  }
}

