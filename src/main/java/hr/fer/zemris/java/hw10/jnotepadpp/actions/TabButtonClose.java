package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPTab;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.CloseTabAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Actions which is added to the tab title button for closing that tab.
 * 
 * @author Erik Banek
 */
public class TabButtonClose extends AbstractAction {
    private static final long serialVersionUID = 1L;
    /** Tab which the action closes. */
    private JNotepadPPTab tabToClose;
    /** Language provider. */
    private ILocalizationProvider lp;
    /** Notepad on which the action acts. */
    private JNotepadPP notepad;
    /** Frame on which the user is shown info. */
    private JFrame frame;

    /**
     * Constructor.
     * 
     * @param lp
     *            language provider.
     * @param notepad
     *            on which the action acts.
     * @param frame
     *            on which the info is shown.
     * @param tabToClose
     *            tab which the action closes.
     */
    public TabButtonClose(ILocalizationProvider lp, JNotepadPP notepad,
            JFrame frame, JNotepadPPTab tabToClose) {
        putValue(Action.SMALL_ICON, new ImageIcon("images/cross.png"));
        this.tabToClose = tabToClose;
        this.lp = lp;
        this.notepad = notepad;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notepad.setCurrentTab(tabToClose);
        Action action = new CloseTabAction("x", lp, notepad, frame);
        action.actionPerformed(e);
    }
}
