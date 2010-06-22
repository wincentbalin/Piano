package piano;

import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.ToneControl;
import javax.microedition.media.control.VolumeControl;

/**
 * Tone player.
 *
 * @author Wincent Balin
 */
public class TonePlayer implements NotePlayer
{
    public static final int TONE_DURATION = 333;

    public static final int NOTE_DIFFERENCE =
            PianoModel.MIDI_MIDDLE_C - ToneControl.C4;

    private PianoModel model;

    private Player player;

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

        // Get tone control
        control = (ToneControl) player.getControl("ToneControl");

        // Get volume control
        volume = (VolumeControl) player.getControl("VolumeControl");
        originalVolumeLevel = volume.getLevel();
    }
    /**
     * Implementation of NotePlayer.
     */
    public void update()
    {
        while(model.hasMoreNoteEvents())
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
                    ToneControl.TEMPO, 120,
                    (byte) (ev.getNote() + NOTE_DIFFERENCE), 4
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
/*
                try
                {
                    Manager.playTone(ev.getNote(),
                                     TONE_DURATION,
                                     (ev.getVelocity() * 100) / 127);
                }
                catch(MediaException e)
                {
                }
 */
            }
        }
    }

    /**
     * Implementation of NotePlayer.
     */
    public void stop()
    {
        // Restore volume
        volume.setLevel(originalVolumeLevel);
    }
}
