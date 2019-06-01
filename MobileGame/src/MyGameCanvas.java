import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class MyGameCanvas extends GameCanvas implements Runnable
{
	private boolean isPlay; // Game Loop runs when isPlay is true
	private long delay; // To give thread consistency
	private int width; // To hold screen width
	private int height; // To hold screen height
        private MyGame midlet;
        private Thread t = null;
        private boolean canMove; //Can the player move.
        private boolean autoFire; //Is auto fire on.
        private int difficult;  //Holds the difficulty of the game.

        //Layer Manager.
        private LayerManager layerManager;
        
        //Object retrieves the images.
        private ImageStore imageStore;

        //HUD and text
        private Text text;
        
        //Setup for all of the levels, maps and object map selection.
        private Levels levels;

	// Sprites to be used
	private Player playerSprite;

        //A list of the bullets in the game.
        private BulletList bulletList;

        //A list of the explosions.
        private ExplosionList explosionList;

        /*
         * Constructor and initialization.
         */
	public MyGameCanvas(MyGame midlet) throws Exception
	{
		super(true);
                setFullScreenMode(true);
		width = getWidth();
		height = getHeight();
		delay = 20;
                this.midlet = midlet;

                canMove = true;
                autoFire = false;
                difficult = 5;

                //Layer Manager.
                layerManager = new LayerManager();
                
                //Object retrieves the images.
                imageStore = new ImageStore();

                //A list of all the bullets
                bulletList = new BulletList();

                //A list of all the explosions
                explosionList = new ExplosionList();
                
                // Initialize the player.
		playerSprite = new Player(this, imageStore.getImage("Ship"), 
                        "player", 6, difficult + 1, 10, 3, 3, 8, 1, 1, 50, 3, 10, false, true, 7, 8, 0, 32);
                
                //Initialize the HUD.
                text = new Text(this); 
                
                //Setup for all of the levels, maps and object map selection.
                levels = new Levels(this);

                //Append the player sprite to the layer manager.
                layerManager.append(playerSprite);
         }
         
         public void appendLayer(TiledLayer tiledLayer)
         {
             layerManager.append(tiledLayer);
         }
         
         public void removeLayer(TiledLayer tiledLayer)
         {
             layerManager.remove(tiledLayer);
         }
         
         public void insertLayer(TiledLayer tiledLayer, int position)
         {
             layerManager.insert(tiledLayer, position);
         }

         /*
         * Automatically start thread for game loop.
         */
	public void start()
	{
            isPlay = true;
            t = new Thread(this);
            t.start();
	}

        /*
         * Stops the main Game Loop.
         */
	public void stop()
        {
            t = null;
            isPlay = false;
            midlet.mainMenuScreenShow();
        }

        /*
         * Main Game Loop.
         */
	public void run()
	{
            Graphics g = getGraphics();
            while (isPlay == true)
            {
                input();
                drawScreen(g);
                try { Thread.sleep(delay); }
                catch (InterruptedException ie) {}
            }
	}

        /*
         * Method to Handle User Inputs.
         */
	private void input()
	{
          if (canMove)
          {
            int keyStates = getKeyStates();
            playerSprite.notMoving();
            // Left
            if ( (keyStates & LEFT_PRESSED) != 0) {
              playerSprite.moveLeft();
            }
            // Right
            if ( (keyStates & RIGHT_PRESSED) != 0) {
              playerSprite.moveRight();
            }
            // Up
            if ( (keyStates & UP_PRESSED) != 0) {
              playerSprite.moveUp();
            }
            // Down
            if ( (keyStates & DOWN_PRESSED) != 0) {
              playerSprite.moveDown();
            }
            //Up and Left
            if ( (keyStates & GAME_A_PRESSED) != 0) {
              playerSprite.moveUpAndLeft();
            }
            //Up and Right
            if ( (keyStates & GAME_B_PRESSED) != 0) {
              playerSprite.moveUpAndRight();
            }
            //Fire ultimate weapon
            if ( (keyStates & GAME_C_PRESSED) != 0) {
              playerSprite.fireUltimateWeapon(this);
            }
            //Fire charged beam weapon.
            if ( (keyStates & GAME_D_PRESSED) != 0) {
              playerSprite.fireBeam(this, false);
            }
            // Player Fires
            if ( ( (keyStates & FIRE_PRESSED) != 0)) { //Fire a bullet
              if (!autoFire)
              {
                playerSprite.fire(this, false);
              }
            }
          }
	}
        
        protected void keyPressed(int keyCode)
        {
            if (keyCode == -6)
            {
                stop();
            }
        }

	/*
         * Method to Display Graphics
         *
         * @param g Graphics object
         */
	private void drawScreen(Graphics g)
	{
            g.setColor(0x000000);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(0x000000);

            //Update the level
            levels.updateLevel(this);

            //If auto fire is on then keep firing.
            if (autoFire)
            {
              playerSprite.fire(this, false);
            }
            //Check players lives and update him.
            if (playerSprite.updatePlayer(this))
            {
                TiledLayer gameOver = text.createText(this, "GAME OVER", 0, 0);
                gameOver.setPosition((getWidth()/2) - (gameOver.getWidth()/2),
                (getHeight()/2) - gameOver.getHeight());
                canMove = false;
                layerManager.insert(gameOver, 0);
                if (playerSprite.onDeath(this))
                {
                    layerManager.remove(playerSprite);
                    stop();
                }
            }
            
            //Move the bullets and remove any that have exited the screen.
            bulletList.moveBullets(this);
            
            //Detect all collissions and create explosions.
            detectCollissions();

            //Update the HUD
            text.initHUD(this); 

            //Update all of the explosions.
            explosionList.updateExplosions(this);
            
            //Check for end of map (Every enemy is dead).
            if (levels.getEndLevel())
            {
                //Increment to the next level and initialize it.
                if (levels.incrementLevel(this))
                {
                    //If all levels have been completed.
                    stop();
                }
            }

            //Paint all layers
            layerManager.paint(g, 0, 0);
            //Flush graphics to make performance gains.
            flushGraphics();
	}

        /*
        * Add a sprite to the layer manager.
        *
        * @param sprite The sprite to be added to the layer manager.
        */
        public void insertSprite(Sprite sprite)
        {
          layerManager.insert(sprite, 0);
        }

        /*
        * Remove a sprite from the layer manager.
        *
        * @param sprite The sprite to be removed from the layer manager.
        */
        public void removeSprite(Sprite sprite)
        {
          layerManager.remove(sprite);
        }

        /*
        * Return the player object.
        *
        * @return The player object.
        */
        public Player getPlayer()
        {
          return playerSprite;
        }
        
        
        public Text getText()
        {
          return text;
        }

        /*
        * Return the vector of bullets.
        *
        * @return The list of bullets.
        */
        public BulletList getBulletList()
        {
          return bulletList;
        }

        /*
        * Return the vector of explosions.
        *
        * @return The list of explosions.
        */
        public ExplosionList getExplosionList()
        {
          return explosionList;
        }
        
        /*
        * Return the vector of enemies.
        *
        * @return The vector of enemies.
        */
        public ObjectMap getObjectMap()
        {
          return levels.getObjectMap();
        }

        /*
        * Return the image store.
        *
        * @return image store.
        */
        public ImageStore getImageStore()
        {
          return imageStore;
        }


        public void detectCollissions()
        {
          Enemy e = null;
          if (levels.getObjectMap() != null)
          {
              for (int i = (levels.getObjectMap().getObjectList().size() - 1); i != -1; --i)
              {
                    e = (Enemy) levels.getObjectMap().getObjectList().elementAt(i);
                    //If it is an enemy
                    if (e.getName().equals("Enemy"))
                    {
                        //Check for enemies colliding with player.
                        if (e.collidesWith(playerSprite, true))
                        {
                          e.onCollission(this, 0, 1);
                          playerSprite.onCollission(this, e.getAddToScore(), 1);
                        }

                        for (int j = (bulletList.getBulletList().size() - 1); j != -1; --j)
                        {
                          Bullet b = (Bullet) bulletList.getBulletList().elementAt(j);

                          //If the bullet is allowed to collide with other objects.
                          if (b.getCanCollide())
                          {
                              //Check for bullets colliding with the player.
                              if (b.getEnemyBullet())           //If the bullet is from an enemy.
                              {
                                if (b.collidesWith(playerSprite, true))
                                {
                                  b.checkDeath(true);
                                  playerSprite.onCollission(this, 0, b.getRemoveLives());
                                  b = null;
                                }
                              }
                              //Check for bullets colliding with the enemies.
                              else if (!b.getEnemyBullet())     //If the bullet is not from an enemy.     
                              {
                                if (e.collidesWith(b, true))
                                {
                                  e.onCollission(this, 0, b.getRemoveLives());
                                  b.checkDeath(true);
                                  b = null;
                                  playerSprite.setScore(playerSprite.getScore() + e.getAddToScore());
                                }
                              }
                          }
                        }
                    }
                    //If it is an item.
                    else if (e.getName().equals("Item"))
                    {
                        //Check for items colliding with player.
                        if (e.collidesWith(playerSprite, true))
                        {
                          e.onCollission(this, 0, 1);
                          playerSprite.setScore(playerSprite.getScore() + e.getAddToScore());
                        }
                    }

                    //If the conditions for destroying the object have been reached destroy it.
                    if (e.updatePlayer(this))
                    {
                      e.onDeath(this);
                      levels.getObjectMap().getObjectList().removeElementAt(i);
                      layerManager.remove(e);
                      e = null;
                    }
                }
            }
        }
}
