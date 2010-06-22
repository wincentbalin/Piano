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

    /**
     * Query whether it is possible to change a timbre.
     *
     * @return Availability of timbres
     */
    public boolean timbresAvailable();

    /**
     * Get list of timbres.
     *
     * @return Array with names of timbres
     */
    public String[] getTimbresList();

    /**
     * Set timbre.
     *
     * @param index Index of the timbre in the previously fetched list
     */
    public void setTimbre(int index);
}
