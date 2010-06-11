package piano;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.MIDIControl;

/**
 * Player of MIDI events.
 *
 * @author Wincent Balin
 */

class MIDIPlayer
{
    private Player player;

    private MIDIControl control;

    private boolean bankQuerySupported;

    private int[] banks;


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

        // Check whether MIDI control was instantiated
        if(control == null)
            throw new ClassNotFoundException("MIDIControl not available!");

        // Check whether it is possible to query banks
        bankQuerySupported = control.isBankQuerySupported();

        // If it is...
        if(bankQuerySupported)
        {
            // Get all (=false) banks
            banks = control.getBankList(false);
        }
    }

    /**
     * Get list of names of all available MIDI programs.
     *
     * @return List with names
     * @throws MediaException
     */
    String[] getAllProgramNames() throws MediaException
    {
        Vector names = new Vector();
        String[] result;

        // If bank query supported ...
        if(bankQuerySupported)
        {
            // Iterate through banks
            final int banksLength = banks.length;
            for(int i = 0; i < banksLength; i++)
            {
                final int bankNumber = banks[i];
                int[] programs = control.getProgramList(bankNumber);

                // Iterate through programs
                final int programsLength = programs.length;
                for(int j = 0; j < programsLength; j++)
                {
                    final int programNumber = programs[j];

                    // Get name of the program
                    String programName =
                            control.getProgramName(bankNumber, programNumber);

                    // Add name of the program to the vector of names
                    names.addElement(programName);
                }
            }
        }

        // Convert vector to array and return it
        result = new String[names.size()];
        names.copyInto(result);

        return result;
    }
}
