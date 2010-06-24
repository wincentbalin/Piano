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

    public static final String HELP_SECTION_INTRODUCTION_TITLE = "";
    public static final String HELP_SECTION_INTRODUCTION_TEXT =
            "This is a simple music instrument.";
    public static final String HELP_SECTION_PLAYING_TITLE = "Playing keyboard\n";
    public static final String HELP_SECTION_PLAYING_TEXT =
            "Use keypad to play the keyboard. The helper under the keys " +
            "shows buttons on the keypad and corresponding  keys.\n" +
            "It is possible that you will not be able " +
            "to use more than one or two keys at once " +
            "due to the limitations of the keypad.\n";
    public static final String HELP_SECTION_OCTAVE_TITLE = "Changing octave\n";
    public static final String HELP_SECTION_OCTAVE_TEXT =
            "Use arrow keys to change the octave.";
    public static final String HELP_SECTION_COPYRIGHT_TITLE = "Copyright\n";
    public static final String HELP_SECTION_COPYRIGHT_TEXT =
            "Copyright (C) 2010 Wincent Balin";
    public static final String HELP_SECTION_THANKS_TITLE = "Thanks\n";
    public static final String HELP_SECTION_THANKS_TEXT =
            "Many thanks to my beta testers Birgit and Marina.";

    /**
     * Constructor.
     */
    public HelpForm()
    {
        // Create formular with appropriate title
        super(HELP_TITLE);

        // Add text with description of the program
        append(new StringItem(HELP_SECTION_INTRODUCTION_TITLE, HELP_SECTION_INTRODUCTION_TEXT));
        append(new StringItem(HELP_SECTION_PLAYING_TITLE, HELP_SECTION_PLAYING_TEXT));
        append(new StringItem(HELP_SECTION_OCTAVE_TITLE, HELP_SECTION_OCTAVE_TEXT));
        append(new StringItem(HELP_SECTION_COPYRIGHT_TITLE, HELP_SECTION_COPYRIGHT_TEXT));
        append(new StringItem(HELP_SECTION_THANKS_TITLE, HELP_SECTION_THANKS_TEXT));
    }
}
