package hr.fer.zemris.java.hw10.jnotepadpp.actions.model;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPTab;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import javax.swing.JTextArea;

/**
 * Notepad action which is dependend on the currently selected text in the
 * notepad.
 * 
 * @author Erik Banek
 */
public abstract class ASelectedTextAction extends NotepadAction {
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
    public ASelectedTextAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad) {
        super(key, lp, notepad);
        setEnabled(false);
    }

    @Override
    public void update() {
        JNotepadPPTab currentTab = notepad.getCurrentTab();
        if (currentTab == null) {
            this.setEnabled(false);
            return;
        }

        JTextArea editor = currentTab.getEditor();
        int length = Math.abs(editor.getCaret().getDot()
                - editor.getCaret().getMark());
        this.setEnabled(length > 0);
    }
}
