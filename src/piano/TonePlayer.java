package piano;

import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.ToneControl;
import javax.microedition.media.control.VolumeControl;

/**
 * Tone player.
 *
 * @author Wincent Balin
 */
public class TonePlayer implements NotePlayer, PlayerListener
{
    /**
     * Difference between the MIDI scale and the tone scale.
     */
    public static final int NOTE_DIFFERENCE =
            PianoModel.MIDI_MIDDLE_C - ToneControl.C4;

    private PianoModel model;

    private Player player;
    private boolean playerAvailable;

    private ToneControl control;
    private VolumeControl volume;

    private int originalVolumeLevel;

    /**
     * Constructor.
     *
     * @param model Model to work with
     *
     * @throws IOException
     * @throws MediaException
     */
    TonePlayer(PianoModel model) throws IOException, MediaException
    {
        // Store model
        this.model = model;

        // Create player
        player = Manager.createPlayer(Manager.TONE_DEVICE_LOCATOR);
        player.realize();
        player.addPlayerListener(this);
        playerAvailable = true;

        // Get tone control
        control = (ToneControl) player.getControl("ToneControl");

        // Get volume control
        volume = (VolumeControl) player.getControl("VolumeControl");
        originalVolumeLevel = volume.getLevel();
    }

    /**
     * Implementation of NotePlayer.
     */
    public void run()
    {
        while(true)
        {
            if(model.hasMoreNoteEvents())
            {
                NoteEvent ev = model.nextNoteEvent();

                if(ev.getCode() == NoteEvent.NOTE_ON)
                {
                    // Set appropriate volume
                    volume.setLevel((ev.getVelocity() * 100) / 127);

                    // Create tone sequence
                    byte[] toneSequence =
                    {
                        ToneControl.VERSION, 1,
                        ToneControl.TEMPO, 30,
                        ToneControl.RESOLUTION, 64,
                        (byte) (ev.getNote() + NOTE_DIFFERENCE), 8
                    };

                    // Play sequence
                    control.setSequence(toneSequence);
                    try
                    {
                        player.start();
                    }
                    catch(MediaException e)
                    {
                        e.printStackTrace();
                    }

                    // Wait for player to end
                    while(!playerAvailable)
                    {
                        try
                        {
                            Thread.sleep(10);
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    // Put player into a fresh state
                    player.deallocate();
                }
            }

            // Wait
            try
            {
                Thread.sleep(10);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Implementation of NotePlayer.
     */
    public void stop() throws MediaException
    {
        // Stop player
        player.stop();

        // Restore volume
        volume.setLevel(originalVolumeLevel);
    }

    /**
     * Implementation of PlayerListener.
     */
    public void playerUpdate(Player player, String event, Object eventData)
    {
        if(player == this.player)
        {
            playerAvailable = event.equals(END_OF_MEDIA);
        }
    }
}
