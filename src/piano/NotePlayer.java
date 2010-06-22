package piano;

/**
 * Note player interface.
 *
 * @author Wincent Balin
 */
public interface NotePlayer extends InstrumentModelListener
{
    /**
     * Stop the player.
     */
    public void stop();
}
