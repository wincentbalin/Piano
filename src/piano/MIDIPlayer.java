import java.io.IOException;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.MIDIControl;

/**
 * Player of MIDI events.
 *
 * @author Wincent Balin
 */

class MIDIPlayer implements Runnable
{
    private Player player;

    private MIDIControl control;

    /**
     * Constructor.
     *
     * @throws IOException
     * @throws MediaException
     * @throws ClassNotFoundException
     */
    MIDIPlayer() throws IOException, MediaException, ClassNotFoundException
    {
        // Create MIDI player
        player = Manager.createPlayer(Manager.MIDI_DEVICE_LOCATOR);
        player.prefetch();

        // Create MIDI control
        control = (MIDIControl) player.getControl("javax.microedition.media.control.MIDIControl");

        if(control == null)
            throw new ClassNotFoundException("MIDIControl not available!");


    }

    /**
     * Running thread.
     */
    public void run()
    {
        
    }
}
