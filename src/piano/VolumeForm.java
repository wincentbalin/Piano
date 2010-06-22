package piano;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;

/**
 * Formular for changing volume of notes.
 *
 * @author Wincent Balin
 */

public class VolumeForm extends Form implements VolumeInterface
{
    public static final String volumeTitle = "Volume";
    
    private Gauge volumeGauge;

    /**
     * Constructor.
     *
     * @param volume Initial volume
     */
    VolumeForm(int volume)
    {
        // Create formular with appropriate title
        super(volumeTitle);

        // Instantiate gauge widget
        volumeGauge = new Gauge(volumeTitle, true, 127, 0);

        // Place volume gauge
        append(volumeGauge);
    }

    /**
     * Implementation of VolumeInterface.
     */
    public int getVolume()
    {
        return volumeGauge.getValue();
    }

    /**
     * Implementation of VolumeInterface.
     */
    public void setVolume(int volume)
    {
        volumeGauge.setValue(volume);
    }
}
