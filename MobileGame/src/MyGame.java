import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class MyGame extends MIDlet
{
  protected Display display;
  private Image splashLogo;
  private boolean isSplash = true;
  private MainMenuScreen mainMenuScreen;
  private SettingsScreen settingsScreen;
  private HighScoreScreen highScoreScreen;
  private HelpScreen helpScreen;
  private AboutScreen aboutScreen;
  private MyGameCanvas myGameCanvas;

  public MyGame()
  {
  }

  public void startApp()
  {
    display = Display.getDisplay(this);
    mainMenuScreen = new MainMenuScreen(this);
    settingsScreen = new SettingsScreen(this);
    highScoreScreen = new HighScoreScreen(this);
    helpScreen = new HelpScreen(this);
    aboutScreen = new AboutScreen(this);
    if(isSplash)
    {
      isSplash = false;
      try
      {
        splashLogo = Image.createImage("/res/Splash.png");
        new SplashScreen(display, mainMenuScreen, splashLogo,3000);
      }
      catch(Exception ex)
      {
        System.out.println("Exception:: " + ex);
        mainMenuScreenShow();
      }
    }
    else
    {
      mainMenuScreenShow();
    }
  }

  public Display getDisplay()
  {
    return display;
  }

  public void pauseApp()
  {
  }

  public void destroyApp(boolean unconditional)
  {
    System.gc();
    notifyDestroyed();
  }

  private Image createImage(String filename)
  {
    Image image = null;
    try
    {
      image = Image.createImage(filename);
    }
    catch (Exception e)
    {
    }
    return image;
  }

  public void mainMenuScreenShow()
  {
    display.setCurrent(mainMenuScreen);
  }

  public void settingsScreenShow()
  {
    display.setCurrent(settingsScreen);
  }

  public void highScoreScreenShow()
  {
    display.setCurrent(highScoreScreen);
  }

  public void helpScreenShow()
  {
    display.setCurrent(helpScreen);
  }

  public void aboutScreenShow()
  {
    display.setCurrent(aboutScreen);
  }

  public void mainMenuScreenQuit()
  {
    destroyApp(true);
  }

  public void startGame()
  {
    try
    {
      myGameCanvas = new MyGameCanvas(this);
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
    myGameCanvas.start();
    display.setCurrent(myGameCanvas);
  }
}
