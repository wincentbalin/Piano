package piano;

/**
 * Localization interface.
 * 
 * @author wincent
 */

public interface LocalizationInterface
{
    /**
     * Resource ID of label of the EXIT command.
     */
    public static final int ID_COMMAND_EXIT = 100;

    /**
     * Resource ID of label of the BACK command.
     */
    public static final int ID_COMMAND_BACK = 101;

    /**
     * Resource ID of label of the OK command.
     */
    public static final int ID_COMMAND_OK = 102;

    /**
     * Resource ID of label of the CANCEL command.
     */
    public static final int ID_COMMAND_CANCEL = 103;


    /**
     * Resource ID of the Help label.
     */
    public static final int ID_HELP = 200;

    /**
     * Resource ID of the Volume label.
     */
    public static final int ID_VOLUME = 201;

    /**
     * Resource ID of the Timbres label.
     */
    public static final int ID_TIMBRES = 202;


    /**
     * Resource ID of title of the Introduction help section.
     */
    public static final int ID_HELP_SECTION_INTRODUCTION_TITLE = 300;

    /**
     * Resource ID of text of the Introduction help section.
     */
    public static final int ID_HELP_SECTION_INTRODUCTION_TEXT = 301;

    /**
     * Resource ID of title of the Playing help section.
     */
    public static final int ID_HELP_SECTION_PLAYING_TITLE = 302;

    /**
     * Resource ID of text of the Playing help section.
     */
    public static final int ID_HELP_SECTION_PLAYING_TEXT = 303;

    /**
     * Resource ID of title of the Octave help section.
     */
    public static final int ID_HELP_SECTION_OCTAVE_TITLE = 304;

    /**
     * Resource ID of text of the Octave help section.
     */
    public static final int ID_HELP_SECTION_OCTAVE_TEXT = 305;

    /**
     * Resource ID of title of the Copyright help section.
     */
    public static final int ID_HELP_SECTION_COPYRIGHT_TITLE = 306;

    /**
     * Resource ID of text of the Copyright help section.
     */
    public static final int ID_HELP_SECTION_COPYRIGHT_TEXT = 307;

    /**
     * Resource ID of title of the Thanks help section.
     */
    public static final int ID_HELP_SECTION_THANKS_TITLE = 308;

    /**
     * Resource ID of text of the Thanks help section.
     */
    public static final int ID_HELP_SECTION_THANKS_TEXT = 309;


    /**
     * Resource ID of the Octave label.
     */
    public static final int ID_OCTAVE = 400;


    /**
     * Resource ID of title of an Error alert.
     */
    public static final int ID_ERROR = 500;

    /**
     * Resource ID of text in the "No tone generator" error dialog.
     */
    public static final int ID_ERROR_NO_TONE_GENERATOR = 501;


    /**
     * Get resource by ID.
     *
     * @param ID of the resource
     *
     * @return Resource or empty string if the ID was unknown
     */
    public String getResource(int id);
}
