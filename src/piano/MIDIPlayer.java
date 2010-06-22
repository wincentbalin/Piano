package piano;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.MIDIControl;

/**
 * Player of MIDI events.
 *
 * @author Wincent Balin
 */

public class MIDIPlayer implements NotePlayer
{
    public static final int CHANNEL = 0;

    public static final int MUTE = 0;

    private PianoModel model;

    private Player player;

    private MIDIControl control;

    private boolean bankQuerySupported;

    private int[] banks;


    /**
     * Constructor.
     *
     * @param model Piano model
     *
     * @throws IOException
     * @throws MediaException
     * @throws ClassNotFoundException
     */
    public MIDIPlayer(PianoModel model) throws IOException, MediaException, ClassNotFoundException
    {
        // Store model
        this.model = model;

        // Create MIDI player
        player = Manager.createPlayer(Manager.MIDI_DEVICE_LOCATOR);
        player.prefetch();

        // Create MIDI control
        control = (MIDIControl) player.getControl("javax.microedition.media.control.MIDIControl");

        // Check whether MIDI control was instantiated
        if(control == null)
            throw new ClassNotFoundException("MIDIControl not available!");

        // Check whether it is possible to query banks
        bankQuerySupported = control.isBankQuerySupported();

        // If it is...
        if(bankQuerySupported)
        {
            // Get all (=false) available banks
            banks = control.getBankList(false);
        }
    }

    /**
     * Get list of names of all available MIDI programs.
     *
     * @return List with names
     * @throws MediaException
     */
    public String[] getAllProgramNames() throws MediaException
    {
        Vector names = new Vector();
        String[] result;

        // If bank query supported ...
        if(bankQuerySupported)
        {
            // Iterate through banks
            final int banksLength = banks.length;
            for(int i = 0; i < banksLength; i++)
            {
                final int bankNumber = banks[i];
                int[] programs = control.getProgramList(bankNumber);

                // Iterate through programs
                final int programsLength = programs.length;
                for(int j = 0; j < programsLength; j++)
                {
                    final int programNumber = programs[j];

                    // Get name of the program
                    String programName =
                            control.getProgramName(bankNumber, programNumber);

                    // Add name of the program to the vector of names
                    names.addElement(programName);
                }
            }
        }

        // Convert vector to array and return it
        result = new String[names.size()];
        names.copyInto(result);

        return result;
    }

    /**
     * Set program on the first channel.
     *
     * @param index Index in the previously created list of program names
     */
    public void setProgram(int index)
    {
        int bank = banks[index / 127];
        int program = index % 127;

        // Use first channel
        control.setProgram(CHANNEL, bank, program);
    }

    /**
     * Convert octave/note combination to MIDI note number.
     *
     * @param octave Octave of the note (first: 0, second: 1, small: -1, etc)
     * @param note Pitch of the note (C: 0, C#: 1, D: 2, etc)
     * @return MIDI note number
     */
    private int calculateMIDINote(int octave, int note)
    {
        return 60 + octave * 12 + note;
    }

    /**
     * Implementation of NotePlayer.
     */
    public void stop()
    {
    }

    /**
     * Implementation of Runnable.
     */
    public void run()
    {
        while(true)
        {
            while(model.hasMoreNoteEvents())
            {
                NoteEvent ev = model.nextNoteEvent();

                switch(ev.getCode())
                {
                    case NoteEvent.NOTE_ON:
                        control.shortMidiEvent(MIDIControl.NOTE_ON | CHANNEL,
                                               ev.getNote(),
                                               ev.getVelocity());
                        break;

                    case NoteEvent.NOTE_OFF:
                        control.shortMidiEvent(MIDIControl.NOTE_ON | CHANNEL,
                                               ev.getNote(),
                                               MUTE);
                        break;
                }
            }

            try
            {
                Thread.sleep(10);
            }
            catch(InterruptedException e)
            {
            }
        }
    }
}
