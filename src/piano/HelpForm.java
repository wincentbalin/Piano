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
    public static final String HELP_TITLE = "Help";
    public static final String HELP_TEXT =
            "This is a simple music instrument.\n" +
            "Use keypad to play it. The helper under the keyboard " +
            "shows corresponding buttons and keys.\n" +
            "It is possible that you will not be able " +
            "to use more than one or two keys at once.\n" +
            "Use arrow keys to change the octave.\n" +
            "\n" +
            "Copyright (C) 2010 Wincent Balin\n" +
            "\n" +
            "Many thanks to my beta testers Birgit and Marina.\n";

    /**
     * Constructor.
     */
    public HelpForm()
    {
        // Create formular with appropriate title
        super(HELP_TITLE);

        // Add text with description of the program
        append(new StringItem(null, HELP_TEXT, StringItem.PLAIN));
    }
}
