package piano;

/**
 * Model of a musical instrument.
 *
 * @author Wincent Balin
 */
public interface InstrumentModel
{
    /**
     * Add listener object to the model.
     *
     * @param listener Listener object to add
     */
    public void addInstrumentModelListener(InstrumentModelListener listener);

    /**
     * Remove listener object from the model.
     *
     * @param listener Listener object to remove
     */
    public void removeInstrumentModelListener(InstrumentModelListener listener);

    /**
     * Process an event.
     *
     * @param e Event that happened
     */
    public void processEvent(InstrumentEvent e);

    /**
     * Notify all listeners about the internal change.
     */
    public void notifyListeners();
}
