package piano;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;

/**
 * Tone player.
 *
 * @author Wincent Balin
 */
public class TonePlayer implements NotePlayer
{
    public static final int TONE_DURATION = 333;

    private PianoModel model;

    /**
     * Constructor.
     *
     * @param model Model to work with
     */
    TonePlayer(PianoModel model)
    {
        this.model = model;
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
                try
                {
                    Manager.playTone(ev.getNote(),
                                     TONE_DURATION,
                                     ev.getVelocity());
                }
                catch(MediaException e)
                {
                }
            }
        }
    }
}
