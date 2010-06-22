package piano;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/**
 * Formular which shows information about the application.
 *
 * @author Wincent Balin
 */

class AboutForm extends Form
{
    public static final String aboutTitle = "About this program";
    public static final String aboutText =
            "This is a simple music instrument.\n" +
            "Use keypad to play it. The helper under the keyboard " +
            "shows corresponding buttons and keys.\n";

    /**
     * Constructor.
     */
    AboutForm()
    {
        // Create formular with appropriate title
        super(aboutTitle);

        // Add text with description of the program
        append(new StringItem(null, aboutText, StringItem.PLAIN));
    }
}
