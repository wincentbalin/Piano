package piano;

import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.media.MediaException;
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
public class Piano extends MIDlet implements CommandListener, PianoModel, PianoNotes
{
    public static final String COMMAND_HELP = "Help";
    public static final String COMMAND_VOLUME = "Volume";
    public static final String COMMAND_EXIT = "Exit";
    public static final String COMMAND_BACK = "Back";

    private Command help;
    private Command volume;
    private Command exit;
    private Command back;

    private Display display;

    private NotePlayer player;
    private boolean playerHasPrograms;
    private Thread playerThread;

    private PianoCanvas pianoCanvas;
    private Form helpForm;
    private Form volumeForm;
    private VolumeInterface volumeInterface;

    private int octave;
    private boolean[] keyPressed;
    private int velocity;

    private Vector noteEvents;

    private Vector listeners;

    /**
     * Constructor of the MIDlet.
     */
    public Piano()
    {
        // Initialize commands
        help = new Command(COMMAND_HELP, Command.SCREEN, 2);
        volume = new Command(COMMAND_VOLUME, Command.SCREEN, 1);
        exit = new Command(COMMAND_EXIT, Command.EXIT, 0);
        back = new Command(COMMAND_BACK, Command.BACK, 0);

        // Create arrays and vectors
        keyPressed = new boolean[MIDI_KEYS];
        noteEvents = new Vector();
        listeners = new Vector(1);

        // Initialize rest
        playerHasPrograms = true;
    }

    /**
     * Handler of the starting event.
     */
    public void startApp()
    {
        // Initialize piano model
        octave = PIANO_FIRST_OCTAVE;

        for(int i = 0; i < MIDI_KEYS; i++)
            keyPressed[i] = false;

        velocity = 100;

        // Get main display
        display = Display.getDisplay(this);

        // Initialize main canvas
        pianoCanvas = new PianoCanvas(this);

        // Set main canvas
        display.setCurrent(pianoCanvas);

        // Instantiate MIDI player
        try
        {
            player = new MIDIPlayer(this);
        }
        catch(Exception em)
        {
            try
            {
                // Initialize tone player
                player = new TonePlayer(this);
            }
            catch(Exception et)
            {
                // Alert user about absence of tone generator
                Alert alert = new Alert("Error",
                                        "No tone generator present!",
                                        null,
                                        AlertType.ERROR);
                alert.setTimeout(3000);
                display.setCurrent(alert, pianoCanvas);
                System.exit(1);
            }

            // Tone player does not have programs
            playerHasPrograms = false;

            // Alert user about certain disability
            Alert alert = new Alert("Warning",
                                    "You will not be able to change the timbre!",
                                    null,
                                    AlertType.WARNING);
            alert.setTimeout(3000);
            display.setCurrent(alert, pianoCanvas);
        }

        // Start player
        playerThread = new Thread(player);
        playerThread.start();

        // Initialize different displayables
        helpForm = new HelpForm();
        volumeForm = new VolumeForm(velocity);
        volumeInterface = (VolumeInterface) volumeForm;

        // Add commands to appropriate forms
        pianoCanvas.addCommand(exit);
        pianoCanvas.addCommand(volume);
        pianoCanvas.addCommand(help);
        helpForm.addCommand(back);
        volumeForm.addCommand(back);

        // Connect controller, model and view
        pianoCanvas.addInstrumentModel(this);
        addInstrumentModelListener(pianoCanvas);

        // Set command listeners
        pianoCanvas.setCommandListener(this);
        volumeForm.setCommandListener(this);
        helpForm.setCommandListener(this);
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
        try
        {
            // Stop player
            player.stop();
        }
        catch(MediaException e)
        {
            e.printStackTrace();
        }
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
        if(c.equals(exit) && d.equals(pianoCanvas))
        {
            destroyApp(false);
            notifyDestroyed();
        }

        // Handle help command
        else if(c.equals(help) && d.equals(pianoCanvas))
        {
            display.setCurrent(helpForm);
        }

        // Handle volume command
        else if(c.equals(volume) && d.equals(pianoCanvas))
        {
            volumeInterface.setVolume(velocity);
            display.setCurrent(volumeForm);
        }

        // Handle back command in the help form
        else if(c.equals(back) && d.equals(helpForm))
        {
            display.setCurrent(pianoCanvas);
        }

        // Handle back command in the volume form
        else if(c.equals(back) && d.equals(volumeForm))
        {
            display.setCurrent(pianoCanvas);
            velocity = volumeInterface.getVolume();
        }
    }

    /**
     * Implementation of PianoModel.
     */
    public int getOctave()
    {
        return octave;
    }

    /**
     * Implementation of PianoModel.
     *
     * @throws ArrayIndexOutOfBoundsException
     */
    public void setOctave(int octave)
    {
        // Calculate index of the lowest key (C) in the new octave
        int keyIndex = MIDI_MIDDLE_C + octave * OCTAVE_NOTES;

        if(keyIndex > 0 && keyIndex <= MIDI_KEYS - OCTAVE_NOTES)
        {
            this.octave = octave;
        }
        else
            throw new ArrayIndexOutOfBoundsException();
    }
    /**
     * Implementation of PianoModel.
     */
    public boolean[] getCurrentOctaveKeys()
    {
        boolean[] keys = new boolean[OCTAVE_NOTES];

        System.arraycopy(keyPressed, MIDI_MIDDLE_C + octave * OCTAVE_NOTES,
                         keys, NOTE_C,
                         OCTAVE_NOTES);

        return keys;
    }

    /**
     * Implementation of PianoModel.
     */
    public boolean hasMoreNoteEvents()
    {
        return !noteEvents.isEmpty();
    }

    /**
     * Implementation of PianoModel.
     */
    public NoteEvent nextNoteEvent()
    {
        // Get first element and remove it from the queue
        NoteEvent ev = (NoteEvent) noteEvents.firstElement();
        noteEvents.removeElementAt(0);

        return ev;
    }

    /**
     * Implementation of PianoModel.
     */
    public void addInstrumentModelListener(InstrumentModelListener listener)
    {
        listeners.addElement(listener);
    }

    /**
     * Implementation of PianoModel.
     */
    public void removeInstrumentModelListener(InstrumentModelListener listener)
    {
        listeners.removeElement(listener);
    }

    /**
     * Implementation of PianoModel.
     */
    public void processEvent(InstrumentEvent e)
    {
        boolean notify = true;
        int key;
        NoteEvent ev;

        switch(e.getCode())
        {
            case InstrumentEvent.KEY_PRESSED:
                key = MIDI_MIDDLE_C + octave * OCTAVE_NOTES + e.getControl();
                keyPressed[key] = true;
                ev = new NoteEvent(NoteEvent.NOTE_ON, key, velocity);
                noteEvents.addElement(ev);
                break;

            case InstrumentEvent.KEY_RELEASED:
                key = MIDI_MIDDLE_C + octave * OCTAVE_NOTES + e.getControl();
                keyPressed[key] = false;
                ev = new NoteEvent(NoteEvent.NOTE_OFF, key, 0);
                noteEvents.addElement(ev);
                break;

            default:
                notify = false;
                break;
        }

        // Notify listeners if needed
        if(notify)
            notifyListeners();
    }

    /**
     * Implementation of PianoModel.
     */
    public void notifyListeners()
    {
        for(int i = 0; i < listeners.size(); i++)
        {
            ((InstrumentModelListener)listeners.elementAt(i)).update();
        }
    }
}
