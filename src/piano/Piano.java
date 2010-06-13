package piano;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;


/**
 * Main class of the Piano MIDlet.
 *
 * Originally taken as an idea from the book
 * "Wireless Java Developing with J2ME, Second Edition" by Jonathan Knudsen,
 * published by Apress, ISBN 1590590775
 * 
 * @author Wincent Balin
 * @version 1
 */
public class Piano extends MIDlet implements CommandListener
{
    public static final String COMMAND_ABOUT = "About";
    public static final String COMMAND_EXIT = "Exit";
    public static final String COMMAND_BACK = "Back";

    private Command about;
    private Command exit;
    private Command back;

    private Display display;

    private MIDIPlayer player;

    private Canvas pianoCanvas;
    private Form aboutForm;

    /**
     * Constructor of the MIDlet.
     */
    public Piano()
    {
        // Initialize commands
        about = new Command(COMMAND_ABOUT, Command.SCREEN, 1);
        exit = new Command(COMMAND_EXIT, Command.EXIT, 0);
        back = new Command(COMMAND_BACK, Command.BACK, 0);
    }

    /**
     * Handler of the starting event.
     */
    public void startApp()
    {
        // Get main display
        display = Display.getDisplay(this);

        // Instantiate MIDI player
        try
        {
            player = new MIDIPlayer();
        }
        catch(Exception e)
        {
            Alert alert = new Alert("Error",
                                    "You will not be able to play notes! " +
                                    "This device does not support MIDI!",
                                    null,
                                    AlertType.ERROR);
            display.setCurrent(alert, pianoCanvas);
        }

        // Initialize different displayables
        pianoCanvas = new PianoCanvas(player);
        aboutForm = new AboutForm();

        // Add commands to appropriate forms
        pianoCanvas.addCommand(exit);
        pianoCanvas.addCommand(about);
        aboutForm.addCommand(back);

        // Set command listeners
        pianoCanvas.setCommandListener(this);
        aboutForm.setCommandListener(this);

        // Set main canvas
        display.setCurrent(pianoCanvas);
    }

    /**
     * Handler of the pausing event.
     */
    public void pauseApp()
    {
    }

    /**
     * Handler of the destroying event.
     *
     * @param unconditional Flag to indicate whether destroying is unconditional
     */
    public void destroyApp(boolean unconditional)
    {
    }

    /**
     * Command handler.
     *
     * @param c Command executed
     * @param d Widget the command was executed on
     */
    public void commandAction(Command c, Displayable d)
    {
        // Handle exit command
        if(c == exit && d == pianoCanvas)
        {
            destroyApp(false);
            notifyDestroyed();
        }

        // Handle about command
        else if(c == about && d == pianoCanvas)
        {
            display.setCurrent(aboutForm);
        }

        // Handle back command in the about form
        else if(c == back && d == aboutForm)
        {
            display.setCurrent(pianoCanvas);
        }
    }
}
