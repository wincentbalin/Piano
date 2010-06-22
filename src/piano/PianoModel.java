package piano;

/**
 * Interface for a model of a piano.
 *
 * @author Wincent Balin
 */

public interface PianoModel extends InstrumentModel
{
    // MIDI constants

    /**
     * MIDI value for middle C.
     */
    public static final int MIDI_MIDDLE_C = 60;

    /**
     * MIDI value for the middle A.
     */
    public static final int MIDI_MIDDLE_A = 69;

    /**
     * Amount of keys in the MIDI standard.
     */
    public static final int MIDI_KEYS = 127;

    // Piano constants

    /**
     * Default octave.
     */
    public static final int PIANO_FIRST_OCTAVE = 0;

    /**
     * Get state of the keys in the current octave.
     * 
     * @return Boolean array with state of keys pressed
     */
    public boolean[] getCurrentOctaveKeys();
}
