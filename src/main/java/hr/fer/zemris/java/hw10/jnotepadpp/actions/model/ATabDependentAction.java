package hr.fer.zemris.java.hw10.jnotepadpp.actions.model;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

/**
 * Notepad action which is dependent on the currently opened tab in the notepad.
 * 
 * @author Erik Banek
 */
public abstract class ATabDependentAction extends NotepadAction {
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
     */
    public ATabDependentAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad) {
        super(key, lp, notepad);
        setEnabled(false);
    }

    @Override
    public void update() {
        this.setEnabled(notepad.getCurrentTab() != null);
    }

}
