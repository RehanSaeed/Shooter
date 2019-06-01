import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Maps
{
  private static final int TILE_WIDTH = 40;
  private static final int TILE_HEIGHT = 40;
  private static final int TILE_NUM_COL = 6;

  private int scrollSpeed;
  private int backgroundScroll;
  private int startPosition;

  /*
  * Maps constructor. Sets the number of columns in the tiled layer.
  *
  * @param screenWidth The screen width.
  * @param screenHeight The screen height.
  * @param scrollSpeed The speed of scrolling the background.
  */
  public Maps(int screenWidth, int screenHeight, int scrollSpeed)
  {
    this.scrollSpeed = scrollSpeed;

  }

  /*
  * sets the tiles in the tiled layer from the map and returns it.
  *
  * @param screenHeight The height of the screen.
  * @param background The name of the background image file.
  * @param mapSetup The map to be used.
  * @return tiledBackground Returns a TiledLayer of the background.
  */
  public TiledLayer setBackground(ImageStore imageStore, int screenWidth,
                                  int screenHeight, int background, int mapSetup)
  {
    TiledLayer tiledBackground = null;

      int map[][] = getMapSetup(mapSetup);
      tiledBackground = new TiledLayer(TILE_NUM_COL, map.length,
                                       getBackground(imageStore, background),
                                       TILE_WIDTH, TILE_WIDTH);

      for (int row = 0; row < map.length; row++)
      {
        for (int column = 0; column < TILE_NUM_COL; column++)
        {
          tiledBackground.setCell(column, row, map[row][column]);
        }
      }
      startPosition = - (TILE_HEIGHT * tiledBackground.getRows()) + screenHeight;
      backgroundScroll = startPosition;
      tiledBackground.setPosition(0, backgroundScroll);
      return tiledBackground;
  }

  /*
  * Gets the background image given a number.
  *
  * @param background The background image wanted.
  * @return tileImages The background image
  */
  private Image getBackground(ImageStore imageStore, int background)
  {
    Image tileImages = null;
    if (background == 0)
    {
      tileImages = imageStore.getImage("DesertBackground");
    }
    else if (background == 1)
    {
      tileImages = imageStore.getImage("GrassBackground");
    }
    else if (background == 2)
    {
      tileImages = imageStore.getImage("IslandBackground");
    }
    else if (background == 3)
    {
      tileImages = imageStore.getImage("SnowBackground");
    }
    else if (background == 4)
    {
      tileImages = imageStore.getImage("Foreground");
    }
    return tileImages;
  }

  /*
  * Scrolls the background down the screen.
  *
  * @param tiledBackground The background in tiled layer format.
  */
  public void scrollBackground(TiledLayer tiledBackground)
  {
    if (backgroundScroll < 0)
    {
      backgroundScroll += scrollSpeed;
      tiledBackground.setPosition(0, backgroundScroll);
    }
    else
    {
      backgroundScroll = startPosition+ (TILE_HEIGHT * 3);
    }
  }

  /*
  * Returns two dimensional array of the tiles to be used in the map.
  * Must have a minimum of 20 items in it and the top and bottom of the map must
  * be the same.
  *
  * @param mapSetup The map to be used.
  * @return map Returns the map.
  */
  private int[][] getMapSetup(int mapSetup)
  {
    int[][] map = null;

    if (mapSetup == 1)
    {
      map = new int[][] {
        {10,10,10,10,10,10},
        {10,10,10,12,10,10},
        {10,10,10,10,10,10},
        {11,11,11,10,10,10},
        {11,11,11,10,10,10},
        {11,10,10,10,10,10},
        {3,10,10,10,10,10},
        {6,10,10,10,10,10},
        {9,10,10,10,10,10},
        {10,10,10,10,10,10},

        {10,10,10,11,10,10},
        {3,10,10,10,10,10},
        {6,10,10,10,10,10},
        {6,10,12,10,1,2},
        {6,10,10,10,4,5},
        {6,10,10,10,7,8},
        {6,10,10,10,11,10},
        {9,10,1,2,3,10},
        {10,10,4,5,6,10},
        {10,10,7,8,9,10},
        {10,10,11,10,10,10},
        {10,10,10,10,10,10},
        {10,10,10,10,10,10},
        {3,10,10,12,10,10},
        {6,10,10,10,10,10},
        {6,10,10,10,1,2},
        {6,10,10,10,4,5},
        {6,10,10,10,7,8},
        {6,10,10,10,10,10},
        {9,10,1,2,3,10},
        {10,10,4,5,6,10},
        {10,10,7,8,9,10},
        {10,10,11,11,10,10},
        {10,10,11,10,10,10},
        {10,10,10,10,10,10},
        {3,10,12,10,10,10},
        {6,10,10,10,10,10},
        {6,10,10,10,1,2},
        {6,10,10,10,4,5},
        {6,10,10,10,7,8},
        {9,10,10,10,10,10},
        {10,10,10,10,10,10},
        {10,10,10,12,10,10},
        {10,10,10,10,10,10},
        {10,10,10,10,10,10},
        {10,10,12,11,10,10},
        {12,10,10,10,10,10},
        {10,10,10,11,10,10},
        {3,10,10,10,10,10},
        {6,10,10,10,10,10},
        {6,10,12,10,1,2},
        {6,10,10,10,4,5},
        {6,10,10,10,7,8},
        {6,10,10,10,11,10},
        {9,10,1,2,3,10},
        {10,10,4,5,6,10},
        {10,10,7,8,9,10},
        {10,10,11,10,10,10},
        {10,10,10,11,10,10},
        {10,10,10,11,10,10},
        {3,10,10,12,10,10},
        {6,10,10,10,10,10},
        {6,10,10,10,1,2},
        {6,10,10,10,4,5},
        {6,10,10,10,7,8},
        {6,10,10,10,10,10},
        {9,10,1,2,3,10},
        {10,10,4,5,6,10},
        {10,10,7,8,9,10},
        {10,10,11,11,10,10},
        {10,10,11,10,10,10},
        {10,10,10,10,10,10},
        {3,10,12,10,10,10},
        {6,10,10,10,10,10},
        {6,10,10,10,1,2},
        {6,10,10,10,4,5},
        {6,10,10,10,7,8},
        {6,10,10,10,10,10},
        {9,10,10,12,10,10},
        {10,10,10,11,10,10},
        {10,10,10,11,10,10},
        {10,10,12,11,10,10},
        {12,11,11,10,10,10},
        {11,11,10,11,11,11},
        {11,11,11,11,11,11},
        {10,11,11,11,10,11},
        {2,2,3,11,10,11},
        {5,5,6,11,10,11},
        {5,5,6,10,10,11},
        {8,8,9,10,10,10},

        {10,10,10,10,10,10},
        {10,10,10,12,10,10},
        {10,10,10,10,10,10},
        {11,11,11,10,10,10},
        {11,11,11,10,10,10},
        {11,10,10,10,10,10},
        {3,10,10,10,10,10},
        {6,10,10,10,10,10},
        {9,10,10,10,10,10},
        {10,10,10,10,10,10},
        };
    }
    else if (mapSetup == 2)
    {
      map = new int[][] {
          {12,10,10,10,10,10},
          {12,10,1,2,3,10},
          {10,10,4,5,6,10},
          {10,10,7,8,9,10},
          {10,10,11,11,10,10},
          {10,10,11,10,10,10},
          {10,10,10,10,10,10},
          {3,10,12,10,10,10},
          {6,10,10,10,10,10},
          {9,10,10,10,10,10},
          
          {12,12,12,10,10,10},
          {12,12,10,10,10,10},
          {12,10,10,10,10,10},
          {3,10,10,10,10,10},
          {6,10,10,10,10,10},
          {6,10,10,11,1,2},
          {6,10,10,10,4,5},
          {6,10,10,10,7,8},
          {6,10,10,10,10,10},
          {9,10,1,2,2,2},
          {10,10,4,5,5,5},
          {10,10,7,8,8,8},
          {10,10,10,10,10,10},
          {10,10,10,10,12,10},
          {10,10,10,12,12,10},
          {2,3,10,10,10,10},
          {5,6,10,10,11,10},
          {5,6,10,10,1,2},
          {5,6,10,10,4,5},
          {8,9,10,10,7,8},
          {12,10,10,10,10,10},
          {12,10,10,10,10,10},
          {10,10,10,10,10,10},
          {10,10,10,10,11,10},
          {10,10,10,10,10,10},
          {12,10,10,10,10,11},
          {10,10,10,10,10,10},
          {3,10,10,10,10,10},
          {6,10,10,10,10,10},
          {6,10,10,10,1,2},
          {6,10,10,10,4,5},
          {6,10,10,10,7,8},
          {6,10,10,10,10,10},
          {9,10,1,2,3,10},
          {10,10,4,5,6,10},
          {10,10,7,8,9,10},
          {10,10,10,10,10,10},
          {10,10,10,10,10,10},
          {10,10,10,10,10,10},
          {3,10,10,10,10,10},
          {6,10,10,10,10,10},
          {6,10,10,10,1,2},
          {6,10,10,10,4,5},
          {6,10,10,10,7,8},
          {6,10,10,10,10,10},
          {9,10,1,2,3,10},
          {10,10,4,5,6,10},
          {10,10,7,8,9,10},
          {10,10,10,10,10,10},
          {12,10,10,10,10,10},
          {10,10,10,10,10,10},
          {3,10,10,10,10,10},
          {6,10,10,10,10,10},
          {6,10,10,10,1,2},
          {6,10,10,10,4,5},
          {6,10,10,10,7,8},
          {6,10,10,10,10,10},
          {9,10,10,10,10,10},
          {10,10,10,10,10,10},
          {10,10,11,10,10,10},
          {10,10,10,10,10,10},
          {12,10,10,10,10,11},
          {10,10,10,11,10,10},
          {3,10,10,10,10,10},
          {6,10,10,10,10,10},
          {6,10,12,10,1,2},
          {6,10,10,10,4,5},
          {6,10,10,10,7,8},
          {6,10,10,10,11,10},
          {9,10,1,2,3,10},
          {10,10,4,5,6,10},
          {10,10,7,8,9,10},
          {10,10,11,10,10,10},
          {10,10,10,10,10,10},
          {10,10,10,10,10,10},
          {3,10,10,12,10,10},
          {6,10,10,10,10,10},
          {6,10,10,10,1,2},
          {6,10,10,10,4,5},
          {9,10,10,10,7,8},

          {12,10,10,10,10,10},
          {12,10,1,2,3,10},
          {10,10,4,5,6,10},
          {10,10,7,8,9,10},
          {10,10,11,11,10,10},
          {10,10,11,10,10,10},
          {10,10,10,10,10,10},
          {3,10,12,10,10,10},
          {6,10,10,10,10,10},
          {9,10,10,10,10,10}
      };
    }
    else if (mapSetup == 3)
    {
      map = new int[][] {
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 2},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 2, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},

          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 2, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},

          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 2},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 2, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},
          {1, 1, 1, 1, 1, 1},

      };
    }
    return map;
  }

}
