import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Enemy extends Player
{
  //ENEMY ATTRIBUTES
  private int addToScore;           //The number added to the players score if it is killed by the player.
  private int movePath;             //Number indicating the path the enemy moves on.
  private boolean reachedPoint;     //Used to determine if an enemy has reached a specific point on its path.
  
  //ENEMY BULLET ATTRIBUTES
  private int bulletMoveType;       //The path the bullet will move on.
  private int noOfBullets;          //The number of bullets the player is allowed to fire (Set to -1 if infinite).
  private int bulletCount;          //The number of bullets fired by the enemy.

  /*
   * Initialize the enem with the given parameters, set its starting position 
   * and start counting the bullets fired from zero.
   *
   * @param myGameCanvas The game canvas.
   * @param image The image of the player.
   * @param name The name of the player.
   * @param moveSpeed The speed at which the player can move.
   * @param lives The number of lives the player has.
   * @param frames The number of frames in the animation of the player (1 if there is no animation).
   * @param bulletDelay The delay between being able to fire a bullet.
   * @param bulletType The image of the bullet to be fired.
   * @param bulletSpeed The speed at which the bullet can move.
   * @param bulletMoveType The path on which the bullet will move.
   * @param bulletTakesLives The number of lives the bullet removes.
   * @param bulletSpread The spread of bullets.
   * @param chargeFireDelay The delay between being able to fire the 
   *                        charged beam.
   * @param ultimateWeapon The number of ultimate weapons held by the 
   *                       player.
   * @param ultimateWeaponRemovesLives The number of lives removed by the 
   *                                   ultimate weapon.
   * @param currentX The starting x position.
   * @param currentY The starting y position.
   * @param addToScore The ammount added to the players score on the enemies death.
   * @param movePath The path on which the enemy moves on.
   * @param noOfBullets The number of bullets the enemy is allowed to fire.
   * @param shield Whether or not the enemy has a shield.
   */
  public Enemy(MyGameCanvas myGameCanvas, Image image, String name, int moveSpeed, 
          int lives, int frames, int bulletDelay, int bulletType, int bulletSpeed, 
          int bulletMoveType, int bulletTakesLives, int bulletSpread, 
          int chargeFireDelay, int ultimateWeapon, int ultimateWeaponRemovesLives, 
          int currentX, int currentY, int addToScore, int movePath, 
          int noOfBullets, boolean shield, boolean setShadow, int animationX, int animationY)
  {
    super(myGameCanvas, image, name, moveSpeed, lives, frames, bulletDelay,
          bulletType, bulletSpeed, bulletTakesLives, bulletSpread,
          chargeFireDelay, ultimateWeapon, ultimateWeaponRemovesLives, shield, setShadow, 6, 7, animationX, animationY);
    
    setPosition(currentX, currentY);        //Sets the given starting position for the enemy.
    
    this.movePath = movePath;
    this.addToScore = addToScore;
    this.noOfBullets = noOfBullets;
    this.bulletMoveType = bulletMoveType;
    bulletCount = 0;        //Start counting the number of bullets from zero.
  }

  /*
  * Get the score to be added to the player.
  *
  * @return addToScore The score to be added to the player.
  */
  public int getAddToScore()
  {
     return addToScore;
  }
  
  /*
  * Return the movement path of the enemy.
  *
  * @return movePath The movement path of the enemy.
  */
  public int getMovePath()
  {
     return movePath;
  }
  
  /*
  * Return the number of bullets the enemy is allowed to fire.
  *
  * @return noOfBullets The number of bullets the enemy is allowed to fire.
  */
  public int getNoOfBullets()
  {
     return noOfBullets;
  }
  
  /*
   * Return the bullet movement type.
   *
   * @return The bullet movement type.
   */
  public int getBulletMoveType()
  {
    return bulletMoveType;
  }
  
  /*
  * Set the number of bullets the enemy is allowed to fire.
  *
  * @param noOfBullets The number of bullets the enemy is allowed to fire.
  */
  public void setNoOfBullets(int noOfBullets)
  {
     this.noOfBullets = noOfBullets;
  }
  
   /*
    * Set the movement type of the bullet to be fired.
    *
    * @param bulletMoveType The movement type of the bullet to be fired.
    */
    public void setBulletMoveType(int bulletMoveType)
    {
      this.bulletMoveType = bulletMoveType;
    }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the left. Moves the player left of the screen according to the move speed.
  * Sets the enemy frame to 1.
  */
  public void moveLeft()
  {
    setPosition((Math.max(-getWidth(), getX() - getMoveSpeed())), getY());
    setFrame(0);
  }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the right. Moves the player right of the screen according to the move speed.
  * Sets the enemy frame to 2.
  */
  public void moveRight()
  {
    setPosition((Math.min(getScreenWidth() + getWidth(), getX() + getMoveSpeed())), getY());
    setFrame(1);
  }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the top. Moves the player up the screen according to the move speed.
  * Sets the enemy frame to 3.
  */
  public void moveUp()
  {
    setPosition(getX(), (Math.max(-getHeight(), getY() - getMoveSpeed())));
    setFrame(2);
  }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the bottom. Moves the player down the screen according to the move speed.
  * Sets the enemy frame to 4.
  */
  public void moveDown()
  {
    setPosition(getX(), (Math.min(getScreenHeight() + getHeight(), getY() + getMoveSpeed())));
    setFrame(3);
  }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the top. Moves the player up and left on the screen according to the
  * move speed. Sets the enemy frame to 5.
  */
  public void moveUpAndLeft()
  {
    setPosition((Math.max(-getWidth(), getX() - getMoveSpeed())),
                (Math.max(-getHeight(), getY() - getMoveSpeed())));
    setFrame(4);
  }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the top. Moves the player up and right on the screen according to the
  * move speed. Sets the enemy frame to 6.
  */
  public void moveUpAndRight()
  {
    setPosition((Math.min(getScreenWidth() + getWidth(), getX() + getMoveSpeed())),
                (Math.max(-getHeight(), getY() - getMoveSpeed())));
    setFrame(5);
  }


  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the bottom. Moves the player down and left on the screen according to the
  * move speed. Sets the enemy frame to 5.
  */
  public void moveDownAndLeft()
  {
    setPosition((Math.max(-getWidth(), getX() - getMoveSpeed())),
                (Math.min(getScreenHeight() + getHeight(), getY() + getMoveSpeed())));
    setFrame(4);
  }

  /*
  * Overides the method in the player class so that the enemy can exit the screen
  * at the bottom. Moves the player down and right on the screen according to the
  * move speed. Sets the enemy frame to 6.
  */
  public void moveDownAndRight()
  {
    setPosition((Math.min(getScreenWidth() + getWidth(), getX() + getMoveSpeed())),
                (Math.min(getScreenHeight() + getHeight(), getY() + getMoveSpeed())));
    setFrame(5);
  }

  /*
  * Fire a bullet if the enemies bullet limit is not reached.
  *
  * @param myGameCanvas The gameCanvas.
  * @param enemyBullet Whether or not the bullet is fired from an enemy or 
  *                    from the player.
  */
  public void fire(MyGameCanvas myGameCanvas, boolean enemyBullet)
  {
     if ((getBulletTimer() > getBulletDelay()) && ((noOfBullets == -1) || (noOfBullets > bulletCount)))
     {
        myGameCanvas.getBulletList().addBullet(myGameCanvas, getBulletType(), 
                getBulletSpeed(), getBulletMoveType(), (getWidth() / 4) + getX(), 
                getY(), getBulletTakesLives(), enemyBullet, 3, -1);
        setBulletTimer(0);
        ++bulletCount;
     }
  }
  
  public void onCollission(MyGameCanvas myGameCanvas, int addedScore, int minusLives)
  {
      setLives(getLives() - minusLives);
      myGameCanvas.getExplosionList().addExplosion(myGameCanvas, getX() + (getWidth()/10),
                getY() + (getHeight()/10));
  }
  
  public boolean onDeath(MyGameCanvas myGameCanvas)
  {
      destroyShadow(myGameCanvas);
      destroyAnimation(myGameCanvas);
      return true;
  }
  
  /*
    * Set the number of lives of the player.
    *
    * @param lives The number of lives of the player.
    */
    public void setLives(int lives)
    {
        this.lives = lives;
    }
  
  
  /*
     * Increment the players bullet tumer and charge weapon timer. Checks to 
     * see if the player has more than one life. If it doesnt return true 
     * otherwise return false.
     *
     * @return destroy Whether or not to destroy the player.
     */
    public boolean updatePlayer(MyGameCanvas myGameCanvas)
    {
        if (getLives() < 1)
        {
            setDestroy(true);
        }
        else
        {
            setBulletTimer(getBulletTimer() + 1);
            setChargeTimer(getChargeTimer() + 1);
            setShadowPosition(getX() + getWidth(), getY() + getHeight());
            updateAnimation();
            setAnimPosition(getX() + getAnimationX(), getY() + getAnimationY());
        }
        return getDestroy();
    }
  
  /*
  * Decide how the enemy will move accross the screen based on the moveType passed
  * to it.
  *
  * @param moveType Integer which represents the way the enemy will move.
  */
  public void moveOnPath(int moveType)
  {
    if (moveType == 0)
    {
      moveDown();
    }
    else if (moveType == 1)
    {
      moveDownAndRight();
    }
    else if (moveType == 2)
    {
      moveDownAndLeft();
    }
    else if (moveType == 3)
    {
      if (getY() < (getScreenHeight() / 2))
      {
        moveDown();
      }
      else
      {
        moveDownAndRight();
      }
    }
    else if (moveType == 4)
    {
      if (getY() < (getScreenHeight() / 2))
      {
        moveDown();
      }
      else
      {
        moveDownAndLeft();
      }
    }
    else if (moveType == 5)
    {
      if ((getY() < (getScreenHeight() / 3)) || (getX() > (getScreenWidth() - getWidth())))
      {
        moveDown();
      }
      else
      {
        moveRight();
      }
    }
    else if (moveType == 6)
    {
      if ((getY() < (getScreenHeight() / 3)) || (getX() <= 0))
      {
        moveDown();
      }
      else
      {
        moveLeft();
      }
    }
    else if (moveType == 7)
    {
      if ((getY() < 0) || (getX() > (getScreenWidth() - getWidth())))
      {
        moveDown();
      }
      else
      {
        moveRight();
      }
    }

    else if (moveType == 8)
    {
      if ((getY() < 0) || (getX() <= 0))
      {
        moveDown();
      }
      else
      {
        moveLeft();
      }
    }
    else if (moveType == 9)
    {
      if (getY() < (getScreenHeight() / 3) && (getX() < (getScreenWidth() - getWidth())))
      {
        moveDownAndRight();
      }
      else
      {
        moveDown();
      }
    }
    else if (moveType == 10)
    {
      if (getY() < (getScreenHeight() / 3) && (getX() > 0))
      {
        moveDownAndLeft();
      }
      else
      {
        moveDown();
      }
    }
    else if (moveType == 11)
    {
      if (getY() < (getScreenHeight() / 3))
     {
       moveDownAndRight();
     }
     else
     {
       moveDownAndLeft();
     }
    }
    else if (moveType == 12)
    {
      if (getY() < (getScreenHeight() / 3))
      {
        moveDownAndLeft();
      }
      else
      {
        moveDownAndRight();
      }
    }
    else if (moveType == 13)
    {
      if (getY() < (getScreenHeight() / 2) && (reachedPoint == false))
      {
        moveDown();
      }
      else
      {
        reachedPoint = true;
        moveUp();
      }
      if ((reachedPoint == true) && (getY() < 0))
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 14)
    {
      if (getY() < (getScreenHeight() / 2) && (reachedPoint == false))
      {
        moveDown();
      }
      else
      {
        reachedPoint = true;
        moveUpAndRight();
      }
      if ((reachedPoint == true) && (getY() < 0))
      {
          setLives(getLives() - 1);
      } 
    }
    else if (moveType == 15)
    {
      if (getY() < (getScreenHeight() / 2) && (reachedPoint == false))
      {
        moveDown();
      }
      else
      {
        reachedPoint = true;
        moveUpAndLeft();
      }
      if ((reachedPoint == true) && (getY() < 0))
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 16)
    {
      moveRight();
      if (getX() > getScreenWidth())
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 17)
    {
      moveLeft();
      if (getX() < 0)
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 18)
    {
      moveUpAndRight();
      if (getX() > getScreenWidth())
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 19)
    {
      moveUpAndLeft();
      if (getX() < 0)
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 20)
    {
      if (getY() < (getScreenHeight() / 8))
      {
        moveDownAndRight();
      }
      else if (getY() < (2 * getScreenHeight() / 8))
      {
        moveDown();
      }
      else if (getY() < (4 * getScreenHeight() / 8))
      {
        moveDownAndLeft();
      }
      else
      {
        moveDown();
      }
    }
    else if (moveType == 21)
    {
      if (getY() < (getScreenHeight() / 8))
      {
        moveDownAndLeft();
      }
      else if (getY() < (2 * getScreenHeight() / 8))
      {
        moveDown();
      }
      else if (getY() < (4 * getScreenHeight() / 8))
      {
        moveDownAndRight();
      }
      else
      {
        moveDown();
      }
    }
    else if (moveType == 22)
    {
      if (getX() < (getScreenWidth() / 2) && (reachedPoint == false))
      {
        moveRight();
      }
      else
      {
        reachedPoint = true;
        moveLeft();
      }
      if ((getX() < 0) && (reachedPoint == true))
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 23)
    {
      if (getX() > (getScreenWidth() / 2) && (reachedPoint == false))
      {
        moveLeft();
      }
      else
      {
        reachedPoint = true;
        moveRight();
      }
      if ((getX() > getScreenWidth()) && (reachedPoint == true))
      {
          setLives(getLives() - 1);
      }
    }
    else if (moveType == 24)
    {
        if ((getX() < (getScreenWidth() - getWidth())) && (reachedPoint == false))
        {
            moveRight();
        }
        else
        {
            reachedPoint = true;
        }
        if ((getX() > 0) && (reachedPoint == true))
        {
            moveLeft();
        }
        else
        {
            reachedPoint = false;
        }
    }
  }
}
