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
    public static final String TIMBRE_TITLE = "Timbres";

    private ChoiceGroup timbreList;

    /**
     * Constructor.
     */
    public TimbreForm(NotePlayer player)
    {
        // Set title
        super(TIMBRE_TITLE);

        // Create list of timbres
        timbreList = new ChoiceGroup(TIMBRE_TITLE,
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
