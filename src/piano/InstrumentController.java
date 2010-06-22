package piano;

/**
 * Interface for a controller of a (music) instrument.
 * 
 * @author Wincent Balin
 */
public interface InstrumentController
{
    /**
     * Index of a single model.
     */
    public static final int SINGLE_MODEL = 0;
    
    /**
     * Add instrument model, which is to contol.
     *
     * @param model Piano model to control
     */
    public void addInstrumentModel(PianoModel model);

    /**
     * Remove instrument model, which is not needed anymore.
     *
     * @param model Piano model to remove
     */
    public void removeInstrumentModel(PianoModel model);

    /**
     * Send instrument event to a model.
     *
     * @param e Instrument event
     * @param index Index of the model
     */
    public void sendEvent(InstrumentEvent e, int index);
}
