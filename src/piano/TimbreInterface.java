package piano;

/**
 * Timbre interface.
 * 
 * @author Wincent Balin
 */
public interface TimbreInterface
{
    /**
     * Get index of chosen timbre.
     *
     * @return Index in the list of timbres
     */
    public int getTimbreIndex();

    /**
     * Set index of the current timbre.
     *
     * @param index Index in the list of timbres
     */
    public void setTimbreIndex(int index);
}
