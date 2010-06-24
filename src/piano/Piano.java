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
import javax.microedition.rms.RecordStoreException;


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
    private LocalizationInterface locale;

    private String labelCommandHelp;
    private String labelCommandVolume;
    private String labelCommandTimbres;
    private String labelCommandExit;
    private String labelCommandBack;
    private String labelCommandOK;
    private String labelCommandCancel;

    private Command help;
    private Command volume;
    private Command timbres;
    private Command exit;
    private Command back;
    private Command ok;
    private Command cancel;

    private Display display;

    private NotePlayer player;
    private Thread playerThread;

    private PianoCanvas pianoCanvas;
    private Form helpForm;
    private Form volumeForm;
    private VolumeInterface volumeInterface;
    private Form timbreForm;
    private TimbreInterface timbreInterface;

    private Preferences preferences;

    private int octave;
    private boolean[] keyPressed;
    private int velocity;
    private int timbre;

    private Vector noteEvents;

    private Vector listeners;

    /**
     * Constructor of the MIDlet.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Piano() throws IllegalAccessException, InstantiationException
    {
        // Initialize localization
        String localizationName = "piano.Localization_" +
                                  System.getProperty("microedition.locale");
        Class localizationClass = null;

        try
        {
            localizationClass = Class.forName(localizationName);
        }
        catch(ClassNotFoundException e)
        {
            try
            {
                localizationClass = Class.forName("piano.Localization_en");
            }
            catch(ClassNotFoundException e2)
            {
                e2.printStackTrace();
            }
        }

        locale = (LocalizationInterface) localizationClass.newInstance();

        // Get localized command labels
        labelCommandHelp = locale.getResource(LocalizationInterface.ID_HELP);
        labelCommandVolume = locale.getResource(LocalizationInterface.ID_VOLUME);
        labelCommandTimbres = locale.getResource(LocalizationInterface.ID_TIMBRES);
        labelCommandExit = locale.getResource(LocalizationInterface.ID_COMMAND_EXIT);
        labelCommandBack = locale.getResource(LocalizationInterface.ID_COMMAND_BACK);
        labelCommandOK = locale.getResource(LocalizationInterface.ID_COMMAND_OK);
        labelCommandCancel = locale.getResource(LocalizationInterface.ID_COMMAND_CANCEL);

        // Initialize commands
        exit = new Command(labelCommandExit, Command.SCREEN, 4);
        volume = new Command(labelCommandVolume, Command.SCREEN, 3);
        timbres = new Command(labelCommandTimbres, Command.SCREEN, 2);
        help = new Command(labelCommandHelp, Command.SCREEN, 1);
        back = new Command(labelCommandBack, Command.BACK, 0);
        ok = new Command(labelCommandOK, Command.OK, 0);
        cancel = new Command(labelCommandCancel, Command.CANCEL, 1);

        // Create arrays and vectors
        keyPressed = new boolean[MIDI_KEYS];
        noteEvents = new Vector();
        listeners = new Vector(1);
    }

    /**
     * Handler of the starting event.
     */
    public void startApp()
    {
        // Load preferences
        try
        {
            preferences = new Preferences("Piano");
        }
        catch(RecordStoreException e)
        {
            e.printStackTrace();
        }

        // Initialize piano model
        octave = preferences.getInt("Octave");

        for(int i = 0; i < MIDI_KEYS; i++)
            keyPressed[i] = false;

        velocity = preferences.getInt("Velocity");

        timbre = preferences.getInt("Timbre");

        // Get main display
        display = Display.getDisplay(this);

        // Initialize main canvas
        pianoCanvas = new PianoCanvas(this, locale);

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
                Alert alert = new Alert(locale.getResource(LocalizationInterface.ID_ERROR),
                                        locale.getResource(LocalizationInterface.ID_ERROR_NO_TONE_GENERATOR),
                                        null,
                                        AlertType.ERROR);
                alert.setTimeout(3000);
                display.setCurrent(alert, pianoCanvas);
                System.exit(1);
            }
        }

        // Start player
        playerThread = new Thread(player);
        playerThread.start();

        // Initialize different displayables
        helpForm = new HelpForm(locale);
        volumeForm = new VolumeForm(velocity, locale);
        volumeInterface = (VolumeInterface) volumeForm;
        if(player.timbresAvailable())
        {
            timbreForm = new TimbreForm(player, locale);
            timbreInterface = (TimbreInterface) timbreForm;
        }

        // Add commands to appropriate forms
        pianoCanvas.addCommand(exit);
        pianoCanvas.addCommand(volume);
        pianoCanvas.addCommand(help);
        helpForm.addCommand(back);
        volumeForm.addCommand(ok);
        volumeForm.addCommand(cancel);
        if(timbreForm != null)
        {
            pianoCanvas.addCommand(timbres);
            timbreForm.addCommand(ok);
            timbreForm.addCommand(cancel);
        }

        // Connect controller, model and view
        pianoCanvas.addInstrumentModel(this);
        addInstrumentModelListener(pianoCanvas);

        // Set command listeners
        pianoCanvas.setCommandListener(this);
        if(timbreForm != null)
            timbreForm.setCommandListener(this);
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

        // Store preferences
        preferences.set("Octave", octave);
        preferences.set("Velocity", velocity);
        preferences.set("Timbre", timbre);

        // Save preferences
        try
        {
            preferences.save();
        }
        catch(RecordStoreException e)
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
        // Handle piano canvas commands
        if(d.equals(pianoCanvas))
        {
            // Handle exit command
            if(c.equals(exit))
            {
                destroyApp(false);
                notifyDestroyed();
            }

            // Handle help command
            else if(c.equals(help))
            {
                display.setCurrent(helpForm);
            }

            // Handle volume command
            else if(c.equals(volume))
            {
                volumeInterface.setVolume(velocity);
                display.setCurrent(volumeForm);
            }

            // Handle timbres command
            else if(c.equals(timbres))
            {
                timbreInterface.setTimbreIndex(timbre);
                display.setCurrent(timbreForm);
            }
        }

        // Handle help form commands
        else if(d.equals(helpForm))
        {
            // Handle back command
            if(c.equals(back))
            {
                display.setCurrent(pianoCanvas);
            }
        }

        // Handle volume form commands
        else if(d.equals(volumeForm))
        {
            // Handle ok command
            if(c.equals(ok))
            {
                display.setCurrent(pianoCanvas);
                velocity = volumeInterface.getVolume();
            }

            // Handle cancel command
            else if(c.equals(cancel))
            {
                display.setCurrent(pianoCanvas);
            }
        }

        // Handle timbre form commands
        else if(d.equals(timbreForm))
        {
            // Handle ok command
            if(c.equals(ok))
            {
                display.setCurrent(pianoCanvas);
                timbre = timbreInterface.getTimbreIndex();
                player.setTimbre(timbre);
            }

            // Handle cancel command
            else if(c.equals(cancel))
            {
                display.setCurrent(pianoCanvas);
            }
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

            case InstrumentEvent.OCTAVE_UP:
                setOctave(getOctave() + 1);
                break;

            case InstrumentEvent.OCTAVE_DOWN:
                setOctave(getOctave() - 1);
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
