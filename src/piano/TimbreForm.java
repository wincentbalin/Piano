package piano;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Form;

/**
 * Formular for changing timbre.
 *
 * @author Wincent Balin
 */

public class TimbreForm extends Form implements TimbreInterface
{
    private ChoiceGroup timbreList;

    /**
     * Constructor.
     *
     * @param player Note player with timbre list
     * @param locale Localization
     */
    public TimbreForm(NotePlayer player, LocalizationInterface locale)
    {
        // Set title
        super(locale.getResource(LocalizationInterface.ID_TIMBRES));

        // Create list of timbres
        timbreList = new ChoiceGroup(getTitle(),
                                     ChoiceGroup.EXCLUSIVE,
                                     player.getTimbresList(),
                                     null);

        // Append list of timbres
        append(timbreList);
    }

    /**
     * Implementation of TimbreInterface.
     */
    public int getTimbreIndex()
    {
        return timbreList.getSelectedIndex();
    }

    /**
     * Implementation of TimbreInterface.
     */
    public void setTimbreIndex(int index)
    {
        timbreList.setSelectedIndex(index, true);
    }
}
