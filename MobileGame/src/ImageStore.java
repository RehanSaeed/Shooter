import javax.microedition.lcdui.*;

/**
 * <p>Title: MobileGame</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Muhammad Rehan Saeed
 * @version 1.0
 */
public class ImageStore
{
    private Image foreground;
    private Image items;
    private Image shield;
    private Image enemy1;
    private Image enemy2;
    private Image enemy3;
    private Image enemy4;
    private Image playersHelper;
    private Image missile;
    private Image combinedImage;

    public ImageStore()
    {
        init();
    }
    
  public void init()
  {
    try
    {
        foreground = Image.createImage("/res/Foreground.png");
        enemy1 = Image.createImage("/res/Enemy1.png");
        items = Image.createImage("/res/Items.png");
        shield = Image.createImage("/res/Shield.png");
        enemy2 = Image.createImage("/res/Enemy2.png");
        enemy3 = Image.createImage("/res/Enemy3.png");
        enemy4 = Image.createImage("/res/Enemy4.png");
        playersHelper = Image.createImage("/res/PlayersHelper.png");
        missile = Image.createImage("/res/Missile.png");
        combinedImage = Image.createImage("/res/CombinedImage.png");
    }
    catch (Exception e)
    {
      System.out.println("getImage Exception::" + e);
      System.out.println("foreground " + foreground);
      System.out.println("enemy1 " + enemy1);
      System.out.println("items " + items);
      System.out.println("shield " + shield);
      System.out.println("enemy2 " + enemy2);
      System.out.println("enemy3 " + enemy3);
      System.out.println("enemy4 " + enemy4);
      System.out.println("playersHelper " + playersHelper);
      System.out.println("missile " + missile);
      System.out.println("combinedImage " + combinedImage);
      init();
    }
  }

  public Image getImage(String imageName)
  {
    Image image = null;
    try
    {
        if (imageName.equals("Foreground"))
        {
            image = foreground;
        }
        else if (imageName.equals("Text"))
        {
            image = combinedImage.createImage(combinedImage, 224, 256, 150, 60, 0);
        }
        else if (imageName.equals("SmallExplosion"))
        {
            image = combinedImage.createImage(combinedImage, 224, 224, 192, 32, 0);
        }
        else if (imageName.equals("Ship"))
        {
            image = combinedImage.createImage(combinedImage, 96, 384, 320, 32, 0);
        }
        else if (imageName.equals("Enemy1"))
        {
            image = enemy1;
        }
        else if (imageName.equals("SnowBackground"))
        {
            image = combinedImage.createImage(combinedImage, 240, 0, 120, 160, 0);
        }
        else if (imageName.equals("IslandBackground"))
        {
            image = combinedImage.createImage(combinedImage, 120, 0, 120, 160, 0);
        }
        else if (imageName.equals("DesertBackground"))
        {
            image = combinedImage.createImage(combinedImage, 360, 0, 120, 160, 0);
        }
        else if (imageName.equals("GrassBackground"))
        {
            image = combinedImage.createImage(combinedImage, 0, 0, 120, 160, 0);
        }
        else if (imageName.equals("Items"))
        {
            image = items;
        }
        else if (imageName.equals("Shield"))
        {
            image = shield;
        }
        else if (imageName.equals("LargeMissile"))
        {
            image = combinedImage.createImage(combinedImage, 96, 352, 64, 32, 0);
        }
        else if (imageName.equals("LargeMissileLeft"))
        {
            image = combinedImage.createImage(combinedImage, 160, 352, 64, 32, 0);
        }
        else if (imageName.equals("LargeMissileRight"))
        {
            image = combinedImage.createImage(combinedImage, 224, 352, 64, 32, 0);
        }
        else if (imageName.equals("LargeEnemyBullet"))
        {
            image = combinedImage.createImage(combinedImage, 384, 256, 96, 32, 0);
        }
        else if (imageName.equals("Fire"))
        {
            image = combinedImage.createImage(combinedImage, 96, 224, 128, 96, 0);
        }
        else if (imageName.equals("Beam"))
        {
            image = combinedImage.createImage(combinedImage, 0, 160, 96, 288, 0);
        }
        else if (imageName.equals("SmallPlayerBullet"))
        {
            image = combinedImage.createImage(combinedImage, 384, 288, 32, 16, 0);
        }
        else if (imageName.equals("SmallPlayerBulletLeft"))
        {
            image = combinedImage.createImage(combinedImage, 416, 288, 32, 16, 0);
        }
        else if (imageName.equals("SmallPlayerBulletRight"))
        {
            image = combinedImage.createImage(combinedImage, 448, 288, 32, 16, 0);
        }
        else if (imageName.equals("SmallMissile"))
        {
            image = combinedImage.createImage(combinedImage, 384, 304, 32, 16, 0);
        }
        else if (imageName.equals("SmallMissileLeft"))
        {
            image = combinedImage.createImage(combinedImage, 416, 304, 32, 16, 0);
        }
        else if (imageName.equals("SmallMissileRight"))
        {
            image = combinedImage.createImage(combinedImage, 448, 304, 32, 16, 0);
        }
        else if (imageName.equals("SmallEnemyBullet"))
        {
            image = combinedImage.createImage(combinedImage, 416, 224, 48, 16, 0);
        }
        else if (imageName.equals("LargePlayerBullet"))
        {
            image = combinedImage.createImage(combinedImage, 96, 320, 64, 32, 0);
        }
        else if (imageName.equals("LargePlayerBulletLeft"))
        {
            image = combinedImage.createImage(combinedImage, 160, 320, 64, 32, 0);
        }
        else if (imageName.equals("LargePlayerBulletRight"))
        {
            image = combinedImage.createImage(combinedImage, 224, 320, 64, 32, 0);
        }
        else if (imageName.equals("LargeExplosion"))
        {
            image = combinedImage.createImage(combinedImage, 96, 160, 384, 64, 0);
        }
        else if (imageName.equals("Enemy2"))
        {
            image = enemy2;
        }
        else if (imageName.equals("PlayersHelper"))
        {
            image = playersHelper;
        }
        else if (imageName.equals("Missile"))
        {
            image = missile;
        }
        else if (imageName.equals("Enemy3"))
        {
            image = enemy3;
        }
        else if (imageName.equals("Enemy4"))
        {
            image = enemy4;
        }
    }
    catch (Exception e)
    {
      System.out.println("getImage Exception::" + e);
      System.out.println("Image: " + image);
      System.out.println("ImageName: " + imageName);
    }
    return image;
  }
}
