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

    /**
     * General MIDI standard program names.
     * Source: http://en.wikipedia.org/wiki/General_MIDI
     */
    public String[] GENERAL_MIDI_TIMBRES =
    {
        // Piano
        "Acoustic Grand Piano",
        "Bright Acoustic Piano",
        "Electric Grand Piano",
        "Honky-tonk Piano",
        "Electric Piano 1",
        "Electric Piano 2",
        "Harpsichord",
        "Clavinet",
        // Chromatic Percussion
        "Celesta",
        "Glockenspiel",
        "Music Box",
        "Vibraphone",
        "Marimba",
        "Xylophone",
        "Tubular Bells",
        "Dulcimer",
        // Organ
        "Organ",
        "Percussive Organ",
        "Rock Organ",
        "Church Organ",
        "Reed Organ",
        "Accordion",
        "Harmonica",
        "Tango Accordion",
        // Guitar
        "Acoustic Guitar (nylon)",
        "Acoustic Guitar (steel)",
        "Electric Guitar (jazz)",
        "Electric Guitar (clean)",
        "Electric Guitar (muted)",
        "Overdriven Guitar",
        "Distortion Guitar",
        "Guitar harmonics",
        // Bass
        "Acoustic Bass",
        "Electric Bass (finger)",
        "Electric Bass (pick)",
        "Fretless Bass",
        "Slap Bass 1",
        "Slap Bass 2",
        "Synth Bass 1",
        "Synth Bass 2",
        // Strings
        "Violin",
        "Viola",
        "Cello",
        "Contrabass",
        "Tremolo Strings",
        "Pizzicato Strings",
        "Orchestral Harp",
        "Timpani",
        // Ensemble
        "String Ensemble 1",
        "String Ensemble 2",
        "Synth Strings 1",
        "Synth Strings 2",
        "Voice Aahs",
        "Voice Oohs",
        "Synth Voice",
        "Orchestra Hit",
        // Brass
        "Trumpet",
        "Trombone",
        "Tuba",
        "Muted Trumpet",
        "French horn",
        "Brass Section",
        "Synth Brass 1",
        "Synth Brass 2",
        // Reed
        "Soprano Sax",
        "Alto Sax",
        "Tenor Sax",
        "Baritone Sax",
        "Oboe",
        "English Horn",
        "Bassoon",
        "Clarinet",
        // Pipe
        "Piccolo",
        "Flute",
        "Recorder",
        "Pan Flute",
        "Blown Bottle",
        "Shakuhachi",
        "Whistle",
        "Ocarina",
        // Synth Lead
        "Lead 1 (square)",
        "Lead 2 (sawtooth)",
        "Lead 3 (calliope)",
        "Lead 4 (chiff)",
        "Lead 5 (charang)",
        "Lead 6 (voice)",
        "Lead 7 (fifths)",
        "Lead 8 (bass + lead)",
        // Synth Pad
        "Pad 1 (new age)",
        "Pad 2 (warm)",
        "Pad 3 (polysynth)",
        "Pad 4 (choir)",
        "Pad 5 (bowed)",
        "Pad 6 (metallic)",
        "Pad 7 (halo)",
        "Pad 8 (sweep)",
        // Synth Effects
        "FX 1 (rain)",
        "FX 2 (soundtrack)",
        "FX 3 (crystal)",
        "FX 4 (atmosphere)",
        "FX 5 (brightness)",
        "FX 6 (goblins)",
        "FX 7 (echoes)",
        "FX 8 (sci-fi)",
        //  Ethnic
        "Sitar",
        "Banjo",
        "Shamisen",
        "Koto",
        "Kalimba",
        "Bagpipe",
        "Fiddle",
        "Shanai",
        // Percussive
        "Tinkle Bell",
        "Agogo Bells",
        "Steel Drums",
        "Woodblock",
        "Taiko Drum",
        "Melodic Tom",
        "Synth Drum",
        "Reverse Cymbal",
        // Sound effects
        "Guitar Fret Noise",
        "Breath Noise",
        "Seashore",
        "Bird Tweet",
        "Telephone Ring",
        "Helicopter",
        "Applause",
        "Gunshot"
    };

    private ChoiceGroup timbreList;

    /**
     * Constructor.
     */
    public TimbreForm(NotePlayer player)
    {
        // Set title
        super(TIMBRE_TITLE);

        // Get names of timbres
        String[] timbreNames = player.timbresAvailable() ?
                                    player.getTimbresList() :
                                    GENERAL_MIDI_TIMBRES;

        // Create list of timbres
        timbreList = new ChoiceGroup(TIMBRE_TITLE,
                                     ChoiceGroup.EXCLUSIVE,
                                     timbreNames,
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
