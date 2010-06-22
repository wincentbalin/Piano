package piano;

import javax.microedition.media.MediaException;

/**
 * Note player interface.
 *
 * @author Wincent Balin
 */
public interface NotePlayer extends Runnable
{
    /**
     * Stop the player.
     *
     * @throws MediaException
     */
    public void stop() throws MediaException;
}
