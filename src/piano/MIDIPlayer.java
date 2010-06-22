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

class MIDIPlayer implements NotePlayer
{
    public static final int CHANNEL = 0;

    public static final int VELOCITY = 100;

    public static final int MUTE = 100;

    private Player player;

    private MIDIControl control;

    private boolean bankQuerySupported;

    private int[] banks;

    private boolean available = false;


    /**
     * Constructor.
     *
     * @throws IOException
     * @throws MediaException
     * @throws ClassNotFoundException
     */
    MIDIPlayer() throws IOException, MediaException, ClassNotFoundException
    {
        // Create MIDI player
        player = Manager.createPlayer(Manager.MIDI_DEVICE_LOCATOR);
        player.prefetch();

        // Create MIDI control
        control = (MIDIControl) player.getControl("javax.microedition.media.control.MIDIControl");

        // Check whether MIDI control was instantiated
        if(control == null)
            throw new ClassNotFoundException("MIDIControl not available!");

        // Player is available then
        available = true;

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
     * Indicates whether the player is available.
     *
     * @return Availability flag
     */
    public boolean isAvailable()
    {
        return available;
    }

    /**
     * Get list of names of all available MIDI programs.
     *
     * @return List with names
     * @throws MediaException
     */
    String[] getAllProgramNames() throws MediaException
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
    void setProgram(int index)
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
     * Play the given note.
     *
     * @param octave Octave of the note (first: 0, second: 1, small: -1, etc)
     * @param note Pitch of the note (C: 0, C#: 1, D: 2, etc)
     */
    void noteOn(int octave, int note)
    {
        int midiNote = calculateMIDINote(octave, note);

        control.shortMidiEvent(control.NOTE_ON | CHANNEL, midiNote, VELOCITY);
    }

    /**
     * Stop playing the given note.
     *
     * @param octave Octave of the note (first: 0, second: 1, small: -1, etc)
     * @param note Pitch of the note (C: 0, C#: 1, D: 2, etc)
     */
    void noteOff(int octave, int note)
    {
        int midiNote = calculateMIDINote(octave, note);

        control.shortMidiEvent(control.NOTE_ON | CHANNEL, midiNote, MUTE);
    }

    /**
     * Implementation of InstrumentModelListener.
     */
    public void update()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
