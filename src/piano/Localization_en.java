package piano;

/**
 * English localization.
 *
 * It is the default one too.
 *
 * @author Wincent Balin
 */

public class Localization_en implements LocalizationInterface
{
    /**
     * Implementation of the LocalizationInterface.
     */
    public String getResource(int id)
    {
        switch(id)
        {
            case ID_COMMAND_EXIT: return "Exit";
            case ID_COMMAND_BACK: return "Back";
            case ID_COMMAND_OK: return "OK";
            case ID_COMMAND_CANCEL: return "Cancel";
            case ID_HELP: return "Help";
            case ID_VOLUME: return "Volume";
            case ID_TIMBRES: return "Timbres";
            case ID_HELP_SECTION_INTRODUCTION_TITLE: return "";
            case ID_HELP_SECTION_INTRODUCTION_TEXT:
                return "This is a simple music instrument.";
            case ID_HELP_SECTION_PLAYING_TITLE: return "Playing keyboard";
            case ID_HELP_SECTION_PLAYING_TEXT:
                return "Use keypad to play the keyboard. " +
                       "The helper under the keys " +
                       "shows buttons on the keypad and corresponding keys.\n" +
                       "It is possible that you will not be able " +
                       "to use more than one or two keys at once " +
                       "due to the limitations of the keypad.\n";
            case ID_HELP_SECTION_OCTAVE_TITLE: return "Changing octave";
            case ID_HELP_SECTION_OCTAVE_TEXT:
                return "Use arrow keys to change the octave.";
            case ID_HELP_SECTION_COPYRIGHT_TITLE: return "Copyright";
            case ID_HELP_SECTION_COPYRIGHT_TEXT:
                return "Copyright (C) 2010 Wincent Balin";
            case ID_HELP_SECTION_THANKS_TITLE: return "Thanks";
            case ID_HELP_SECTION_THANKS_TEXT:
                return "Many thanks to my beta testers Birgit and Marina.";
            case ID_OCTAVE: return "Octave";
            case ID_ERROR: return "Error";
            case ID_ERROR_NO_TONE_GENERATOR:
                return "No tone generator present!";
            default: return "";
        }
    }
}
