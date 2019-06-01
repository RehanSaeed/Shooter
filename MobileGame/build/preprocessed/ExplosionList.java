import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.Vector;

public class ExplosionList
{
  private Vector explosions;
  private int explosionTime;
  private int[] count;

  public ExplosionList()
  {
    explosions = new Vector();
    explosionTime = 0;
  }

  public void addExplosion(MyGameCanvas myGameCanvas, int X, int Y)
  {
    Explosion explosion = new Explosion(myGameCanvas.getImageStore().getImage("SmallExplosion"), X, Y);
    explosions.addElement(explosion);
    myGameCanvas.insertSprite(explosion);
  }
  
  public void addLargeExplosion(MyGameCanvas myGameCanvas, int X, int Y)
  {
    Explosion explosion = new Explosion(myGameCanvas.getImageStore().getImage("LargeExplosion"), X, Y);
    explosions.addElement(explosion);
    myGameCanvas.insertSprite(explosion);
  }

  public void updateExplosions(MyGameCanvas myGameCanvas)
  {
    for (int i = (explosions.size() - 1); i != -1; --i)
    {
      Explosion e = (Explosion) explosions.elementAt(i);
      boolean destroy = e.updateExplosion();
      if (destroy)
      {
        myGameCanvas.removeSprite(e);
        explosions.removeElement(e);
      }
    }
  }

  public boolean addMultipleExplosions(MyGameCanvas myGameCanvas, int explosionLength)
  {
    boolean completed = false;
    //Check players lives
    if (explosionTime == explosionLength)
    {
      completed = true;
    }

    for (int i = 0; (i * 4) < explosionLength; ++i)
    {
      if (explosionTime == (i * 6))
      {
        addExplosion(myGameCanvas, myGameCanvas.getPlayer().getX(),
                     myGameCanvas.getPlayer().getY());
      }
      else if (explosionTime == ((i * 6) + 1))
      {
        addExplosion(myGameCanvas, myGameCanvas.getPlayer().getX() +
                     (myGameCanvas.getPlayer().getWidth() / 3),
                     myGameCanvas.getPlayer().getY());
      }
      else if (explosionTime == ((i * 6) + 2))
      {
        addExplosion(myGameCanvas, myGameCanvas.getPlayer().getX(),
                     myGameCanvas.getPlayer().getY() +
                     (myGameCanvas.getPlayer().getHeight() / 3));
      }
      else if (explosionTime == ((i * 6) + 3))
      {
        addExplosion(myGameCanvas, myGameCanvas.getPlayer().getX() +
                     (myGameCanvas.getPlayer().getWidth() / 3),
                     myGameCanvas.getPlayer().getY() +
                     (myGameCanvas.getPlayer().getHeight() / 3));
      }
    }
    ++explosionTime;
    return completed;
  }
  
  public void addLargeExplosionsAroundScreen(MyGameCanvas myGameCanvas, int explosionLength)
  {
        addLargeExplosion(myGameCanvas, myGameCanvas.getWidth()/10,
                     myGameCanvas.getHeight()/15);
        addLargeExplosion(myGameCanvas, myGameCanvas.getWidth() - (myGameCanvas.getWidth()/3),
                     myGameCanvas.getHeight()/15);
        addLargeExplosion(myGameCanvas, myGameCanvas.getWidth()/10,
                     myGameCanvas.getHeight() - (myGameCanvas.getHeight()/3));
        addLargeExplosion(myGameCanvas, myGameCanvas.getWidth() - (myGameCanvas.getWidth()/3),
                     myGameCanvas.getHeight() - (myGameCanvas.getHeight()/3));
        addLargeExplosion(myGameCanvas, myGameCanvas.getWidth() - (3*myGameCanvas.getWidth()/5),
                     myGameCanvas.getHeight() - (3*myGameCanvas.getHeight()/5));
  }
}
