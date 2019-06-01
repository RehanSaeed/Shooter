import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Bullet extends Sprite
{
    private static final int DEATH_FRAME_TIME = 1;
    
  private int moveSpeed;        //The speed at which to move.
  private int moveType;         //The type of path to follow.
  private int moveX;            //The X direction in which to move if move type is 4.
  private int moveY;            //The Y direction in which to move if move type is 4.
  private int removeLives;      //The number of lives removed by the bullet.
  private boolean enemyBullet;  //Is the bullet fired from an enemy.
  private int frames;           //The number of frames if the bullet is to be animated.
  private int destroyAfter;     //After this limit is reached the bullet is to be destroyed.
  private int destroyCount;     //Counting up to the destroyAfter limit.
  private boolean destroy;      //Whether or not the bullet should be destroyed.
  private boolean canCollide;   //Whether or not the bullet can colldie with objects.
  private int deathMode;

  /*
   * Initialize the bullet set its positon, speed, path, etc.
   *
   * @param image The image of the bullet.
   * @param X The x position to start at.
   * @param Y The y position to start at.
   * @param moveSpeed The speed at which the bullet will move.
   * @param moveType a number indicating the movement path of the bullet.
   * @param removeLives The number of lives removed if the bullet hits something.
   * @param enemyBullet If true the bullet was fired from an enemy.
   * @param frames The number of frames the bullet image has (MINIMUM 1). The 
   *               bullet will cycle through the frames. The frames must flow 
   *               from left to right.
   * @param destroyAfter A limit after which the bullet is destroyed. If the 
   *                     bullet is not to be destroyed this must be set to -1.
   */
  public Bullet(Image image, int X, int Y, int moveSpeed, int moveType, 
          int removeLives, boolean enemyBullet, int frames, int destroyAfter)
  {
    super(image, image.getWidth()/frames, image.getHeight());

    this.moveSpeed = moveSpeed;
    this.moveType = moveType;
    this.removeLives = removeLives;
    this.enemyBullet = enemyBullet;
    this.frames = frames;
    this.destroyAfter = destroyAfter;
    
    setPosition(X, Y);                  //Set the starting position
    
    canCollide = true;                  //The bullet can collide with other objects.
    destroy = false;                    //The bullet is not going to be destroyed.
    deathMode = 0;
    destroyCount = 0;
    moveX = 0;
    moveY = 0;
  }
  
  /*
   * Set a boolean value of whether or not the bullet should be destroyed.
   *
   * @param destroy A boolean value of whether or not the bullet should be destroyed.
   */
  public void setDestroy(boolean destroy)
  {
      this.destroy = destroy;
  }
  
  /*
   * Get a boolean value of whether or not the bullet can collide with other 
   * objects.
   *
   * @return canCollide A boolean value of whether or not the bullet can collide 
   * with other objects.
   */
  public boolean getCanCollide()
  {
      return canCollide;
  }
  
  /*
   * Set a boolean value of whether or not the bullet can collide with other 
   * objects.
   *
   * @param canCollide A boolean value of whether or not the bullet can collide 
   * with other objects.
   */
  public void setCanCollide(boolean canCollide)
  {
      this.canCollide = canCollide;
  }
  
  /*
   * Set a flag to destroy the bullet and remove it from the screen. Also set 
   * it so it is not bale to collide with other objects.
   */
  public void onDeath()
  {
      canCollide = false;
      destroy = false;
      destroyAfter = DEATH_FRAME_TIME;
      destroyCount = 0;
      setFrame(frames - 1);
  }
  
  public void checkDeath(boolean hasCollided)
  {
    if (deathMode == 0)
    {
        //If the bullet has collided
        if (hasCollided)
        {
            //If the bullet is not timed to be destroyed
            if (!getTimedDestroy())
            {
                onDeath();
            }
        }
        if (getTimedDestroy())
        {
            //If the bullet is timed and the time is up.
            if (destroyCount > destroyAfter)
            {
                ++deathMode;
                onDeath();
            }
        }
    }
    else if (deathMode == 1)
    {
        if (destroyCount > destroyAfter)
        {
            ++deathMode;
        }
    }
    else if (deathMode == 2)
    {
        destroy = true;
    }
  }

  /*
   * Decide whether or not to destroy the bullet and set a flag accordingly.
   *
   * @return Whether or not to destroy the bullet.
   */
  public boolean getDestroy()
  {
      return destroy;
  }
  
  /*
   * Returns a boolean value telling you if an expiry date has been set.
   *
   * @return Return whether or not there is a limit to the bullets existance.
   */
  public boolean getTimedDestroy()
  {
    if (destroyAfter < 1)
    {
        return false;
    }
    else
    {
        return true;
    }
  }
    
  /*
   * Returns the number of lives removed by the bullet.
   *
   * @return The number of lives removed by the bullet.
   */
  public int getRemoveLives()
  {
    return removeLives;
  }
  
  /*
   * Boolean value telling you if the bullet was fired from an enemy or not.
   *
   * @return Whether or not the bullet was fired from an enemy.
   */
  public boolean getEnemyBullet()
  {
      return enemyBullet;
  }
  
  /*
   * Moves the bullet on the given path, increment the countdown to the bullets 
   * death if an expiry date has been set and cycle the frames if there is more 
   * than one frame.
   *
   * @param playerX The X-coordinate of the player.
   * @param playerY The Y-coordinate of the player.
   * @param screenHeight The height of the screen.
   */
  public void moveBullet(int playerX, int playerY, int screenHeight)
  {
    //Dont move.
    if (moveType == 1)
    {}
    //Bullet moves up the screen
    if (moveType == 2)
    {
      move(0, -moveSpeed);
    }
    //Bullet moves down the screen
    if (moveType == 3)
    {
      move(0, moveSpeed);
    }
    //Bullet moves left of the screen
    if (moveType == 4)
    {
      move(-moveSpeed, 0);
    }
    //Bullet moves right of the screen
    if (moveType == 5)
    {
      move(moveSpeed, 0);
    }
    //Bullet moves up and left of the screen
    if (moveType == 6)
    {
      move(-moveSpeed/2, -moveSpeed/2);
    }
    //Bullet moves up and right of the screen
    if (moveType == 7)
    {
      move(moveSpeed/2, -moveSpeed/2);
    }
    //Bullet moves down and left of the screen
    if (moveType == 8)
    {
      move(-moveSpeed/2, moveSpeed/2);
    }
    //Bullet moves down and right of the screen
    if (moveType == 9)
    {
      move(moveSpeed/2, moveSpeed/2);
    }
    //Bullet moves towards the player
    else if (moveType == 10)
    {
      if ( (moveX == 0) && (moveY == 0)) //If the bullet has not moved before.
      {
        int length = (int) Math.sqrt(((playerX - getX())*(playerX - getX())) + ((playerY - getY())*(playerY - getY())));
        move(moveX = ((((playerX - getX())*moveSpeed) / length)), //Move towards the player.
             moveY = ((((playerY - getY())*moveSpeed) / length)));
      }
      else
      {
        move(moveX, moveY); //Move in the initial direction.
      }
    }
    //Bullet follows the player
    else if (moveType == 11)
    {
      if (getY() < (playerY / 2))
      {
        int length = (int) Math.sqrt(((playerX - getX())*(playerX - getX())) + ((playerY - getY())*(playerY - getY())));
        move(moveX = ((((playerX - getX())*moveSpeed) / length)), //Move towards the player.
             moveY = ((((playerY - getY())*moveSpeed) / length)));
      }
      else
      {
        move(moveX, moveY);
      }
    }
    //Bullet moves with the player.
    else if (moveType == 12)
    {
        setPosition(playerX, playerY - (97 * getHeight()/100));
    }
    
    ++destroyCount;                     //Add to the destroyCount.
    if (((frames - 1) > 1) && (canCollide == true))                     //Animate the bullet by changing frames.
    {
        if (getFrame() < (frames - 2))
        {
            setFrame(getFrame() + 1);
        }
        else
        {
            setFrame(0);
        }
    }
  }
}
