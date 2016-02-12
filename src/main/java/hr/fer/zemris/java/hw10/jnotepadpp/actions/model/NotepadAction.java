package hr.fer.zemris.java.hw10.jnotepadpp.actions.model;

import hr.fer.zemris.java.hw10.jnotepadpp.INotepadListener;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.local.swing.LocalizableAction;

/**
 * Localizable action which acts on some notepad.
 * 
 * @author Erik Banek
 */
public abstract class NotepadAction extends LocalizableAction implements
        INotepadListener {
    private static final long serialVersionUID = 1L;
    /** Notepad on which the action acts. */
    protected JNotepadPP notepad;

    /**
     * Constructor.
     * 
     * @param key
     *            name of action text.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     */
    public NotepadAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad) {
        super(key, lp);
        this.notepad = notepad;
    }
}
