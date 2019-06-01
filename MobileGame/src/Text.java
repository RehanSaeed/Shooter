import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

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
public class Text
{
    private TiledLayer HUDLives;
    private TiledLayer HUDWeapons;
    private TiledLayer HUDScore;
    
    private boolean visible;
        
  public Text(MyGameCanvas myGameCanvas)
  {
      visible = false;
      initHUD(myGameCanvas);
  }
  
  public void setHUDVisibility(boolean visible)
  {
      this.visible = visible;
  }
  
  public void applyHUDVisibility()
  {
      if (visible == false)
      {
          HUDLives.setVisible(false);
          HUDWeapons.setVisible(false);
          HUDScore.setVisible(false);
      }
      else
      {
          HUDLives.setVisible(true);
          HUDWeapons.setVisible(true);
          HUDScore.setVisible(true);
      }
  }
  
  public void initHUD(MyGameCanvas myGameCanvas)
  {
      if ((HUDLives != null) && (HUDWeapons != null) && (HUDScore != null))
      {
          myGameCanvas.removeLayer(HUDLives);
          myGameCanvas.removeLayer(HUDWeapons);
          myGameCanvas.removeLayer(HUDScore);
      }
      
      HUDLives = setHUDLives(myGameCanvas);
      HUDWeapons = setHUDWeapons(myGameCanvas);
      HUDScore = setHUDScore(myGameCanvas);
      applyHUDVisibility();
      myGameCanvas.insertLayer(HUDLives, 0);
      myGameCanvas.insertLayer(HUDWeapons, 0);
      myGameCanvas.insertLayer(HUDScore, 0);
      
      
  }

  public TiledLayer setHUDLives(MyGameCanvas myGameCanvas)
  {
    Image image = myGameCanvas.getImageStore().getImage("Text");
    TiledLayer tiledLayer = new TiledLayer(1, 10, image, image.getWidth()/10, image.getHeight()/4);
    for (int y = (tiledLayer.getRows() - 1); y > (tiledLayer.getRows() - myGameCanvas.getPlayer().getLives() - 1); --y)
    {
      tiledLayer.setCell(0, y, 39);
    }
    tiledLayer.setPosition(0, myGameCanvas.getHeight() - tiledLayer.getHeight());
    return tiledLayer;
  }

  public TiledLayer setHUDWeapons(MyGameCanvas myGameCanvas)
  {
    Image image = myGameCanvas.getImageStore().getImage("Text");
    TiledLayer tiledLayer = new TiledLayer(1, 10, image, image.getWidth()/10, image.getHeight()/4);
    for (int y = (tiledLayer.getRows() - 1); y > (tiledLayer.getRows() - myGameCanvas.getPlayer().getUltimateWeapon() - 1); --y)
    {
      tiledLayer.setCell(0, y, 40);
    }
    tiledLayer.setPosition(myGameCanvas.getWidth() - tiledLayer.getWidth(), myGameCanvas.getHeight() - tiledLayer.getHeight());
    return tiledLayer;
  }

  public TiledLayer setHUDScore(MyGameCanvas myGameCanvas)
  {
    Image image = myGameCanvas.getImageStore().getImage("Text");
    TiledLayer tiledLayer = new TiledLayer(12, 1, image, image.getWidth()/10, image.getHeight()/4);
    tiledLayer.setCell(0, 0, 29);
    tiledLayer.setCell(1, 0, 13);
    tiledLayer.setCell(2, 0, 25);
    tiledLayer.setCell(3, 0, 28);
    tiledLayer.setCell(4, 0, 15);

    String score = myGameCanvas.getPlayer().getScore() + "";
    if (score.length() == 1)
        score = "00000" + score;
    else if (score.length() == 2)
        score = "0000" + score;
    else if (score.length() == 3)
        score = "000" + score;
    else if (score.length() == 4)
        score = "00" + score;
    else if (score.length() == 5)
        score = "0" + score;
    int cellCount = 6;

    for (int x = 0; x < score.length(); ++x)
    {
      String temp = score.substring(x, x+1);
      if (temp.equals("0"))
      {
        tiledLayer.setCell(cellCount, 0, 1);
      }
      else if (temp.equals("1"))
      {
        tiledLayer.setCell(cellCount, 0, 2);
      }
      else if (temp.equals("2"))
      {
        tiledLayer.setCell(cellCount, 0, 3);
      }
      else if (temp.equals("3"))
      {
        tiledLayer.setCell(cellCount, 0, 4);
      }
      else if (temp.equals("4"))
      {
        tiledLayer.setCell(cellCount, 0, 5);
      }
      else if (temp.equals("5"))
      {
        tiledLayer.setCell(cellCount, 0, 6);
      }
      else if (temp.equals("6"))
      {
        tiledLayer.setCell(cellCount, 0, 7);
      }
      else if (temp.equals("7"))
      {
        tiledLayer.setCell(cellCount, 0, 8);
      }
      else if (temp.equals("8"))
      {
        tiledLayer.setCell(cellCount, 0, 9);
      }
      else if (temp.equals("9"))
      {
        tiledLayer.setCell(cellCount, 0, 10);
      }
      ++cellCount;
    }
    tiledLayer.setPosition((myGameCanvas.getWidth()/2) - (tiledLayer.getWidth()/2), 0);
    return tiledLayer;
  }
  
    public TiledLayer createText(MyGameCanvas myGameCanvas, String text, int X, int Y)
    {
        Image image = myGameCanvas.getImageStore().getImage("Text");
        TiledLayer tiledLayer = new TiledLayer(text.length(), 1, image, image.getWidth()/10, image.getHeight()/4);
        int cellCount = 0;
        for (int x = 0; x < text.length(); ++x)
        {
          String temp = text.substring(x, x+1);
          if (temp.equals("0"))
          {
            tiledLayer.setCell(cellCount, 0, 1);
          }
          else if (temp.equals("1"))
          {
            tiledLayer.setCell(cellCount, 0, 2);
          }
          else if (temp.equals("2"))
          {
            tiledLayer.setCell(cellCount, 0, 3);
          }
          else if (temp.equals("3"))
          {
            tiledLayer.setCell(cellCount, 0, 4);
          }
          else if (temp.equals("4"))
          {
            tiledLayer.setCell(cellCount, 0, 5);
          }
          else if (temp.equals("5"))
          {
            tiledLayer.setCell(cellCount, 0, 6);
          }
          else if (temp.equals("6"))
          {
            tiledLayer.setCell(cellCount, 0, 7);
          }
          else if (temp.equals("7"))
          {
            tiledLayer.setCell(cellCount, 0, 8);
          }
          else if (temp.equals("8"))
          {
            tiledLayer.setCell(cellCount, 0, 9);
          }
          else if (temp.equals("9"))
          {
            tiledLayer.setCell(cellCount, 0, 10);
          }
          else if (temp.equals("A"))
          {
            tiledLayer.setCell(cellCount, 0, 11);
          }
          else if (temp.equals("B"))
          {
            tiledLayer.setCell(cellCount, 0, 12);
          }
          else if (temp.equals("C"))
          {
            tiledLayer.setCell(cellCount, 0, 13);
          }
          else if (temp.equals("D"))
          {
            tiledLayer.setCell(cellCount, 0, 14);
          }
          else if (temp.equals("E"))
          {
            tiledLayer.setCell(cellCount, 0, 15);
          }
          else if (temp.equals("F"))
          {
            tiledLayer.setCell(cellCount, 0, 16);
          }
          else if (temp.equals("G"))
          {
            tiledLayer.setCell(cellCount, 0, 17);
          }
          else if (temp.equals("H"))
          {
            tiledLayer.setCell(cellCount, 0, 18);
          }
          else if (temp.equals("I"))
          {
            tiledLayer.setCell(cellCount, 0, 19);
          }
          else if (temp.equals("J"))
          {
            tiledLayer.setCell(cellCount, 0, 20);
          }
          else if (temp.equals("K"))
          {
            tiledLayer.setCell(cellCount, 0, 21);
          }
          else if (temp.equals("L"))
          {
            tiledLayer.setCell(cellCount, 0, 22);
          }
          else if (temp.equals("M"))
          {
            tiledLayer.setCell(cellCount, 0, 23);
          }
          else if (temp.equals("N"))
          {
            tiledLayer.setCell(cellCount, 0, 24);
          }
          else if (temp.equals("O"))
          {
            tiledLayer.setCell(cellCount, 0, 25);
          }
          else if (temp.equals("P"))
          {
            tiledLayer.setCell(cellCount, 0, 26);
          }
          else if (temp.equals("Q"))
          {
            tiledLayer.setCell(cellCount, 0, 27);
          }
          else if (temp.equals("R"))
          {
            tiledLayer.setCell(cellCount, 0, 28);
          }
          else if (temp.equals("S"))
          {
            tiledLayer.setCell(cellCount, 0, 29);
          }
          else if (temp.equals("T"))
          {
            tiledLayer.setCell(cellCount, 0, 30);
          }
          else if (temp.equals("U"))
          {
            tiledLayer.setCell(cellCount, 0, 31);
          }
          else if (temp.equals("V"))
          {
            tiledLayer.setCell(cellCount, 0, 32);
          }
          else if (temp.equals("W"))
          {
            tiledLayer.setCell(cellCount, 0, 33);
          }
          else if (temp.equals("X"))
          {
            tiledLayer.setCell(cellCount, 0, 34);
          }
          else if (temp.equals("Y"))
          {
            tiledLayer.setCell(cellCount, 0, 35);
          }
          else if (temp.equals("Z"))
          {
            tiledLayer.setCell(cellCount, 0, 36);
          }
          else if (temp.equals("."))
          {
            tiledLayer.setCell(cellCount, 0, 37);
          }
          else if (temp.equals("-"))
          {
            tiledLayer.setCell(cellCount, 0, 38);
          }
          ++cellCount;
        }
        tiledLayer.setPosition(X, Y);
        return tiledLayer;
    }
}
