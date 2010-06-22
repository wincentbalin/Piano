package piano;

/**
 * Volume interface.
 *
 * @author Wincent Balin
 */
public interface VolumeInterface
{
    /**
     * Get current volume.
     *
     * @return Current volume value
     */
    public int getVolume();

    /**
     * Set volume.
     *
     * @param volume New volume value
     */
    public void setVolume(int volume);
}
