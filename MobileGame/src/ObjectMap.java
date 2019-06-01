import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.Vector;

public class ObjectMap
{
  private Vector objectList;
  private int callCount;
  private int[][] map;

  /*
  * Constructor for ObjectMap which initializes the vector of enemies and
  * retrieves the given enemy map.
  *
  * @param mapSetup Decide which map to use.
  */
  public ObjectMap(int mapSetup)
  {
    objectList = new Vector();
    map = getMapSetup(mapSetup);
  }

  /*
  * Get the vector of enemies.
  *
  * @return The vector of enemies.
  */
  public Vector getObjectList()
  {
    return objectList;
  }
  
  /*
  * Increment the call count which counts the number of times it is called.
  */
  private void incrementCallCount()
  {
    ++callCount;
  }

  /*
  * Remove enemies from the vector and layer manager which have exited the
  * screen. Add new enemies if their entry time is released. Move the enemies
  * and fire bullets.
  *
  * @param mYGameCanvas The game canvas
  */
  public void addRemoveObjects(MyGameCanvas myGameCanvas)
  {
    incrementCallCount();

    //REMOVE OBJECTS
    for (int i = (objectList.size() - 1); i != -1; --i)
    {
      Enemy object = (Enemy) objectList.elementAt(i);
      //If the sprite is at the end of the screen
      if (object.getY() >= myGameCanvas.getHeight())
      {
        myGameCanvas.removeSprite(object); //Remove object from layer manager
        objectList.removeElement(object);
      }
    }

    //ADD OBJECTS
    for (int i = 0; i < map.length; ++i)
    {
      if (callCount == map[i][0])
      {
        Image image = getImage(myGameCanvas.getImageStore(), map[i][2]);   //Get the image of the enemy.
        //Work out the x position of the enemy from the 7 standard positions on the x-axis.
        int x = 0;                                                         
        if (map[i][3] == 0)
        {
          x = -image.getHeight();
        }
        else if (map[i][3] == 1)
        {
          x = 0;
        }
        else if (map[i][3] == 2)
        {
          x = (myGameCanvas.getWidth()/4) - (image.getHeight()/3);
        }
        else if (map[i][3] == 3)
        {
          x = (myGameCanvas.getWidth()/2) - (image.getHeight()/2);
        }
        else if (map[i][3] == 4)
        {
          x = ((3 * myGameCanvas.getWidth())/4) - ((2 * image.getHeight())/3);
        }
        else if (map[i][3] == 5)
        {
          x = (myGameCanvas.getWidth() - image.getHeight());
        }
        else if (map[i][3] == 6)
        {
          x = (myGameCanvas.getWidth() + image.getHeight());
        }
        //Work out the y position of the enemy from the 6 standard positions on the y-axis.
        int y = 0;
        if (map[i][4] == 0)
        {
          y = -image.getHeight();
        }
        else if (map[i][4] == 1)
        {
          y = 0;
        }
        else if (map[i][4] == 2)
        {
          y = (myGameCanvas.getHeight()/4) - (image.getHeight()/3);
        }
        else if (map[i][4] == 3)
        {
          y = (myGameCanvas.getHeight()/2) - (image.getHeight()/2);
        }
        else if (map[i][4] == 4)
        {
          y = ((3 * myGameCanvas.getHeight())/4) - ((2 * image.getHeight())/3);
        }
        else if (map[i][4] == 5)
        {
          y = (myGameCanvas.getHeight() - image.getHeight());
        }
        //If an enemy has to be created.
        if (map[i][1] == 1)
        {
            boolean shadow = false;
            if (map[i][20] == 1)
                shadow = true;
            
            Enemy enemy = new Enemy(myGameCanvas, image, "Enemy", map[i][5], 
                    map[i][6], map[i][7], map[i][9], map[i][10], map[i][11], map[i][12],
                    map[i][13], map[i][14], map[i][16], map[i][17], map[i][18], x, y, 
                    map[i][19], map[i][8], map[i][15], false, shadow, map[i][21], map[i][22]);        
            myGameCanvas.insertSprite(enemy);
            objectList.addElement(enemy);
        }
        //If an item has to be created.
        else if (map[i][1] == 2)
        {
            ItemObject item = new ItemObject(myGameCanvas, image, "Item", map[i][5], 
                    map[i][6], map[i][7], x, y, map[i][18], map[i][8], map[i][23]);
            myGameCanvas.insertSprite(item);
            objectList.addElement(item);
        }
      }
    }
    moveEnemies(myGameCanvas);      //Move the enemies and fire bullets.
  }

  /*
  * Move the enemies in the vector on their set paths and fire bullets.
  *
  * @param myGameCanvas The game canvas
  */
  public void moveEnemies(MyGameCanvas myGameCanvas)
  {
    for (int i = 0; i < objectList.size(); ++i)
    {
      Enemy enemy = (Enemy) objectList.elementAt(i);
      enemy.moveOnPath(enemy.getMovePath());            //Move the enemy.
      enemy.fire(myGameCanvas, true);                   //Fire a bullet.
    }
  }

  /*
  * Returns the image with the given image number.
  *
  * @param imageNumber The image to be returned.
  * @return image Return the image.
  */
  private Image getImage(ImageStore imageStore, int imageNumber)
  {
    Image image = null;
    if (imageNumber == 0)
    {
      image = imageStore.getImage("Items");
    }
    else if (imageNumber == 1)
    {
      image = imageStore.getImage("Enemy1");
    }
    else if (imageNumber == 2)
    {
      image = imageStore.getImage("Enemy2");
    }
    else if (imageNumber == 3)
    {
      image = imageStore.getImage("Enemy3");
    }
    else if (imageNumber == 4)
    {
      image = imageStore.getImage("Enemy4");
    }
    return image;
  }
  
  public boolean endOfMap()
  {
      if ((callCount > map[map.length - 1][0]) && (objectList.size() == 0))
      {
          return true;
      }
      else
      {
          return false;
      }
  }

  /*
  * Holds the map of enemies to appear on the screen as well as their vital
  * properties in the following order:
  * 0) Entry Time - The time at which the enemy will appear on the screen.
  * 1) Object Type - The type of object to add to the map.
  *                  1 - Enemy
  *                  2 - Item Object
  *                  3 - Boss
  * 2) Image Type - The image to be displayed for the object.
  *                 0 - Item
  *                 1 - Spaceship
  *                 2 - Skull
  *                 3 - Tank
  *                 4 - Helicopter
  * 3) X Position - The X position of the enemy chosen from 7 standard positions (0 TO 6).
  * 4) Y Position - The Y position of the enemy chosen from 6 standard positions (0 TO 5).
  * 5) Move Speed - The speed at which the enemy will move.
  * 6) Lives - The number of lives the enemy will have.
  * 7) Frames - The number of frames in the object (1 if no animation). Frames must be first images in the image.
  * 8) Move Type - The path the enemy will move on.
  * 9) Bullet Delay - The time between firing a bullet (MINIMUM 19).
  * 10) Bullet Type - The image of the bullet to be used.
  * 11) Bullet Speed - The speed at which the bullet will move. For Move Type 2
  *                   or 3 the speed is lower with ahigher number.
  *                   Suggestion - Set to: 10 for Move Type 1
  *                                        40 for Move Type 2
  *                                        30 for Move Type 3
  * 12) Bullet Move Type - The path in which the bullet will move.
  *                        0 - Dont move.
  *                        2 - Move up the screen.
  *                        3 - Move Down the screen.
  *                        4 - Move left of the screen.
  *                        5 - Move right of the screen.
  *                        6 - Move up and left of the screen.
  *                        7 - Move up and right of the screen.
  *                        8 - Move down and left of the screen.
  *                        9 - Move down and right of the screen.
  *                        10 - Move in the direction of the player.
  *                        11 - Follow the player.
  *                        12 - Move with the player.
  * 13) Bullet Takes Lives - The number of lives taken away by bullet.
  * 14) Bullet Spread - The spread of bullets.
  * 15) Number of Bullets - The number of bullets to be fired.
  * 16) Charge Fire Delay - The delay between firing a charged bullet.
  * 17) UltimateWeapon - The number of ultimate weapons the enemy has.
  * 18) Ultimate Weapon Removes Lives - The number of lives taken by the ultimate weapon.
  * 19) Add score - The score added to the player when the enemy is destroyed.
  * 20) Shadow - Whether or not the enemy will have a shadow.
  * 21) AnimationX - The x position of the enemies animation relative to its x position.
  * 22) AnimationY - The y position of the enemies animation relative to its y position.
   *
  * 23) Item Ability - Only used for Items.
  *
  * @param mapSetup Selects which map of enemies to use.
  * @return map Return the map of enemies.
  */
  private int[][] getMapSetup(int mapSetup)
  {
    if (mapSetup == 1)
    {
      map = new int[][] {
          
          //0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19   20   21   22   23        
          { 70,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          { 80,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          
          { 70,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
          { 80,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
 
          { 70,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          { 80,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          
          { 145, 1,   4,   1,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   2,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 155, 1,   4,   3,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   4,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 145, 1,   4,   5,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 185, 2,   0,   2,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 190, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 195, 2,   0,   4,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 200, 1,   1,   1,   0,   3,   2,   9,   3,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 200, 1,   1,   3,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 250, 1,   1,   5,   0,   3,   2,   9,   4,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 300, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 300, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 350, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON

          { 280, 1,   1,   1,   0,   3,   2,   9,   5,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 300, 1,   1,   5,   0,   3,   2,   9,   6,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 350, 1,   1,   3,   0,   3,   2,   9,   7,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 350, 1,   1,   1,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 400, 1,   1,   3,   0,   3,   2,   9,   8,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 400, 1,   1,   5,   0,   3,   2,   9,   8,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 450, 1,   4,   0,   0,   3,   2,   9,   9,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   6,   0,   3,   2,   9,   10,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   3,   0,   3,   2,   9,   0,   5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 600, 1,   1,   0,   0,   3,   2,   9,   11,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 650, 1,   1,   6,   0,   3,   2,   9,   12,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 700, 2,   0,   2,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 700, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 750, 2,   0,   4,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 800, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 800, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 850, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON
          
          { 900, 1,   4,   2,   0,   3,   2,   9,   14,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 950, 1,   4,   4,   0,   3,   2,   9,   15,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 800, 1,   4,   1,   0,   3,   2,   9,   20,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 850, 1,   4,   5,   0,   3,   2,   9,   21,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 900, 1,   4,   0,   2,   3,   2,   9,   16,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 920, 1,   4,   6,   3,   3,   2,   9,   17,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 940, 1,   4,   0,   2,   3,   2,   9,   16,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 960, 1,   4,   6,   3,   3,   2,   9,   17,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 980, 1,   4,   0,   2,   3,   2,   9,   16,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1000,1,   4,   6,   3,   3,   2,   9,   17,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 1100,1,   1,   0,   5,   3,   2,   9,   18,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1150,1,   1,   6,   5,   3,   2,   9,   19,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},

          { 1200,1,   1,   0,   3,   3,   2,   9,   22,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1250,1,   1,   6,   3,   3,   2,   9,   23,  5,   1,   8,   10,  1,   1,   1,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 1500,1,   4,   0,   1,   3,   10,  9,   24,  10,  1,   8,   11,  1,   1,   -1,  0,   0,   0,   50,  1,   0,   0,   0},
      };
    }
    else if (mapSetup == 2)
    {
      map = new int[][] {
          //0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19   20   21   22   23        
          { 70,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          { 80,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          
          { 70,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
          { 80,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
 
          { 70,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          { 80,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          
          { 145, 1,   4,   1,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   2,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 155, 1,   4,   3,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   4,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 145, 1,   4,   5,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 185, 2,   0,   2,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 190, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 195, 2,   0,   4,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 200, 1,   2,   1,   0,   3,   3,   9,   3,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 200, 1,   2,   3,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 250, 1,   2,   5,   0,   3,   3,   9,   4,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 300, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 300, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 350, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON

          { 280, 1,   2,   1,   0,   3,   3,   9,   5,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 300, 1,   2,   5,   0,   3,   3,   9,   6,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 350, 1,   2,   3,   0,   3,   3,   9,   7,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 350, 1,   2,   1,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 400, 1,   2,   3,   0,   3,   3,   9,   8,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 400, 1,   2,   5,   0,   3,   3,   9,   8,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 450, 1,   4,   0,   0,   3,   3,   9,   9,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   6,   0,   3,   3,   9,   10,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   3,   0,   3,   3,   9,   0,   5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 600, 1,   2,   0,   0,   3,   3,   9,   11,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 650, 1,   2,   6,   0,   3,   3,   9,   12,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 700, 2,   0,   2,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 700, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 750, 2,   0,   4,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 800, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 800, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 850, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON
          
          { 900, 1,   4,   2,   0,   3,   3,   9,   14,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 950, 1,   4,   4,   0,   3,   3,   9,   15,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 800, 1,   4,   1,   0,   3,   3,   9,   20,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 850, 1,   4,   5,   0,   3,   3,   9,   21,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 900, 1,   4,   0,   2,   3,   3,   9,   16,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 920, 1,   4,   6,   3,   3,   3,   9,   17,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 940, 1,   4,   0,   2,   3,   3,   9,   16,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 960, 1,   4,   6,   3,   3,   3,   9,   17,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 980, 1,   4,   0,   2,   3,   3,   9,   16,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1000,1,   4,   6,   3,   3,   3,   9,   17,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 1100,1,   2,   0,   5,   3,   3,   9,   18,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1150,1,   2,   6,   5,   3,   3,   9,   19,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},

          { 1200,1,   2,   0,   3,   3,   3,   9,   22,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1250,1,   2,   6,   3,   3,   3,   9,   23,  5,   1,   8,   10,  1,   1,   2,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 1500,1,   1,   0,   1,   3,   20,  9,   24,  10,  1,   8,   11,  1,   1,   -1,  0,   0,   0,   50,  1,   0,   0,   0},
      };
    }
    else if (mapSetup == 3)
    {
      map = new int[][] {
          //0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19   20   21   22   23        
          { 70,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          { 80,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          
          { 70,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
          { 80,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
 
          { 70,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          { 80,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          
          { 145, 1,   4,   1,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   2,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 155, 1,   4,   3,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   4,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 145, 1,   4,   5,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 185, 2,   0,   2,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 190, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 195, 2,   0,   4,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 200, 1,   3,   1,   0,   3,   5,   9,   3,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 200, 1,   3,   3,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 250, 1,   3,   5,   0,   3,   5,   9,   4,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          
          { 300, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 300, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 350, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON

          { 280, 1,   3,   1,   0,   3,   5,   9,   5,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 300, 1,   3,   5,   0,   3,   5,   9,   6,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},

          { 350, 1,   3,   3,   0,   3,   5,   9,   7,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 350, 1,   3,   1,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 400, 1,   3,   3,   0,   3,   5,   9,   8,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 400, 1,   3,   5,   0,   3,   5,   9,   8,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},

          { 450, 1,   4,   0,   0,   3,   5,   9,   9,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   6,   0,   3,   5,   9,   10,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   3,   0,   3,   5,   9,   0,   5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},

          { 600, 1,   3,   0,   0,   3,   5,   9,   11,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 650, 1,   3,   6,   0,   3,   5,   9,   12,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          
          { 700, 2,   0,   2,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 700, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 750, 2,   0,   4,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 800, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 800, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 850, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON
          
          { 900, 1,   4,   2,   0,   3,   5,   9,   14,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 950, 1,   4,   4,   0,   3,   5,   9,   15,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},

          { 800, 1,   4,   1,   0,   3,   5,   9,   20,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 850, 1,   4,   5,   0,   3,   5,   9,   21,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},

          { 900, 1,   4,   0,   2,   3,   5,   9,   16,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 920, 1,   4,   6,   3,   3,   5,   9,   17,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 940, 1,   4,   0,   2,   3,   5,   9,   16,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 960, 1,   4,   6,   3,   3,   5,   9,   17,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 980, 1,   4,   0,   2,   3,   5,   9,   16,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1000,1,   4,   6,   3,   3,   5,   9,   17,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  1,   0,   0,   0},

          { 1100,1,   3,   0,   5,   3,   5,   9,   18,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 1150,1,   3,   6,   5,   3,   5,   9,   19,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},

          { 1200,1,   3,   0,   3,   3,   5,   9,   22,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          { 1250,1,   3,   6,   3,   3,   5,   9,   23,  5,   1,   8,   10,  1,   1,   3,   0,   0,   0,   50,  0,   0,   0,   0},
          
          { 1500,1,   3,   0,   1,   3,   40,  9,   24,  10,  1,   8,   11,  1,   1,   -1,  0,   0,   0,   50,  1,   0,   0,   0},
      };
    }
    else if (mapSetup == 4)
    {
      map = new int[][] {
          //0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18   19   20   21   22   23        
          { 70,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          { 80,  2,   0,   1,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   4},     //ITEM BLUE
          
          { 70,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
          { 80,  2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   3},     //ITEM MISSILE
 
          { 70,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          { 80,  2,   0,   5,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   5},     //ITEM FIRE
          
          { 145, 1,   4,   1,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   2,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 155, 1,   4,   3,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 150, 1,   4,   4,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 145, 1,   4,   5,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          
          { 185, 2,   0,   2,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 190, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 195, 2,   0,   4,   0,   4,   1,   1,   13,  0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 200, 1,   3,   1,   0,   3,   7,   9,   3,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 200, 1,   3,   3,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 250, 1,   3,   5,   0,   3,   7,   9,   4,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          
          { 300, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 300, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 350, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON

          { 280, 1,   3,   1,   0,   3,   7,   9,   5,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 300, 1,   3,   5,   0,   3,   7,   9,   6,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},

          { 350, 1,   3,   3,   0,   3,   7,   9,   7,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 350, 1,   3,   1,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 400, 1,   3,   3,   0,   3,   7,   9,   8,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 400, 1,   3,   5,   0,   3,   7,   9,   8,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},

          { 450, 1,   4,   0,   0,   3,   7,   9,   9,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   6,   0,   3,   7,   9,   10,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 550, 1,   4,   3,   0,   3,   7,   9,   0,   5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},

          { 600, 1,   3,   0,   0,   3,   7,   9,   11,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 650, 1,   3,   6,   0,   3,   7,   9,   12,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          
          { 700, 2,   0,   2,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 700, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD
          { 750, 2,   0,   4,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   6},     //ITEM BULLET SPREAD

          { 800, 2,   0,   1,   0,   4,   1,   1,   1,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   7},     //ITEM SHIELD
          { 800, 2,   0,   3,   0,   4,   1,   1,   0,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   1},     //ITEM LIFE
          { 850, 2,   0,   5,   0,   4,   1,   1,   2,   0,   1,   0,   0,   0,   0,   0,   0,   0,   0,   50,  0,   0,   0,   2},     //ITEM ULTIMATE WEAPON
          
          { 900, 1,   4,   2,   0,   3,   7,   9,   14,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 950, 1,   4,   4,   0,   3,   7,   9,   15,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},

          { 800, 1,   4,   1,   0,   3,   7,   9,   20,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 850, 1,   4,   5,   0,   3,   7,   9,   21,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},

          { 900, 1,   4,   0,   2,   3,   7,   9,   16,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 920, 1,   4,   6,   3,   3,   7,   9,   17,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 940, 1,   4,   0,   2,   3,   7,   9,   16,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 960, 1,   4,   6,   3,   3,   7,   9,   17,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 980, 1,   4,   0,   2,   3,   7,   9,   16,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},
          { 1000,1,   4,   6,   3,   3,   7,   9,   17,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  1,   0,   0,   0},

          { 1100,1,   3,   0,   5,   3,   7,   9,   18,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 1150,1,   3,   6,   5,   3,   7,   9,   19,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},

          { 1200,1,   3,   0,   3,   3,   7,   9,   22,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          { 1250,1,   3,   6,   3,   3,   7,   9,   23,  5,   1,   8,   10,  1,   1,   4,   0,   0,   0,   50,  0,   0,   0,   0},
          
          { 1500,1,   2,   0,   1,   3,   50,  9,   24,  10,  1,   8,   11,  1,   1,   -1,  0,   0,   0,   50,  1,   0,   0,   0},    
      };
    }
    return map;
  }
}
