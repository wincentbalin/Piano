import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 * Formular which shows information about the application.
 *
 * @author Wincent Balin
 */

class AboutForm extends Form
{
    public static final String aboutTitle = "About this program...";
    public static final String aboutText = "About this program...";

    AboutForm()
    {
        // Create formular with appropriate title
        super(aboutTitle);

        // Add text field with description of the program
        append(new TextField("About",
                             aboutText,
                             aboutText.length(),
                             TextField.UNEDITABLE));
    }
}
