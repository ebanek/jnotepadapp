package hr.fer.zemris.java.hw10.jnotepadpp.actions.model;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import javax.swing.JFrame;

/**
 * Document notepad action which is dependent on the currently opened tab.
 * 
 * @author Erik Banek
 */
public abstract class ADocumentTabAction extends DocumentNotepadAction {
    private static final long serialVersionUID = 1L;

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
     *            on which the information is shown.
     */
    public ADocumentTabAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        setEnabled(false);
    }

    @Override
    public void update() {
        this.setEnabled(notepad.getCurrentTab() != null);
    }
}
