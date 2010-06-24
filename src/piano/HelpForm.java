package piano;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 * Formular which shows information about the application.
 *
 * @author Wincent Balin
 */

public class HelpForm extends Form
{
    /**
     * Constructor.
     *
     * @param locale Localization interface with strings
     */
    public HelpForm(LocalizationInterface locale)
    {
        // Create formular with an appropriate title
        super(locale.getResource(LocalizationInterface.ID_HELP));

        String title;
        String text;

        // Add text with description of the program
        title = locale.getResource(LocalizationInterface.ID_HELP_SECTION_INTRODUCTION_TITLE);
        text = locale.getResource(LocalizationInterface.ID_HELP_SECTION_INTRODUCTION_TEXT);
        append(new StringItem(title,        text + "\n"));
        title = locale.getResource(LocalizationInterface.ID_HELP_SECTION_PLAYING_TITLE);
        text = locale.getResource(LocalizationInterface.ID_HELP_SECTION_PLAYING_TEXT);
        append(new StringItem(title + "\n", text + "\n"));
        title = locale.getResource(LocalizationInterface.ID_HELP_SECTION_OCTAVE_TITLE);
        text = locale.getResource(LocalizationInterface.ID_HELP_SECTION_OCTAVE_TEXT);
        append(new StringItem(title + "\n", text + "\n"));
        title = locale.getResource(LocalizationInterface.ID_HELP_SECTION_COPYRIGHT_TITLE);
        text = locale.getResource(LocalizationInterface.ID_HELP_SECTION_COPYRIGHT_TEXT);
        append(new StringItem(title + "\n", text + "\n"));
        title = locale.getResource(LocalizationInterface.ID_HELP_SECTION_THANKS_TITLE);
        text = locale.getResource(LocalizationInterface.ID_HELP_SECTION_THANKS_TEXT);
        append(new StringItem(title + "\n", text + "\n"));
    }
}
