package piano;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * Canvas of the Piano MIDlet
 *
 * @author Wincent Balin
 * @author Pitt Werhan
 */

public class PianoCanvas extends Canvas implements PianoView, InstrumentController
{
    /**
     * Constant for black color.
     */
    public static final int BLACK_COLOR = 0x00000000;

    /**
     * Constant for white color.
     */
    public static final int WHITE_COLOR = 0x00FFFFFF;

    /**
     * Color of the contour.
     */
    public static final int CONTOUR_COLOR = 0x00000000;

    /**
     * Margin between the elements of the the canvas and it's border.
     */
    public static final int MARGIN = 10;

    /**
     * Distance between the keyboard and the note marks of the helper.
     */
    public static final int KEYS_NOTES_DISTANCE = 10;

    /**
     * Dimensions of the keys.
     */
    private int fontHeight;
    private int whiteKeyWidth;
    private int whiteKeyHeight;
    private int blackKeyWidth;
    private int blackKeyHeight;
    private int blackKeyNarrowWidth;
    private int whiteKeyNarrowWidth;
    private int whiteKeyLesserHeight;

    /**
     * The current key pressed by the pointer and needed in pointerDragged().
     */
    private int currentPressedKey;
    
    /**
     * Key positions for pointer usage.
     */
    private int[][] keyPositions;

    /**
     * Octave controls positions for pointer usage.
     */
    private int[][] octavePositions;

    /**
     * Lookup table for the color of the keys.
     */
    public static final boolean[] WHITE_KEY =
    {
        true,
        false,
        true,
        false,
        true,
        true,
        false,
        true,
        false,
        true,
        false,
        true
    };

    private Vector models;

    private PianoModel model;

    private String octaveString;
    
    private boolean sizeChanged = false;

    private boolean octaveChanged = false;

    /**
     * Constructor.
     *
     * @param model Model of a piano
     * @param locale Localization
     */
    public PianoCanvas(PianoModel model, LocalizationInterface locale)
    {
        // Initialize canvas
        super();

        // Initialize vector of models
        models = new Vector(1);

        // Get octave string
        octaveString = locale.getResource(LocalizationInterface.ID_OCTAVE);

        // Store model
        this.model = model;

        // Calculate key positions for touchscreen
        if (this.hasPointerEvents())
        {
            this.sizeChanged = true;
            this.octaveChanged = true;
            this.keyPositions = new int[12][2];
            this.octavePositions = new int[2][2];
        }
    }

    /**
     * Handler of the painting event.
     *
     * @param g Graphics to pain on
     */
    public void paint(Graphics g)
    {
        // Array with keys pressed
        final boolean[] keys = model.getCurrentOctaveKeys();

        // Get dimentions of display to draw upon
        final int width = getWidth();
        final int height = getHeight();

        this.fontHeight = g.getFont().getHeight();

        // Calculate dimension of a key
        this.whiteKeyWidth = (width - MARGIN * 2) / 7;
        this.whiteKeyHeight = height -
                (MARGIN + KEYS_NOTES_DISTANCE + fontHeight * 3);
        this.blackKeyWidth = whiteKeyWidth / 2; // One half
        this.blackKeyHeight = whiteKeyHeight / 2; // One half
        this.blackKeyNarrowWidth = blackKeyWidth / 2;
        this.whiteKeyNarrowWidth = whiteKeyWidth - blackKeyNarrowWidth;
        this.whiteKeyLesserHeight = whiteKeyHeight - blackKeyHeight;

        /* Draw background */
        g.setColor(WHITE_COLOR);
        g.fillRect(0, 0, width, height);

        /* Draw keys */

        // Draw C
        final int cx1 = 0 + MARGIN;
        final int cy1 = 0 + MARGIN;
        final int cx2 = cx1 + whiteKeyNarrowWidth;
        final int cy2 = cy1;
        final int cx3 = cx2;
        final int cy3 = cy2 + blackKeyHeight;
        final int cx4 = cx1 + whiteKeyWidth;
        final int cy4 = cy3;
        final int cx5 = cx4;
        final int cy5 = cy1 + whiteKeyHeight;
        final int cx6 = cx1;
        final int cy6 = cy5;

        setKeyColor(g, NOTE_C, keys[NOTE_C]);
        g.fillRect(cx1, cy1, whiteKeyNarrowWidth, whiteKeyHeight);
        g.fillRect(cx3, cy3, blackKeyNarrowWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(cx1, cy1, cx2, cy2);
        g.drawLine(cx2, cy2, cx3, cy3);
        g.drawLine(cx3, cy3, cx4, cy4);
        g.drawLine(cx4, cy4, cx5, cy5);
        g.drawLine(cx5, cy5, cx6, cy6);
        g.drawLine(cx6, cy6, cx1, cy1);

        // Draw C#
        final int ccx1 = cx2;
        final int ccy1 = cy2;
        final int ccx2 = ccx1 + blackKeyWidth;
        final int ccy2 = ccy1;
        //final int ccx3 = ccx2;
        //final int ccy3 = ccy2 + blackKeyHeight;
        //final int ccx4 = ccx1;
        //final int ccy4 = ccy3;

        setKeyColor(g, NOTE_C_SHARP, keys[NOTE_C_SHARP]);
        g.fillRect(ccx1, ccy1, blackKeyWidth, blackKeyHeight);

        if(keys[NOTE_C_SHARP])
        {
            g.setColor(CONTOUR_COLOR);
            g.drawRect(ccx1, ccy1, blackKeyWidth, blackKeyHeight);
        }

        // Draw D
        final int dx1 = ccx2;
        final int dy1 = ccy2;
        final int dx2 = dx1 + whiteKeyNarrowWidth - blackKeyNarrowWidth;
        final int dy2 = dy1;
        final int dx3 = dx2;
        final int dy3 = dy2 + blackKeyHeight;
        final int dx4 = cx4 + whiteKeyWidth;
        final int dy4 = dy3;
        final int dx5 = dx4;
        final int dy5 = dy2 + whiteKeyHeight;
        final int dx6 = cx5;
        final int dy6 = dy5;
        final int dx7 = dx6;
        final int dy7 = dy3;
        final int dx8 = dx1;
        final int dy8 = dy3;

        setKeyColor(g, NOTE_D, keys[NOTE_D]);
        g.fillRect(dx1, dy1, dx2 - dx1, blackKeyHeight);
        g.fillRect(dx7, dy7, whiteKeyWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(dx1, dy1, dx2, dy2);
        g.drawLine(dx2, dy2, dx3, dy3);
        g.drawLine(dx3, dy3, dx4, dy4);
        g.drawLine(dx4, dy4, dx5, dy5);
        g.drawLine(dx5, dy5, dx6, dy6);
        g.drawLine(dx6, dy6, dx7, dy7);
        g.drawLine(dx7, dy7, dx8, dy8);
        g.drawLine(dx8, dy8, dx1, dy1);

        // Draw D#
        final int ddx1 = dx2;
        final int ddy1 = dy2;
        final int ddx2 = ddx1 + blackKeyWidth;
        final int ddy2 = ddy1;
        //final int ddx3 = ddx2;
        //final int ddy3 = ddy2 + blackKeyHeight;
        //final int ddx4 = ddx1;
        //final int ddy4 = ddy3;

        setKeyColor(g, NOTE_D_SHARP, keys[NOTE_D_SHARP]);
        g.fillRect(ddx1, ddy1, blackKeyWidth, blackKeyHeight);

        if(keys[NOTE_D_SHARP])
        {
            g.setColor(CONTOUR_COLOR);
            g.drawRect(ddx1, ddy1, blackKeyWidth, blackKeyHeight);
        }

        // Draw E
        final int ex1 = ddx2;
        final int ey1 = ddy2;
        final int ex2 = dx4 + whiteKeyWidth;
        final int ey2 = ey1;
        final int ex3 = ex2;
        final int ey3 = ey2 + whiteKeyHeight;
        final int ex4 = dx5;
        final int ey4 = ey3;
        final int ex5 = ex4;
        final int ey5 = dy4;
        final int ex6 = ex1;
        final int ey6 = ey5;

        setKeyColor(g, NOTE_E, keys[NOTE_E]);
        g.fillRect(ex1, ey1, whiteKeyNarrowWidth, blackKeyHeight);
        g.fillRect(ex5, ey5, whiteKeyWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(ex1, ey1, ex2, ey2);
        g.drawLine(ex2, ey2, ex3, ey3);
        g.drawLine(ex3, ey3, ex4, ey4);
        g.drawLine(ex4, ey4, ex5, ey5);
        g.drawLine(ex5, ey5, ex6, ey6);
        g.drawLine(ex6, ey6, ex1, ey1);

        // Draw F
        final int fx1 = ex2;
        final int fy1 = ey2;
        final int fx2 = fx1 + whiteKeyNarrowWidth;
        final int fy2 = fy1;
        final int fx3 = fx2;
        final int fy3 = fy2 + blackKeyHeight;
        final int fx4 = fx1 + whiteKeyWidth;
        final int fy4 = fy3;
        final int fx5 = fx4;
        final int fy5 = fy1 + whiteKeyHeight;
        final int fx6 = fx1;
        final int fy6 = fy5;

        setKeyColor(g, NOTE_F, keys[NOTE_F]);
        g.fillRect(fx1, fy1, whiteKeyNarrowWidth, whiteKeyHeight);
        g.fillRect(fx3, fy3, blackKeyNarrowWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(fx1, fy1, fx2, fy2);
        g.drawLine(fx2, fy2, fx3, fy3);
        g.drawLine(fx3, fy3, fx4, fy4);
        g.drawLine(fx4, fy4, fx5, fy5);
        g.drawLine(fx5, fy5, fx6, fy6);
        g.drawLine(fx6, fy6, fx1, fy1);

        // Draw F#
        final int ffx1 = fx2;
        final int ffy1 = fy2;
        final int ffx2 = ffx1 + blackKeyWidth;
        final int ffy2 = ffy1;
        //final int ffx3 = ffx2;
        //final int ffy3 = ffy2 + blackKeyHeight;
        //final int ffx4 = ffx1;
        //final int ffy4 = ffy3;

        setKeyColor(g, NOTE_F_SHARP, keys[NOTE_F_SHARP]);
        g.fillRect(ffx1, ffy1, blackKeyWidth, blackKeyHeight);

        if(keys[NOTE_F_SHARP])
        {
            g.setColor(CONTOUR_COLOR);
            g.drawRect(ffx1, ffy1, blackKeyWidth, blackKeyHeight);
        }

        // Draw G
        final int gx1 = ffx2;
        final int gy1 = ffy2;
        final int gx2 = gx1 + whiteKeyNarrowWidth - blackKeyNarrowWidth;
        final int gy2 = gy1;
        final int gx3 = gx2;
        final int gy3 = gy2 + blackKeyHeight;
        final int gx4 = fx4 + whiteKeyWidth;
        final int gy4 = gy3;
        final int gx5 = gx4;
        final int gy5 = gy2 + whiteKeyHeight;
        final int gx6 = fx5;
        final int gy6 = gy5;
        final int gx7 = gx6;
        final int gy7 = gy3;
        final int gx8 = gx1;
        final int gy8 = gy3;

        setKeyColor(g, NOTE_G, keys[NOTE_G]);
        g.fillRect(gx1, gy1, gx2 - gx1, blackKeyHeight);
        g.fillRect(gx7, gy7, whiteKeyWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(gx1, gy1, gx2, gy2);
        g.drawLine(gx2, gy2, gx3, gy3);
        g.drawLine(gx3, gy3, gx4, gy4);
        g.drawLine(gx4, gy4, gx5, gy5);
        g.drawLine(gx5, gy5, gx6, gy6);
        g.drawLine(gx6, gy6, gx7, gy7);
        g.drawLine(gx7, gy7, gx8, gy8);
        g.drawLine(gx8, gy8, gx1, gy1);

        // Draw G#
        final int ggx1 = gx2;
        final int ggy1 = gy2;
        final int ggx2 = ggx1 + blackKeyWidth;
        final int ggy2 = ggy1;
        //final int ggx3 = ggx2;
        //final int ggy3 = ggy2 + blackKeyHeight;
        //final int ggx4 = ggx1;
        //final int ggy4 = ggy3;

        setKeyColor(g, NOTE_G_SHARP, keys[NOTE_G_SHARP]);
        g.fillRect(ggx1, ggy1, blackKeyWidth, blackKeyHeight);

        if(keys[NOTE_G_SHARP])
        {
            g.setColor(CONTOUR_COLOR);
            g.drawRect(ggx1, ggy1, blackKeyWidth, blackKeyHeight);
        }

        // Draw A
        final int ax1 = ggx2;
        final int ay1 = ggy2;
        final int ax2 = ax1 + whiteKeyNarrowWidth - blackKeyNarrowWidth;
        final int ay2 = ay1;
        final int ax3 = ax2;
        final int ay3 = ay2 + blackKeyHeight;
        final int ax4 = gx4 + whiteKeyWidth;
        final int ay4 = ay3;
        final int ax5 = ax4;
        final int ay5 = ay2 + whiteKeyHeight;
        final int ax6 = gx5;
        final int ay6 = ay5;
        final int ax7 = ax6;
        final int ay7 = ay3;
        final int ax8 = ax1;
        final int ay8 = ay3;

        setKeyColor(g, NOTE_A, keys[NOTE_A]);
        g.fillRect(ax1, ay1, ax2 - ax1, blackKeyHeight);
        g.fillRect(ax7, ay7, whiteKeyWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(ax1, ay1, ax2, ay2);
        g.drawLine(ax2, ay2, ax3, ay3);
        g.drawLine(ax3, ay3, ax4, ay4);
        g.drawLine(ax4, ay4, ax5, ay5);
        g.drawLine(ax5, ay5, ax6, ay6);
        g.drawLine(ax6, ay6, ax7, ay7);
        g.drawLine(ax7, ay7, ax8, ay8);
        g.drawLine(ax8, ay8, ax1, ay1);

        // Draw A#
        final int aax1 = ax2;
        final int aay1 = ay2;
        final int aax2 = aax1 + blackKeyWidth;
        final int aay2 = aay1;
        //final int aax3 = aax2;
        //final int aay3 = aay2 + blackKeyHeight;
        //final int aax4 = aax1;
        //final int aay4 = aay3;

        setKeyColor(g, NOTE_A_SHARP, keys[NOTE_A_SHARP]);
        g.fillRect(aax1, aay1, blackKeyWidth, blackKeyHeight);

        if(keys[PianoNotes.NOTE_A_SHARP])
        {
            g.setColor(CONTOUR_COLOR);
            g.drawRect(aax1, aay1, blackKeyWidth, blackKeyHeight);
        }

        // Draw H
        final int hx1 = aax2;
        final int hy1 = aay2;
        final int hx2 = ax4 + whiteKeyWidth;
        final int hy2 = hy1;
        final int hx3 = hx2;
        final int hy3 = hy2 + whiteKeyHeight;
        final int hx4 = ax5;
        final int hy4 = hy3;
        final int hx5 = hx4;
        final int hy5 = ay4;
        final int hx6 = hx1;
        final int hy6 = hy5;

        setKeyColor(g, NOTE_H, keys[NOTE_H]);
        g.fillRect(hx1, hy1, hx2 - hx1, blackKeyHeight);
        g.fillRect(hx5, hy5, whiteKeyWidth, whiteKeyLesserHeight);

        g.setColor(CONTOUR_COLOR);
        g.drawLine(hx1, hy1, hx2, hy2);
        g.drawLine(hx2, hy2, hx3, hy3);
        g.drawLine(hx3, hy3, hx4, hy4);
        g.drawLine(hx4, hy4, hx5, hy5);
        g.drawLine(hx5, hy5, hx6, hy6);
        g.drawLine(hx6, hy6, hx1, hy1);

        /* Draw helping characters */

        g.setColor(CONTOUR_COLOR);

        final int dcyw = height - fontHeight;
        final int dcyb = dcyw - fontHeight;

        final int yl = cy6 + 5;
        final int dcylw = dcyb - 2;
        final int dcylb = dcylw - fontHeight;

        final int halfWhiteKeyWidth = whiteKeyWidth / 2;
        final int halfBlackKeyWidth = blackKeyWidth / 2;

        final int dccx1 = cx1 + halfWhiteKeyWidth;
        g.drawChar('1', dccx1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dccx1, dcylw, dccx1, yl);

        final int dcccx1 = ccx1 + halfBlackKeyWidth;
        g.drawChar('2', dcccx1, dcyb, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcccx1, dcylb, dcccx1, yl);

        final int dcdx1 = dx6 + halfWhiteKeyWidth;
        g.drawChar('3', dcdx1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcdx1, dcylw, dcdx1, yl);

        final int dcddx1 = ddx1 + halfBlackKeyWidth;
        g.drawChar('4', dcddx1, dcyb, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcddx1, dcylb, dcddx1, yl);

        final int dcex1 = ex4 + halfWhiteKeyWidth;
        g.drawChar('5', dcex1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcex1, dcylw, dcex1, yl);

        final int dcfx1 = fx1 + halfWhiteKeyWidth;
        g.drawChar('6', dcfx1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcfx1, dcylw, dcfx1, yl);

        final int dcffx1 = ffx1 + halfBlackKeyWidth;
        g.drawChar('7', dcffx1, dcyb, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcffx1, dcylb, dcffx1, yl);

        final int dcgx1 = gx6 + halfWhiteKeyWidth;
        g.drawChar('8', dcgx1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcgx1, dcylw, dcgx1, yl);

        final int dcggx1 = ggx1 + halfBlackKeyWidth;
        g.drawChar('9', dcggx1, dcyb, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcggx1, dcylb, dcggx1, yl);

        final int dcax1 = ax6 + halfWhiteKeyWidth;
        g.drawChar('*', dcax1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcax1, dcylw, dcax1, yl);

        final int dcaax1 = aax1 + halfBlackKeyWidth;
        g.drawChar('0', dcaax1, dcyb, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dcaax1, dcylb, dcaax1, yl);

        final int dchx1 = hx4 + halfWhiteKeyWidth;
        g.drawChar('#', dchx1, dcyw, Graphics.BOTTOM | Graphics.HCENTER);
        g.drawLine(dchx1, dcylw, dchx1, yl);

        /* Draw octave. */
        final int oy = dcyw + fontHeight;
        StringBuffer octaveBuffer = new StringBuffer();
        octaveBuffer.append(octaveString);
        octaveBuffer.append(" < ");
        octaveBuffer.append(model.getOctave());
        octaveBuffer.append(" >");
        g.drawString(octaveBuffer.toString(),
                     width / 2,
                     oy,
                     Graphics.BOTTOM | Graphics.HCENTER);

        if (this.sizeChanged)
        {
            calculateKeyPositions();
            this.sizeChanged = false;
        }

        if (this.octaveChanged)
        {
            calculateOctavePositions(g.getFont().stringWidth("<"),
                    g.getFont().stringWidth(octaveBuffer.toString()),
                    g.getFont().stringWidth(" " + model.getOctave() + " "));
            this.octaveChanged = false;
        }
    }

    /**
     * Set color of the key.
     *
     * @param g Graphics to paint upon
     * @param note Index of the note
     * @param pressed Stated of the key (pressed or not)
     */
    private void setKeyColor(Graphics g, int note, boolean pressed)
    {
        if(WHITE_KEY[note] && !pressed ||
           !WHITE_KEY[note] && pressed)
        {
            g.setColor(WHITE_COLOR);
        }
        else
        {
            g.setColor(BLACK_COLOR);
        }
    }

    /**
     * Handler of key pressed event.
     *
     * @param key Code of the key pressed
     */
    public void keyPressed(int key)
    {
        InstrumentEvent ev;
        int note = -1;

        // Handle keyboard keys
        switch(key)
        {
            case KEY_NUM1: note = NOTE_C; break;
            case KEY_NUM2: note = NOTE_C_SHARP; break;
            case KEY_NUM3: note = NOTE_D; break;
            case KEY_NUM4: note = NOTE_D_SHARP; break;
            case KEY_NUM5: note = NOTE_E; break;
            case KEY_NUM6: note = NOTE_F; break;
            case KEY_NUM7: note = NOTE_F_SHARP; break;
            case KEY_NUM8: note = NOTE_G; break;
            case KEY_NUM9: note = NOTE_G_SHARP; break;
            case KEY_STAR: note = NOTE_A; break;
            case KEY_NUM0: note = NOTE_A_SHARP; break;
            case KEY_POUND: note = NOTE_H; break;
        }

        if(note > -1)
        {
            ev = new InstrumentEvent(note, InstrumentEvent.KEY_PRESSED);
            sendEvent(ev, SINGLE_MODEL);
            return;
        }

        int action = getGameAction(key);

        int octave = -1;
        
        // Handle keyboard octave
        switch(action)
        {
            case UP: octave = 1; break;
            case DOWN: octave = 0; break;
            case RIGHT: octave = 1; break;
            case LEFT: octave = 0; break;
        }

        if(octave > -1)
        {
            int octaveOperation = (octave == 1) ?
                                   InstrumentEvent.OCTAVE_UP :
                                   InstrumentEvent.OCTAVE_DOWN;
            ev = new InstrumentEvent(0, octaveOperation);
            sendEvent(ev, SINGLE_MODEL);
            if (model.getOctave() == 0 || model.getOctave() == -1)
                this.octaveChanged = true;
            return;
        }
    }

    /**
     * Handler of key released event.
     *
     * @param key Code of the key released
     */
    public void keyReleased(int key)
    {
        int note = -1;

        switch(key)
        {
            case KEY_NUM1: note = NOTE_C; break;
            case KEY_NUM2: note = NOTE_C_SHARP; break;
            case KEY_NUM3: note = NOTE_D; break;
            case KEY_NUM4: note = NOTE_D_SHARP; break;
            case KEY_NUM5: note = NOTE_E; break;
            case KEY_NUM6: note = NOTE_F; break;
            case KEY_NUM7: note = NOTE_F_SHARP; break;
            case KEY_NUM8: note = NOTE_G; break;
            case KEY_NUM9: note = NOTE_G_SHARP; break;
            case KEY_STAR: note = NOTE_A; break;
            case KEY_NUM0: note = NOTE_A_SHARP; break;
            case KEY_POUND: note = NOTE_H; break;
        }

        if(note > -1)
        {
            InstrumentEvent ev =
                    new InstrumentEvent(note, InstrumentEvent.KEY_RELEASED);
            sendEvent(ev, SINGLE_MODEL);
        }
    }
   
    /**
     * Calculate key positions for touchscreen use
     */
    private void calculateKeyPositions()
    {
        this.keyPositions[0][0] = MARGIN;
        this.keyPositions[0][1] = MARGIN + this.whiteKeyWidth;

        this.keyPositions[1][0] = MARGIN + this.whiteKeyNarrowWidth;
        this.keyPositions[1][1] = this.keyPositions[1][0] + this.blackKeyWidth;

        this.keyPositions[2][0] = this.keyPositions[0][1];
        this.keyPositions[2][1] = this.keyPositions[2][0] + this.whiteKeyWidth;
        
        this.keyPositions[3][0] = this.keyPositions[1][1] + this.blackKeyWidth;
        this.keyPositions[3][1] = this.keyPositions[3][0] + this.blackKeyWidth;

        this.keyPositions[4][0] = this.keyPositions[2][1];
        this.keyPositions[4][1] = this.keyPositions[4][0] + this.whiteKeyWidth;
        
        this.keyPositions[5][0] = this.keyPositions[4][1];
        this.keyPositions[5][1] = this.keyPositions[5][0] + this.whiteKeyWidth;

        this.keyPositions[6][0] = this.keyPositions[3][1]
                + (2 * this.whiteKeyNarrowWidth);
        this.keyPositions[6][1] = this.keyPositions[6][0] + this.blackKeyWidth;

        this.keyPositions[7][0] = this.keyPositions[5][1];
        this.keyPositions[7][1] = this.keyPositions[7][0] + this.whiteKeyWidth;

        this.keyPositions[8][0] = this.keyPositions[6][1] + this.blackKeyWidth;
        this.keyPositions[8][1] = this.keyPositions[8][0] + this.blackKeyWidth;

        this.keyPositions[9][0] = this.keyPositions[7][1];
        this.keyPositions[9][1] = this.keyPositions[9][0] + this.whiteKeyWidth;

        this.keyPositions[10][0] = this.keyPositions[8][1] + this.blackKeyWidth;
        this.keyPositions[10][1] = this.keyPositions[10][0]
                + this.blackKeyWidth;

        this.keyPositions[11][0] = this.keyPositions[9][1];
        this.keyPositions[11][1] = this.keyPositions[11][0]
                + this.whiteKeyWidth;
     }

    /**
     * Calculate positions of octave controls for touchscreen use
     */
    private void calculateOctavePositions(int charWidth, int octaveFullString,
            int octaveValue)
    {
        this.octavePositions[1][1] = (getWidth() / 2)
                + (octaveFullString / 2);
        this.octavePositions[1][0] = this.octavePositions[1][1]
                - charWidth;
        this.octavePositions[0][1] = this.octavePositions[1][0]
                - octaveValue;
        this.octavePositions[0][0] = this.octavePositions[0][1]
                - charWidth;
        repaint();
    }

    /*
     * Determines keycode for coordinates of the pointer
     * @param x x-coordinate value of the pointer
     * @param y y-coordinate value of the pointer
     * @return the keycode of the key pressed on the clavier
     */
    private int determinePressedKey(int x, int y)
    {
        //Check if pointer was pressed within clavier
        if (x > MARGIN && x < (getWidth() - MARGIN)
                && y > MARGIN && y < (MARGIN + this.whiteKeyHeight))
        {
            // Check if pointer is in uopper area where black keys are possible
            if(y >= MARGIN && y <= (this.blackKeyHeight + MARGIN) )
            {
                if (x > this.keyPositions[0][0] && x < this.keyPositions[1][0])
                    return KEY_NUM1;

                if (x > this.keyPositions[1][0] && x < this.keyPositions[1][1])
                    return KEY_NUM2;

                if (x > this.keyPositions[1][1] && x < this.keyPositions[3][0])
                    return KEY_NUM3;

                if (x > this.keyPositions[3][0] && x < this.keyPositions[3][1])
                    return KEY_NUM4;

                if (x > this.keyPositions[3][1] && x < this.keyPositions[4][1])
                    return KEY_NUM5;
               
                if (x > this.keyPositions[5][0] && x < this.keyPositions[6][0])
                   return KEY_NUM6;

                if (x > this.keyPositions[6][0] && x < this.keyPositions[6][1])
                    return KEY_NUM7;
               
                if (x > this.keyPositions[6][1] && x < this.keyPositions[8][0])
                   return KEY_NUM8;

                if (x > this.keyPositions[8][0] && x < this.keyPositions[8][1])
                    return KEY_NUM9;

                if (x > this.keyPositions[8][1] && x < this.keyPositions[10][0])
                    return KEY_STAR;

                if (x > this.keyPositions[10][0]
                        && x < this.keyPositions[10][1])
                    return KEY_NUM0;
                
                if (x > this.keyPositions[10][1]
                        && x < this.keyPositions[11][1])
                    return KEY_POUND;
           }
            // Check only for white keys in the lower area
           else
           {
                if (x > this.keyPositions[0][0] && x < this.keyPositions[0][1])
                    return KEY_NUM1;
                if (x > this.keyPositions[2][0] && x < this.keyPositions[2][1])
                    return KEY_NUM3;
                if (x > this.keyPositions[4][0] && x < this.keyPositions[4][1])
                    return KEY_NUM5;
                if (x > this.keyPositions[5][0] && x < this.keyPositions[5][1])
                    return KEY_NUM6;
                if (x > this.keyPositions[7][0] && x < this.keyPositions[7][1])
                    return KEY_NUM8;
                if (x > this.keyPositions[9][0] && x < this.keyPositions[9][1])
                    return KEY_STAR;
                if (x > this.keyPositions[11][0]
                        && x < this.keyPositions[11][1])
                    return KEY_POUND;
           }
        }
        // Check if octave controls were pressed
        else if (y > (getHeight() - this.fontHeight))
        {
            if (x > this.octavePositions[0][0]
                    && x < this.octavePositions[0][1])
                return -3;
            if (x > this.octavePositions[1][0]
                    && x < this.octavePositions[1][1])
                return -4;
        }
        else
        {
            // Pointer not over clavier nor over octave controls
        }
        return -1;
    }

     /**
     * Handler of pointer pressed event
     * @param x x-coordinate value of the pointer
     * @param y y-coordinate value of the pointer
     */
    protected void pointerPressed(int x, int y)
    {
        this.currentPressedKey = determinePressedKey(x, y);
        if (this.currentPressedKey != -1)
            keyPressed(this.currentPressedKey);
    }

    /**
     * Handler of pointer dragged event
     * @param x x-coordinate value of the pointer
     * @param y y-coordinate value of the pointer
     */
    protected void pointerDragged(int x, int y)
    {
        int draggedKey = determinePressedKey(x, y);
        if (this.currentPressedKey != draggedKey)
        {
            keyReleased(this.currentPressedKey);
            if (draggedKey != -1)
                keyPressed(draggedKey);
            this.currentPressedKey = draggedKey;
            
        }
    }

    /**
     * Handler of pointer released event
     * @param x x-coordinate value of the pointer
     * @param y y-coordinate value of the pointer
     */
    protected void pointerReleased(int x, int y)
    {
        this.currentPressedKey = determinePressedKey(x, y);
        if (this.currentPressedKey != -1)
            keyReleased(this.currentPressedKey);
    }
    /**
     * Handler of size changed event
     */
    protected void sizeChanged(int w, int h)
    {
        if (this.hasPointerEvents())
        {
            this.sizeChanged = true;
            this.octaveChanged = true;
        }

    }

    /**
     * Implementation of PianoView.
     */
    public void update()
    {
        repaint();
    }

    /**
     * Implementation of InstrumentController.
     */
    public void addInstrumentModel(PianoModel model)
    {
        models.addElement(model);
    }

    /**
     * Implementation of InstrumentController.
     */
    public void removeInstrumentModel(PianoModel model)
    {
        models.removeElement(model);
    }

    /**
     * Implementation of InstrumentController.
     */
    public void sendEvent(InstrumentEvent e, int index)
    {
        ((InstrumentModel) models.elementAt(index)).processEvent(e);
    }
}
