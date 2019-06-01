import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/*
 * ItemObject.java
 *
 * Created on 04 September 2006, 16:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * @author Rehan
 */
public class ItemObject extends Enemy
{
    private static final int ITEM_TYPES = 5;
    
    private int addAbility;         //A number indicating what the item does.
    
    
    /** Creates a new instance of ItemObject */
    public ItemObject(MyGameCanvas myGameCanvas, Image image, String name, 
            int moveSpeed, int lives, int frames, int currentX, int currentY, int addToScore, 
            int movePath, int addAbility)
    {
        super(myGameCanvas, image, name, moveSpeed, lives, frames, 0, 0, 0, 
                0, 0, 0, 0, 0, 0, currentX, currentY, addToScore, movePath, 0, false, false, 0, 0);
                
        this.addAbility = addAbility;
        //ADD A LIFE TO THE PLAYER
        if (addAbility == 1)
        {
            setFrame(0);
        }
        //ADD AN ULTIMATE WEAPON TO THE PLAYER
        else if (addAbility == 2)
        {
            setFrame(1);
        } 
        //INCREASE BLUE BULLET SIZE
        else if (addAbility == 3)
        {
            setFrame(2);
        } 
        //INCREASE MISSILE SIZE
        else if (addAbility == 4)
        {
            setFrame(3);
        } 
        //INCREASE FIRE SIZE
        else if (addAbility == 5)
        {
            setFrame(4);
        } 
        //INCREASE THE PLAYERS BULLET SPREAD BY ONE
        else if (addAbility == 6)
        {
            setFrame(5);
        } 
        //ADD A SHIELD TO THE PLAYER
        else if (addAbility == 7)
        {
            setFrame(6);
        } 
    }
    
  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the left. Moves the player left of the screen according to the move speed.
  */
  public void moveLeft()
  {
    setPosition((Math.max(-getWidth(), getX() - getMoveSpeed())), getY());
  }

  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the right. Moves the player right of the screen according to the move speed.
  */
  public void moveRight()
  {
    setPosition((Math.min(getScreenWidth() + getWidth(), getX() + getMoveSpeed())), getY());
  }

  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the top. Moves the player up the screen according to the move speed.
  */
  public void moveUp()
  {
    setPosition(getX(), (Math.max(-getHeight(), getY() - getMoveSpeed())));
  }

  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the bottom. Moves the player down the screen according to the move speed.
  */
  public void moveDown()
  {
    setPosition(getX(), (Math.min(getScreenHeight() + getHeight(), getY() + getMoveSpeed())));
  }

  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the top. Moves the player up and left on the screen according to the
  * move speed.
  */
  public void moveUpAndLeft()
  {
    setPosition((Math.max(-getWidth(), getX() - getMoveSpeed())),
                (Math.max(-getHeight(), getY() - getMoveSpeed())));
  }

  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the top. Moves the player up and right on the screen according to the
  * move speed.
  */
  public void moveUpAndRight()
  {
    setPosition((Math.min(getScreenWidth() + getWidth(), getX() + getMoveSpeed())),
                (Math.max(-getHeight(), getY() - getMoveSpeed())));
  }


  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the bottom. Moves the player down and left on the screen according to the
  * move speed.
  */
  public void moveDownAndLeft()
  {
    setPosition((Math.max(-getWidth(), getX() - getMoveSpeed())),
                (Math.min(getScreenHeight() + getHeight(), getY() + getMoveSpeed())));
  }

  /*
  * Overides the method in the enemy class so that the item can exit the screen
  * at the bottom. Moves the player down and right on the screen according to the
  * move speed.
  */
  public void moveDownAndRight()
  {
    setPosition((Math.min(getScreenWidth() + getWidth(), getX() + getMoveSpeed())),
                (Math.min(getScreenHeight() + getHeight(), getY() + getMoveSpeed())));
  }
    
    public void onCollission(MyGameCanvas myGameCanvas, int addedScore, int minusLives)
    {
        //ADD A LIFE TO THE PLAYER
        if (addAbility == 1)
        {
            myGameCanvas.getPlayer().setLives(myGameCanvas.getPlayer().getLives() + 1);
        }
        //ADD AN ULTIMATE WEAPON TO THE PLAYER
        else if (addAbility == 2)
        {
            myGameCanvas.getPlayer().setUltimateWeapon(myGameCanvas.getPlayer().getUltimateWeapon() + 1);
        }
        //INCREASE BLUE BULLET SIZE
        else if (addAbility == 3)
        {
            myGameCanvas.getPlayer().incrementBlueBullet();
        } 
        //INCREASE MISSILE SIZE
        else if (addAbility == 4)
        {
            myGameCanvas.getPlayer().incrementMissileBullet();
        } 
        //INCREASE FIRE SIZE
        else if (addAbility == 5)
        {
            myGameCanvas.getPlayer().incrementFireBullet();
        } 
        //INCREASE THE PLAYERS BULLET SPREAD BY ONE
        else if (addAbility == 6)
        {
            myGameCanvas.getPlayer().incrementBulletSpread();
        } 
        //ADD A SHIELD TO THE PLAYER
        else if (addAbility == 7)
        {
            myGameCanvas.getPlayer().addShield(myGameCanvas);
        } 
        setLives(getLives() - minusLives);
    }
    
    /*
     * Checks to see if the player has more than one life. If it doesnt return 
     * true otherwise return false.
     *
     * @return destroy Whether or not to destroy the player.
     */
    public boolean updatePlayer(MyGameCanvas myGameCanvas)
    {
        if (getLives() < 1)
        {
            setDestroy(true);
        }
        return getDestroy();
    }
    
    /*
    * Does nothing but overide the fire method in the enemy class so that the item 
    * does not fire any bullets.
    *
    * @param myGameCanvas The gameCanvas.
    * @param enemyBullet Whether or not the bullet is fired from an enemy or 
    *                    from the player.
    */
    public void fire(MyGameCanvas myGameCanvas, boolean enemyBullet)
    {
        //Do Nothing
    }
    
    public boolean onDeath(MyGameCanvas myGameCanvas)
    {
        //Do Nothing.
        return true;
    }
    
}