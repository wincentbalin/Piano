package piano;

/**
 * Event in a musical instrument.
 *
 * @author Wincent Balin
 */
public class InstrumentEvent
{
    /**
     * Nothing happened.
     */
    public static final int NOTHING = 0;

    /**
     * A key was pressed.
     */
    public static final int KEY_PRESSED = 1;

    /**
     * A key was released.
     */
    public static final int KEY_RELEASED = 2;

    /**
     * Keyboard went one octave up.
     */
    public static final int OCTAVE_UP = 3;

    /**
     * Keyboard went one octave down.
     */
    public static final int OCTAVE_DOWN = 4;
    
    private int control;
    private int code;

    /**
     * Constructor.
     *
     * @param control Number of the control used
     * @param code Code of the event (see constants in this class)
     */
    public InstrumentEvent(int control, int code)
    {
        this.control = control;
        this.code = code;
    }

    /**
     * Get number of the control, which issued the event.
     *
     * @return Control number
     */
    public int getControl()
    {
        return control;
    }

    /**
     * Get code of the event.
     *
     * @return Code of the event
     */
    public int getCode()
    {
        return code;
    }
}
