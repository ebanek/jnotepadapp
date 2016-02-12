package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;

/**
 * Shows information about the current document opened in the notepad.
 * 
 * @author Erik Banek
 */
public class JNotepadPPBar extends JPanel implements INotepadListener {
    private static final long serialVersionUID = 1L;
    /** Preset dimensions of the bar, only the height matters. */
    private static final Dimension BAR_HEIGHT = new Dimension(20, 20);
    /** The date format that the bar displays. */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    /** Interval in which the timer that shows the date is updated. */
    private static final int INTERVAL = 500;
    /** Notepad who owns this bar. */
    private JNotepadPP notepad;
    /** Label that shows the length of current document. */
    private JLabel lenLabel;
    /** Shows info about the position of the carent in current document. */
    private JLabel caretLabel;
    /** Shows current time and date. */
    private JLabel timeLabel;
    /** Localization provider that holds the language information. */
    private ILocalizationProvider lp;

    /**
     * Constructs a new bar.
     * 
     * @param notepad
     *            who owns the bar.
     * @param lp
     *            localization provider which has language information.
     * @param frame
     *            in which the bar is shown, stops the timer when it is closed.
     */
    public JNotepadPPBar(JNotepadPP notepad, ILocalizationProvider lp,
            JFrame frame) {
        this.notepad = notepad;
        this.lp = lp;
        setLayout(new GridLayout(1, 5));
        setPreferredSize(BAR_HEIGHT);

        add(lenLabel = new JLabel());
        add(caretLabel = new JLabel());
        add(timeLabel = new JLabel());
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        timerInit(frame);
    }

    /**
     * Initializes the timer that shows datetime in the bar.
     * 
     * @param frame
     *            which stops the timer when it is closed.
     */
    private void timerInit(JFrame frame) {
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                timeLabel.setText(DATE_FORMAT.format(now.getTime()));
            }
        });
        timer.start();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                timer.stop();
            }
        });
    }

    @Override
    public void update() {
        if (notepad.getCurrentTab() == null) {
            lenLabel.setText("");
            caretLabel.setText("");
            return;
        }

        JTextArea editor = notepad.getCurrentTab().getEditor();

        lenLabel.setText(lp.getString("length") + ": "
                + editor.getText().length());

        int selected = Math.abs(editor.getCaret().getDot()
                - editor.getCaret().getMark());
        int caretPosition = editor.getCaretPosition();

        int line = 0;
        int column = 0;
        try {
            line = editor.getLineOfOffset(caretPosition);
            column = caretPosition - editor.getLineStartOffset(line) + 1;
        } catch (BadLocationException ignored) {
        }

        caretLabel.setText(lp.getString("line") + ": " + (line + 1) + "  "
                + lp.getString("column") + ": " + column + "  "
                + lp.getString("selected") + ": " + selected);
    }
}
