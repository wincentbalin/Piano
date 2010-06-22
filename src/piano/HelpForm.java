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
    public static final String helpTitle = "Help";
    public static final String helpText =
            "This is a simple music instrument.\n" +
            "Use keypad to play it. The helper under the keyboard " +
            "shows corresponding buttons and keys.\n" +
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
        super(helpTitle);

        // Add text with description of the program
        append(new StringItem(null, helpText, StringItem.PLAIN));
    }
}
