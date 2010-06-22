package piano;

/**
 * Listener for changes in instrument model.
 *
 * @author Wincent Balin
 */
public interface InstrumentModelListener
{
    /**
     * Update the state of the current object accordingly to the model.
     */
    public void update();
}
