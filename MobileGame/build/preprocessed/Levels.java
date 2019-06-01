import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/*
 * Levels.java
 *
 * Created on 17 September 2006, 14:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * @author Rehan
 */
public class Levels
{
    private static final int levelInfoVisible = 60;     //The time for which the level information should be visible.
    
    private int levelInfoCount;
    
    //Maps to be used.
    private Maps backgroundMap;
    private Maps foregroundMap;
    private TiledLayer tiledBackground;
    private TiledLayer tiledForeground;
    private ObjectMap objectMap;
    
    private TiledLayer levelInformation;
    private TiledLayer levelName;

    private int level;
    private int[][] levels;
    
    public Levels(MyGameCanvas myGameCanvas)
    {
        level = 0;
        levelInfoCount = 0;
        setLevels();
        destroyLevel(myGameCanvas);
    }
    
    public boolean incrementLevel(MyGameCanvas myGameCanvas)
    {
        boolean endGame = false;
        if (level < (levels.length - 1))
        {
            ++level;
            destroyLevel(myGameCanvas);
        }
        else if (level == (levels.length - 1))
        {
            endGame = true;
        }
        return endGame;
    }
    
    public void destroyLevel(MyGameCanvas myGameCanvas)
    {
        //Initialize the maps
        if ((tiledForeground != null) && (tiledBackground != null))
        {
            myGameCanvas.removeLayer(tiledForeground);
            myGameCanvas.removeLayer(tiledBackground);
        }
        backgroundMap = null;
        foregroundMap = null;
        objectMap = null;
        
        levelInformation = myGameCanvas.getText().createText(myGameCanvas, "LEVEL " + (level + 1), 0, 0);
        levelInformation.setPosition((myGameCanvas.getWidth()/2) - (levelInformation.getWidth()/2),
                (myGameCanvas.getHeight()/2) - levelInformation.getHeight());
        levelName = myGameCanvas.getText().createText(myGameCanvas, getText(levels[level][7]), 0, 32);
        levelName.setPosition((myGameCanvas.getWidth()/2) - (levelName.getWidth()/2),
                (myGameCanvas.getHeight()/2) + levelName.getHeight());
        myGameCanvas.insertLayer(levelInformation, 0);
        myGameCanvas.insertLayer(levelName, 0);
        levelInfoCount = 0;
        
        myGameCanvas.getPlayer().setVisible(false);
        myGameCanvas.getPlayer().setCanFire(false);
        myGameCanvas.getText().setHUDVisibility(false);
    }
    
    public void initLevel(MyGameCanvas myGameCanvas)
    {
        backgroundMap = new Maps(myGameCanvas.getWidth(), myGameCanvas.getHeight(), levels[level][0]);
        foregroundMap = new Maps(myGameCanvas.getWidth(), myGameCanvas.getHeight(), levels[level][3]);
        tiledBackground = backgroundMap.setBackground(myGameCanvas.getImageStore(), 
                myGameCanvas.getWidth(), myGameCanvas.getHeight(), levels[level][1], levels[level][2]);
        tiledForeground = foregroundMap.setBackground(myGameCanvas.getImageStore(), 
                myGameCanvas.getWidth(), myGameCanvas.getHeight(), levels[level][4], levels[level][5]);
        myGameCanvas.appendLayer(tiledForeground);
        myGameCanvas.appendLayer(tiledBackground);
        
        //Initialize the object maps.
        objectMap = new ObjectMap(levels[level][6]);
        
        myGameCanvas.getPlayer().setVisible(true);
        myGameCanvas.getPlayer().setAtStartPosition(myGameCanvas);
        myGameCanvas.getPlayer().setCanFire(true);
        myGameCanvas.getText().setHUDVisibility(true);
    }
    
    public boolean getEndLevel()
    {
        if (objectMap == null)
        {
            return false;
        }
        else
        {
            return objectMap.endOfMap();
        }
    }
    
    public ObjectMap getObjectMap()
    {
        return objectMap;
    }
    
    public void updateLevel(MyGameCanvas myGameCanvas)
    {
        if (levelInfoCount < levelInfoVisible)
        {
            ++levelInfoCount;
            myGameCanvas.insertLayer(levelInformation, 0);
            myGameCanvas.insertLayer(levelName, 0);
        }
        else if (levelInfoCount == levelInfoVisible)
        {
            if (levelInformation != null)
            {
                myGameCanvas.removeLayer(levelInformation);
                myGameCanvas.removeLayer(levelName);
                initLevel(myGameCanvas);
                levelInformation = null;
                ++levelInfoCount;
            }
        }  
         
        if (backgroundMap != null && foregroundMap != null && objectMap != null)
        {
            //Scroll the background.
            backgroundMap.scrollBackground(tiledBackground);
            foregroundMap.scrollBackground(tiledForeground);
            //Add and remove all enemies and move them.
            objectMap.addRemoveObjects(myGameCanvas); 
        }
    }
    
    public String getText(int t)
    {
        if (t == 0)
        {
            return "THE BEGINNING";
        }
        else if (t == 1)
        {
            return "THE BEGINNING 2";
        }
        else if (t == 2)
        {
            return "THE RETURN";
        }
        else if (t == 3)
        {
            return "THE REVISIT";
        }
        else 
        {
            return "";
        }
    }
    
    /** 
     * Creates a new instance of Levels
     * 0) The scroll speed of the map.
     * 1) The background image of the level.
     *      0 - DesertBackground
     *      1 - GrassBackground
     *      2 - IslandBackground
     *      3 - SnowBackground
     * 2) The map setup.
     *
     * 3) The foreground scroll speed.
     * 4) The foreground image.
     *      4 - Foreground Image.
     * 5) The foreground map setup.
     *
     * 6) ObjectMap setup.
     *
     * 7) The text to display at the beginning of a level.
     */
    public void setLevels()
    {
      levels = new int[][] {
       //0     1     2     3     4     5     6     7     8    9
        {4,    1,    2,    2,    4,    3,    1,    0,    0,   0},           //Level 1
        {4,    2,    1,    2,    4,    3,    2,    1,    0,   0},           //Level 2
        {4,    3,    1,    2,    4,    3,    3,    2,    0,   0},           //Level 3
        {4,    0,    1,    2,    4,    3,    4,    3,    0,   0},           //Level 4
      };
    }

    
}
