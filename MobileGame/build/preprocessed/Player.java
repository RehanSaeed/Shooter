import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Player extends Sprite
{
    private static final int NO_OF_BULLET_SPREADS = 4;
    private static final int MAX_LIVES = 10;
    private static final int INVULNERABLE_TIME = 10;
    
    private int screenWidth;                //The width of the screen.
    private int screenHeight;               //The height of the screen.
	
    //PLAYER ATTRIBUTES
    private String name;                    //The name of the player
    private int moveSpeed;                  //The speed at which the player is to move.
    private int score;                      //The players score.
    protected int lives;                      //The number of lives the player has.
    private int frames;                     //The number of frames in the animation of the player (1 if there is no animation).
    private boolean destroy;                //Boolean telling you whether or not to destroy the player.
    private int invulnerabilityCount;       //After a collission with an enemy. count up to the next collission being allowed.
    private boolean canFire;                //Whether or not the player can fire a bullet;
    
    //SHIELD ATTRIBUTES
    private Sprite shieldSprite;            //The shield sprite;
    private boolean shield;                 //Whether or not a shield is in place.
    
    //SHADOW ATTRIBUTES
    private Sprite shadow;                  //The shadow sprite;
    private int shadowFrame;                //The frame in which the shadow exists;
    
    //ANIMATION SPRITE
    private Sprite anim;                    //The animation for the player.
    private int animationX;
    private int animationY;
    private int animFrame;             //The frame of the image with the animation in it.

    //BULLET ATTRIBUTES
    private int bulletTimer;                //The time to the next bullet being allowed to be fired.
    private int bulletDelay;                //The delay between being able to fire a bullet.
    private int bulletType;                 //The image type to use for the bullet.
    private int bulletSpeed;                //The speed at which the bullet will move.
    private int bulletTakesLives;           //The number of lives the bullets will remove.
    private int bulletSpread;               //The spread of bullets fired.

    //CHARGE WEAPON ATTRIBUTES
    private int chargeFireDelay;            //The delay before another charged beam can be shot.
    private int chargeTimer;                //The time to the next charge being allowed to be fired.

    //ULTIMATE WEAPON ATTRIBUTES
    private int ultimateWeapon;             //The number of ultimate weapons the player has.
    private int ultimateWeaponRemovesLives; //The number of lives the ultimate weapon removes.


    /*
    * Initialize the player class with the given attributes and set the 
    * score to zero.
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
    * @param bulletTakesLives The number of lives the bullet removes.
    * @param bulletSpread The spread of bullets fired.
    * @param chargeFireDelay The delay between being able to fire the 
    *                        charged beam.
    * @param ultimateWeapon The number of ultimate weapons held by the 
    *                       player.
    * @param ultimateWeaponRemovesLives The number of lives removed by the 
    *                                   ultimate weapon.
    */
    public Player(MyGameCanvas myGameCanvas, Image image, 
            String name, int moveSpeed, int lives, int frames, int bulletDelay, 
            int bulletType, int bulletSpeed, int bulletTakesLives, int bulletSpread, 
            int chargeFireDelay, int ultimateWeapon, int ultimateWeaponRemovesLives, 
            boolean shield, boolean setShadow, int shadowFrame, int animFrame, int animationX, int animationY)
    {
        super(image, image.getHeight(), image.getHeight());

        //Se the starting position
        setAtStartPosition(myGameCanvas);

        this.name = name;
        this.moveSpeed = moveSpeed;
        this.lives = lives;
        if (lives > MAX_LIVES)
        {
            lives = MAX_LIVES;
        }
        this.frames = frames;
        this.screenWidth = myGameCanvas.getWidth();
        this.screenHeight = myGameCanvas.getHeight();
        this.bulletDelay = bulletDelay;
        this.bulletType = bulletType;
        this.bulletSpeed = bulletSpeed;
        this.bulletTakesLives = bulletTakesLives;
        this.bulletSpread = bulletSpread;
        this.ultimateWeapon = ultimateWeapon;
        this.ultimateWeaponRemovesLives = ultimateWeaponRemovesLives;
        this.chargeFireDelay = chargeFireDelay;
        this.shield = shield;
        this.shadowFrame = shadowFrame;
        this.animFrame = animFrame;
        this.animationX = animationX;
        this.animationY = animationY;
        
        if (setShadow == true)
        {
            setShadow(myGameCanvas, image);
        }
        if (frames > animFrame)
        {
            insertAnimation(myGameCanvas, image, animationX, animationY);
        }
        
        shieldSprite = null;
        destroy = false;    //Do not destroy the player.
        score = 0;          //Start the score at zero.
        bulletTimer = 0;    //Start the bullet timer at zero.
    }
    
    public void setAtStartPosition(MyGameCanvas myGameCanvas)
    {
        setPosition(((myGameCanvas.getWidth() - getHeight()) / 2), myGameCanvas.getHeight() - getHeight());
    }
    
    public void setVisible(boolean visible)
    {
        if (visible == false)
        {
            super.setVisible(false);
            shadow.setVisible(false);
            anim.setVisible(false);
            if (shieldSprite != null)
            {
                shieldSprite.setVisible(false);
            }
        }
        else if (visible == true)
        {
            super.setVisible(true);
            shadow.setVisible(true);
            anim.setVisible(true);
            if (shieldSprite != null)
            {
                shieldSprite.setVisible(true);
            }
        }
    }
    
    public void insertAnimation(MyGameCanvas myGameCanvas, Image image, int X, int Y)
    {
        anim = new Sprite(image, image.getHeight(), image.getHeight());
        anim.setFrame(animFrame);
        anim.setPosition(X, Y);
        myGameCanvas.insertSprite(anim);
    }

    public int getAnimationX()
    {
      return animationX;
    }
    
    public int getAnimationY()
    {
      return animationY;
    }
    
    
    /*
     * Return the screen width.
     *
     * @return The screen width.
     */
    public int getScreenWidth()
    {
      return screenWidth;
    }
    
    /*
     * Return the charge timer.
     *
     * @return The charge timer.
     */
    public int getChargeTimer()
    {
      return chargeTimer;
    }
    
    /*
     * Return the charge timer.
     *
     * @return The charge timer.
     */
    public void setChargeTimer(int chargeTimer)
    {
      this.chargeTimer = chargeTimer;
    }
    
    public void setCanFire(boolean canFire)
    {
        this.canFire = canFire;
    }

    /*
     * Return the screen height.
     *
     * @return The screen height.
     */
    public int getScreenHeight()
    {
      return screenHeight;
    }
    
    /*
     * Return the name of the player.
     *
     * @return The name of the player.
     */
    public String getName()
    {
      return name;
    }

    /*
     * Return the movement speed of the player.
     *
     * @return The movement speed of the player.
     */
    public int getMoveSpeed()
    {
      return moveSpeed;
    }
    
    /*
     * Return the score of the player.
     *
     * @return The score of the player.
     */
    public int getScore()
    {
      return score;
    }

    /*
     * Return the number of lives of the player.
     *
     * @return The number of lives of the player.
     */
    public int getLives()
    {
      return lives;
    }
    
    /*
     * Return the number of frames of the players image.
     *
     * @return The number of frames of the players image.
     */
    public int getFrames()
    {
      return frames;
    }
    
    /*
     * Return whether or not to destroy the player.
     *
     * @return Whether or not to destroy the player.
     */
    public boolean getDestroy()
    {
      return destroy;
    }
    
    /*
     * Return the bulletTimer.
     *
     * @return The incrementing time between firing bullets.
     */
    public int getBulletTimer()
    {
      return bulletTimer;
    }
    
    /*
     * Return the bullet delay.
     *
     * @return The delay between firing a bullet.
     */
    public int getBulletDelay()
    {
      return bulletDelay;
    }
    
    /*
     * Return the bullet type.
     *
     * @return The image to be used for the bullet.
     */
    public int getBulletType()
    {
      return bulletType;
    }

    /*
     * Return the bullet speed.
     *
     * @return The speed to be used for the bullet.
     */
    public int getBulletSpeed()
    {
      return bulletSpeed;
    }

    /*
     * Return the number of lives taken off by the bullet.
     *
     * @return The number of lives taken off by the bullet.
     */
    public int getBulletTakesLives()
    {
      return bulletTakesLives;
    }
    
    /*
     * Return the spread of bullets.
     *
     * @return The spread of bullets.
     */
    public int getBulletSpread()
    {
      return bulletSpread;
    }

    /*
     * Return the number of ultimate weapons the player has.
     *
     * @return The number of ultimate weapons the player has.
     */
    public int getUltimateWeapon()
    {
      return ultimateWeapon;
    }

   /*
    * Return the number of lives the ultimate weapon removes.
    *
    * @return The number of lives the ultimate weapon removes.
    */
    public int getUltimateWeaponRemovesLives()
    {
      return ultimateWeaponRemovesLives;
    }
    
    /*
    * Return whether or not a shield is in place.
    *
    * @return Whether or not a shield is in place.
    */
    public boolean getShield()
    {
      return shield;
    }
    
    /*
    * Set the speed at which the player can move.
    *
    * @param moveSpeed The speed at which the player can move.
    */
    public void setMoveSpeed(int moveSpeed)
    {
      this.moveSpeed = moveSpeed;
    }
    
    /*
    * Set the score of the player.
    *
    * @param score The score of the player.
    */
    public void setScore(int score)
    {
      this.score = score;
    }

    /*
    * Set the number of lives of the player.
    *
    * @param lives The number of lives of the player.
    */
    public void setLives(int lives)
    {
        this.lives = lives;
        if (lives > MAX_LIVES)
        {
            this.lives = MAX_LIVES;
        }
    }
    
    /*
     * Set the boolean value which decides whether or not to destroy the player.
     *
     * @param destroy Whether or not to destroy the player.
     */
    public void setDestroy(boolean destroy)
    {
      this.destroy = destroy;
    }
    
    /*
    * Set the count to the next bullet being able to be fired.
    *
    * @param bulletTimer The count to the next bullet being able to be fired.
    */
    public void setBulletTimer(int bulletTimer)
    {
      this.bulletTimer = bulletTimer;
    }

    /*
    * Set the delay to the next bullet being able to be fired.
    *
    * @param bulletDelay The delay to the next bullet being able to be fired.
    */
    public void setBulletDelay(int bulletDelay)
    {
      this.bulletDelay = bulletDelay;
    }
    
    /*
    * Set the image of the bullet to be fired.
    *
    * @param bulletType The image of the bullet to be fired.
    */
    public void setBulletType(int bulletType)
    {
      this.bulletType = bulletType;
    }
    
    /*
    * Set the speed of the bullet to be fired.
    *
    * @param bulletSpeed The speed of the bullet to be fired.
    */
    public void setBulletSpeed(int bulletSpeed)
    {
      this.bulletSpeed = bulletType;
    }
    
    /*
    * Set the number of lives the bullet takes off.
    *
    * @param bulletTakesLives The number of lives the bullet takes off.
    */
    public void setBulletTakesLives(int bulletTakesLives)
    {
      this.bulletTakesLives = bulletTakesLives;
    }
    
    /*
     * Set the spread of bullets.
     *
     * @param bulletSpread The spread of bullets.
     */
    public void setBulletSpread(int bulletSpread)
    {
      this.bulletSpread = bulletSpread;
    }

    /*
    * Set the number of ultimate weapons the player has.
    *
    * @param ultimateWeapon The number of ultimate weapons the player has.
    */
    public void setUltimateWeapon(int ultimateWeapon)
    {
      this.ultimateWeapon = ultimateWeapon;
    }
    
    /*
    * Set the number of lives the ultimate weapon removes.
    *
    * @param ultimateWeaponRemovesLives The number of lives the ultimate weapon removes.
    */
    public void setUltimateWeaponRemovesLives(int ultimateWeaponRemovesLives)
    {
      this.ultimateWeaponRemovesLives = ultimateWeaponRemovesLives;
    }
    
    /*
    * Give the player a shield.
    *
    * @param shield Whether or not the player has a shield
    */
    public void setUltimateWeaponRemovesLives(boolean shield)
    {
      this.shield = shield;
    }
    
    public void setShadow(MyGameCanvas myGameCanvas, Image image)
    {
        shadow = new Sprite(image, image.getHeight(), image.getHeight());
        shadow.setFrame(shadowFrame);
        shadow.setPosition(getX() + getWidth(), getY() + getHeight());
        myGameCanvas.insertSprite(shadow);
    }
    
    public void setShadowPosition(int X, int Y)
    {
        if (shadow != null)
        {
            shadow.setPosition(X, Y);
        }
    }
    
    public void removeShadow(MyGameCanvas myGameCanvas)
    {
        if (shadow != null)
        {
            myGameCanvas.removeSprite(shadow);
            shadow = null;
        }
    }
    
    /*
     * Increase the bullet spread by one if it has not reached the maximum value 
     * of NO_OF_BULLET_SPREADS.
     */
    public void incrementBulletSpread()
    {
        if (bulletSpread < NO_OF_BULLET_SPREADS)
        {
            ++bulletSpread;
        }
    }
    
    /*
     * Increase the blue bullet spread by one.
     */
    public void incrementBlueBullet()
    {
        if (bulletType == 3 || bulletType == 5 || bulletType == 6 || bulletType == 15)
        {
            bulletType = 4;
            bulletTakesLives = 2;
            bulletDelay = 5;
        }
    }
    
    /*
     * Increase the blue bullet spread by one.
     */
    public void incrementMissileBullet()
    {
        if (bulletType == 3 || bulletType == 15)
        {
            bulletType = 5;
            bulletTakesLives = 3;
            bulletDelay = 8;
        }
        else if (bulletType == 4 || bulletType == 5)
        {
            bulletType = 6;
            bulletTakesLives = 4;
            bulletDelay = 10;
        }
    }

    /*
     * Increase the blue bullet spread by one.
     */
    public void incrementFireBullet()
    {
        if (bulletType == 3 || bulletType == 5)
        {
            bulletType = 15;
            bulletTakesLives = 2;
            bulletDelay = 1;
        }
        else if (bulletType == 4 || bulletType == 15)
        {
            bulletType = 15;
            bulletTakesLives = 3;
            bulletDelay = 1;
        }
        
    }
    
    /*
     * Increase the bullet spread by one if it has not reached the maximum value 
     * of NO_OF_BULLET_SPREADS.
     */
    public void addShield(MyGameCanvas myGameCanvas)
    {
        if (shield == false)
        {
            shield = true;
            Image image = myGameCanvas.getImageStore().getImage("Shield");
            shieldSprite = new Sprite(image, image.getWidth(), image.getHeight());
            shieldSprite.setPosition(getX(), getY());
            myGameCanvas.insertSprite(shieldSprite);
        }
    }
    
    /*
    * When the player is not moving.
    */
    public void notMoving()
    {
      setFrame(0);
      anim.setPosition(getX() + animationX, getY() + animationY);
    }
    
    /*
    * Moves the player left if the player is not moving off the screen by the 
    * move speed and sets the frame to 1.
    */
    public void moveLeft()
    {
      setPosition((Math.max(0, getX() - getMoveSpeed())), getY());
      setFrame(1);
      anim.setPosition(getX() + animationX - 3, getY() + animationY);
    }

    /*
    * Moves the player right if the player is not moving off the screen by the 
    * move speed and sets the frame to 2.
    */
    public void moveRight()
    {
      if (getX() + getWidth() < screenWidth)
      {
        setPosition((Math.min(screenWidth, getX() + getMoveSpeed())), getY());
      }
      setFrame(2);
      anim.setPosition(getX() + animationX + 3, getY() + animationY);
    }

    /*
    * Moves the player up if the player is not moving off the screen by the 
    * move speed and sets the frame to 3.
    */
    public void moveUp()
    {
      setPosition(getX(), (Math.max(0, getY() - getMoveSpeed())));
      setFrame(3);
      anim.setPosition(getX() + animationX, getY() + animationY - 5);
    }

    /*
    * Moves the player down if the player is not moving off the screen by the 
    * move speed and sets the frame to 4.
    */
    public void moveDown()
    {
      if (getY() + getHeight() < screenHeight)
      {
        setPosition(getX(), (Math.min(screenHeight, getY() + getMoveSpeed())));
      }
      setFrame(4);
      anim.setPosition(getX() + animationX, getY() + animationY);
    }

    /*
    * Moves the player up and left if the player is not moving off the screen by the 
    * move speed and sets the frame to 5.
    */
    public void moveUpAndLeft()
    {
      setPosition((Math.max(0, getX() - getMoveSpeed())),
                  (Math.max(0, getY() - getMoveSpeed())));
      setFrame(5);
      anim.setPosition(getX() + animationX + 5, getY() + animationY - 10);
    }

    /*
    * Moves the player up and right if the player is not moving off the screen by the 
    * move speed and sets the frame to 6.
    */
    public void moveUpAndRight()
    {
      if (getX() + getWidth() < screenWidth)
      {
        setPosition((Math.min(screenWidth, getX() + getMoveSpeed())), getY());
      }
      setPosition(getX(), (Math.max(0, getY() - getMoveSpeed())));
      setFrame(6);
      anim.setPosition(getX() + animationX - 5, getY() + animationY - 10);
    }

    /*
    * Moves the player down and left if the player is not moving off the screen by the 
    * move speed and sets the frame to 5.
    */
    public void moveDownAndLeft()
    {
      if (getY() + getHeight() < screenHeight)
      {
        setPosition(getX(), (Math.min(screenHeight, getY() + getMoveSpeed())));
      }
      setPosition((Math.max(0, getX() - getMoveSpeed())), getY());
      setFrame(5);
    }

    /*
    * Moves the player down and right if the player is not moving off the screen by the 
    * move speed and sets the frame to 6.
    */
    public void moveDownAndRight()
    {
      if (getX() + getWidth() < screenWidth)
      {
        setPosition((Math.min(screenWidth, getX() + getMoveSpeed())), getY());
      }
      if (getY() + getHeight() < screenHeight)
      {
        setPosition(getX(), (Math.min(screenHeight, getY() + getMoveSpeed())));
      }
      setFrame(6);
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
        if (lives < 1)
        {
            destroy = true;
        }
        ++bulletTimer;
        ++chargeTimer;
        ++invulnerabilityCount;
        myGameCanvas.removeSprite(this);
        myGameCanvas.insertSprite(this);
        if (shieldSprite != null)
        {
            shieldSprite.setPosition(getX(), getY());
            myGameCanvas.removeSprite(shieldSprite);
            myGameCanvas.insertSprite(shieldSprite);
        }
        if (shadow != null)
        {
            shadow.setPosition(getX() + getWidth(), getY() + getHeight());
        }
        updateAnimation();      
        return destroy;
    }
    
    public void updateAnimation()
    {
        if (anim != null)
        {
            if (((anim.getFrame()+1) > animFrame) && ((anim.getFrame()+1) < frames))
            {
                anim.setFrame(anim.getFrame() + 1);
            }
            else
            {
                anim.setFrame(animFrame);
            }
        }
    }
    
    public void setAnimPosition(int X, int Y)
    {
        anim.setPosition(X, Y);
    }

    /*
     * Fire a bullet.
     *
     * @param myGameCanvas The gameCanvas.
     * @param enemyBullet Whether or not the bullet is fired from an enemy or 
     *                    from the player.
     */
    public void fire(MyGameCanvas myGameCanvas, boolean enemyBullet)
    {
        if (canFire)
        {
            if (bulletType != 15)
            {
                  if (bulletTimer > bulletDelay)
                  {
                      if (bulletType == 3 || bulletType == 5)
                      {
                          if (bulletSpread == 1)
                          {
                               myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 4), 
                                    getY() - (getHeight() / 10), bulletTakesLives, enemyBullet, 2, -1);
                          }
                          else if (bulletSpread == 2)
                          {
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX(), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 2), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                          }
                          else if (bulletSpread == 3)
                          {
                              //Move a bullet up.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 4), 
                                    getY() - (getHeight() / 10), bulletTakesLives, enemyBullet, 2, -1);
                              //Move a bullet left.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 4, 
                                    bulletSpeed, 6, getX(), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              //Move a bullet right.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 8, 
                                    bulletSpeed, 7, getX() + (getWidth() / 2), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                          }
                          else if (bulletSpread == 4)
                          {
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX(), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 2), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              //Move a bullet left.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 4, 
                                    bulletSpeed, 6, getX(), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              //Move a bullet right.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 8, 
                                    bulletSpeed, 7, getX() + (getWidth() / 2), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                          }
                      }
                      else if (bulletType == 4 || bulletType == 6)
                      {
                          if (bulletSpread == 1)
                          {
                               myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 20), 
                                    getY() - (getHeight() / 10), bulletTakesLives, enemyBullet, 2, -1);
                          }
                          else if (bulletSpread == 2)
                          {
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() - (getWidth() / 5), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 3), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                          }
                          else if (bulletSpread == 3)
                          {
                              //Move a bullet up.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 20), 
                                    getY() - (getHeight() / 10), bulletTakesLives, enemyBullet, 2, -1);
                              //Move a bullet left.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 4, 
                                    bulletSpeed, 6, getX() - (getWidth() / 5), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              //Move a bullet right.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 8, 
                                    bulletSpeed, 7, getX() + (getWidth() / 3), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                          }
                          else if (bulletSpread == 4)
                          {
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() - (getWidth() / 5), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                    bulletSpeed, 2, getX() + (getWidth() / 3), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              //Move a bullet left.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 4, 
                                    bulletSpeed, 6, getX() - (getWidth() / 5), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                              //Move a bullet right.
                              myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType + 8, 
                                    bulletSpeed, 7, getX() + (getWidth() / 3), getY() - (getHeight() / 10), bulletTakesLives, 
                                      enemyBullet, 2, -1);
                          }
                      }
                      bulletTimer = 0;
                  }
            }
            else        //If the bullet type is 15
            {
                if (bulletTimer > bulletDelay)
                  {
                      if (bulletSpread == 1)
                      {
                           myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                0, 1, getX(), 
                                (getY() - (3 * getHeight())), bulletTakesLives, enemyBullet, 4, 3);
                      }
                      else if (bulletSpread == 2)
                      {
                          myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                0, 1, getX(), (getY() - (3 * getHeight())), bulletTakesLives, 
                                  enemyBullet, 4, 5);
                      }
                      else if (bulletSpread == 3)
                      {
                          myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                0, 1, getX() + (getWidth() / 4), 
                                (getY() - (3 * getHeight())), bulletTakesLives, enemyBullet, 4, 10);
                      }
                      else if (bulletSpread == 4)
                      {
                          myGameCanvas.getBulletList().addBullet(myGameCanvas, bulletType, 
                                0, 1, getX() + (getWidth() / 4), 
                                (getY() - (3 * getHeight())), bulletTakesLives, enemyBullet, 4, 15);
                      }
                    bulletTimer = 0;
                  }
            }
        }
    }

    /*
     * Fire a charged beam weapon.
     *
     * @param myGameCanvas The gameCanvas.
     * @param enemyBullet Whether or not the bullet is fired from an enemy or 
     *                    from the player.
     */
    public void fireBeam(MyGameCanvas myGameCanvas, boolean enemyBullet)
    {
      if ((chargeTimer > chargeFireDelay) && canFire)
      {
        myGameCanvas.getBulletList().addBullet(myGameCanvas, 16, moveSpeed, 12, 
                getX(), getY()-280, bulletTakesLives, enemyBullet, 3, 10);
        bulletTimer = 0;
        chargeTimer = 0;
      }
    }
    
    public void fireUltimateWeapon(MyGameCanvas myGameCanvas)
    {
        if ((ultimateWeapon) > 0 && canFire)
        {
            --ultimateWeapon;
            myGameCanvas.getExplosionList().addLargeExplosionsAroundScreen(myGameCanvas, 100);
            for (int i = (myGameCanvas.getObjectMap().getObjectList().size() - 1); i != -1; --i)
            {
              Enemy e = (Enemy) myGameCanvas.getObjectMap().getObjectList().elementAt(i);
              e.setLives(e.getLives() - ultimateWeaponRemovesLives);
              myGameCanvas.getExplosionList().addExplosion(myGameCanvas, e.getX(), e.getY());
              score += e.getAddToScore();
            }
        }
    }
    
    public void onCollission(MyGameCanvas myGameCanvas, int addedScore, int minusLives)
    {
        if (invulnerabilityCount > INVULNERABLE_TIME)
        {
            //If the player has a sheild and collides with an enemy, remove the shield.
            if (shield)
            {
                myGameCanvas.removeSprite(shieldSprite);
                shield = false;
                shieldSprite = null;
                myGameCanvas.getExplosionList().addExplosion(myGameCanvas, getX() + (getWidth()/10),
                      getY() + (getHeight()/10));
            }
            else    //Otherwise remove a life.
            {
                lives -= minusLives;
                score += addedScore;
                bulletSpread = 1;           //Set the bullet spread back to one.
                bulletType = 3;             //Set the bullet type back to one.
                bulletTakesLives = 1;
                bulletDelay = 3;
                myGameCanvas.getExplosionList().addExplosion(myGameCanvas, getX() + (getWidth()/10),
                      getY() + (getHeight()/10));
            }
            invulnerabilityCount = 0;
        }
    }
    
    public boolean onDeath(MyGameCanvas myGameCanvas)
    {
        setPosition(getX(), (Math.min(screenHeight, getY() + getMoveSpeed())));
        setFrame(1);
        destroyShadow(myGameCanvas);
        destroyAnimation(myGameCanvas);
        return myGameCanvas.getExplosionList().addMultipleExplosions(myGameCanvas, 50);
    }
    
    public void destroyShadow(MyGameCanvas myGameCanvas)
    {
        if (shadow != null)
        {
            myGameCanvas.removeSprite(shadow);
        }
        shadow = null;
    }
    
    public void destroyAnimation(MyGameCanvas myGameCanvas)
    {
        if (anim != null)
        {
            myGameCanvas.removeSprite(anim);
        }
        anim = null;
    }
}
