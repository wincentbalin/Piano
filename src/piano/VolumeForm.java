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
    private Gauge volumeGauge;

    /**
     * Constructor.
     *
     * @param volume Initial volume
     * @param locale Localization
     */
    public VolumeForm(int volume, LocalizationInterface locale)
    {
        // Create formular with appropriate title
        super(locale.getResource(LocalizationInterface.ID_VOLUME));

        // Instantiate gauge widget
        volumeGauge = new Gauge(getTitle(), true, 127, 0);

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
