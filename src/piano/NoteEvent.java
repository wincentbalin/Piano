package piano;

/**
 * Note event encapsulator.
 *
 * @author Wincent Balin
 */
public class NoteEvent
{
    public static final int NOTE_ON = 90;
    public static final int NOTE_OFF = 91;

    private int code;
    private int note;
    private int velocity;

    /**
     * Constructor.
     *
     * @param code Code of the event (see constants)
     * @param note Number of the note (see MIDI standard)
     * @param velocity Velocity of the note
     */
    public NoteEvent(int code, int note, int velocity)
    {
        this.code = code;
        this.note = note;
        this.velocity = velocity;
    }

    /**
     * Get code of the event.
     *
     * @return COde of the event
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Get note of the event.
     *
     * @return Note of the event
     */
    public int getNote()
    {
        return note;
    }

    /**
     * Get velocity of the note.
     *
     * @return Velocity of the note
     */
    public int getVelocity()
    {
        return velocity;
    }
}
