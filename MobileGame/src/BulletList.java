import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.Vector;

public class BulletList
{
  private static final int NO_OF_BULLET_TYPES = 15;
  
  private Vector bulletList;

  /*
  * BulletList Constructor.
  */
  public BulletList()
  {
    bulletList = new Vector();
  }

  /*
  * Get the number of bullet types.
  *
  * @return The number of bullet types.
  */
  public int getNoOfBulletTypes()
  {
    return NO_OF_BULLET_TYPES;
  }
  
  /*
  * Get the vector of bullets.
  *
  * @return The vector of bullets.
  */
  public Vector getBulletList()
  {
    return bulletList;
  }

  /*
  * Add a bullet to the bullet list with the given speed and move type.
  *
  * @param myGameCanvas The game canvas
  * @param bulletType The image to be used for the bullet.
  * @param bulletSpeed The speed at which the bullet should move.
  * @param bulletMoveType The path on which the bullet should move.
  * @param X The x position of the bullet to set as its starting point.
  * @param Y The y position of the bullet to set as its starting point.
  * @param removeLives The number of lives the bullet removes from a player.
  * @param enemyBullet A boolean value telling you if the bullet was fired from 
  *                    an enemy or not.
  * @param frames The number of frames the bullet image has (MINIMUM 1).
  * @param destroyAfter The time after which the bullet should be destroyed. 
  *                     If the bullet is not to be destroyed set this to -1.
  */
  public void addBullet(MyGameCanvas myGameCanvas, int bulletType, 
          int bulletSpeed, int bulletMoveType, int X, int Y, int removeLives, 
          boolean enemyBullet, int frames, int destroyAfter)
  {
      Image image = getBulletImage(myGameCanvas, bulletType);
      Bullet bullet = new Bullet(image, X, Y, bulletSpeed, bulletMoveType, removeLives, enemyBullet, frames, destroyAfter);
      bulletList.addElement(bullet);
      myGameCanvas.insertSprite(bullet);
  }
  
  /*
   * Return a sub-image of a bullet given its number.
   *
   * @param myGameCanvas The game canvas.
   * @param bulletType The number for the image wanted.
   * @return The specified image.
   */
  public Image getBulletImage(MyGameCanvas myGameCanvas, int bulletType)
  { 
      Image bulletImage = null;
      if (bulletType == 1)              //SMALL ENEMY BULLET
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallEnemyBullet");
      }
      else if (bulletType == 2)         //LARGE ENEMY BULLET
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargeEnemyBullet");
      }
      else if (bulletType == 3)         //SMALL PLAYER BULLET
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallPlayerBullet");
      }
      else if (bulletType == 4)         //LARGE PLAYER BULLET
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargePlayerBullet");
      }
      else if (bulletType == 5)         //SMALL MISSILE
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallMissile");
      }
      else if (bulletType == 6)         //LARGE MISSILE
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargeMissile");
      }
      else if (bulletType == 7)         //SMALL PLAYER BULLET LEFT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallPlayerBulletLeft");
      }
      else if (bulletType == 8)         //LARGE PLAYER BULLET LEFT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargePlayerBulletLeft");
      }
      else if (bulletType == 9)         //SMALL MISSILE LEFT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallMissileLeft");
      }
      else if (bulletType == 10)        //LARGE MISSILE LEFT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargeMissileLeft");
      }
      else if (bulletType == 11)        //SMALL PLAYER BULLET RIGHT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallPlayerBulletRight");
      }
      else if (bulletType == 12)        //LARGE PLAYER BULLET RIGHT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargePlayerBulletRight");
      }
      else if (bulletType == 13)        //SMALL MISSILE RIGHT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("SmallMissileRight");
      }
      else if (bulletType == 14)        //LARGE MISSILE RIGHT
      {
          bulletImage = myGameCanvas.getImageStore().getImage("LargeMissileRight");
      }
      else if (bulletType == 15)        //FIRE
      {
          bulletImage = myGameCanvas.getImageStore().getImage("Fire");
      }
      else if (bulletType == 16)        //BEAM
      {
          bulletImage = myGameCanvas.getImageStore().getImage("Beam");
      }
      return bulletImage;
  }

  /*
  * Move the bullets along the screen and remove any bullets which have exited
  * the screen (except if they are to be destroyed).
  *
  * @param myGameCanvas The game canvas
  */
  public void moveBullets(MyGameCanvas myGameCanvas)
  {
    Bullet bullet = null;
    for (int i = (bulletList.size() - 1); i != -1; --i)
    {
        bullet = (Bullet) bulletList.elementAt(i);
        bullet.moveBullet(myGameCanvas.getPlayer().getX(), myGameCanvas.getPlayer().getY(), myGameCanvas.getHeight());

        //Check if a bullet has reached its expiry date and needs to be destroyed.
        bullet.checkDeath(false);



                          
        //If bullet is outside of the screen remove it.
        if (((bullet.getY() < -bullet.getHeight()) || (bullet.getY() > myGameCanvas.getHeight())
            || (bullet.getX() < -bullet.getWidth()) || (bullet.getX() > myGameCanvas.getWidth())) || bullet.getDestroy())
        {
          bulletList.removeElement(bullet);
          myGameCanvas.removeSprite(bullet);
        }
     }
  }


}
