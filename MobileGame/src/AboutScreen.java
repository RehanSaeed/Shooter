import javax.microedition.lcdui.*;

public class AboutScreen extends Form implements CommandListener
{
  private MyGame midlet;
  private Command backCommand = new Command("Back", Command.BACK, 1);
  public AboutScreen (MyGame midlet)
  {
    super("About");
    this.midlet = midlet;
    StringItem stringItem = new StringItem(null,"MyGame\nVersion 1.0.0\nBy Muhammad Rehan Saeed");
    append(stringItem);
    addCommand(backCommand);
    setCommandListener(this);
  }

  public void commandAction(Command c, Displayable d)
  {
    if (c == backCommand)
    {
      midlet.mainMenuScreenShow();
      return;
    }
  }
}
