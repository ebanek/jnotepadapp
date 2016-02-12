package hr.fer.zemris.java.hw10.jnotepadpp.actions.model;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import javax.swing.JFrame;

/**
 * Notepad action which can show appropriate notifications to the user on some
 * frame.
 * 
 * @author Erik Banek
 */
public abstract class DocumentNotepadAction extends NotepadAction {
    private static final long serialVersionUID = 1L;
    /** Frame on which the info is shown. */
    protected JFrame frame;

    /**
     * Constructor.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @param frame
     *            on which the info or prompts are shown.
     */
    public DocumentNotepadAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad);
        this.frame = frame;
    }
}
