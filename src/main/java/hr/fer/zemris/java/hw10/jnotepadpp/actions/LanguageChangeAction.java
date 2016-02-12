package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.NotepadAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.local.LocalizationProvider;

import java.awt.event.ActionEvent;

/**
 * Sets the application language.
 * 
 * @author Erik Banek
 */
public class LanguageChangeAction extends NotepadAction {
    private static final long serialVersionUID = 1L;
    /** Language to which this action sets the application language. */
    private String language;

    /**
     * Constructor.
     * 
     * @param language
     *            to which this actions sets the app language.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     */
    public LanguageChangeAction(String language, ILocalizationProvider lp,
            JNotepadPP notepad) {
        super(language, lp, notepad);
        this.language = language;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LocalizationProvider.getInstance().setLanguage(language);
        notepad.updateListeners();
    }
}
