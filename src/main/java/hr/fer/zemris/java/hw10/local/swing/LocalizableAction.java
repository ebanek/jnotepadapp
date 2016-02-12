package hr.fer.zemris.java.hw10.local.swing;

import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import javax.swing.AbstractAction;

/**
 * Localizable Swing action.
 * 
 * @author Erik Banek
 */
public abstract class LocalizableAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    /** Language provider of action. */
    protected ILocalizationProvider lp;

    /**
     * Constructor.
     * 
     * @param key
     *            of text in appropriate language of action.
     * @param lp
     *            language provider.
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.lp = lp;

        putValue(NAME, lp.getString(key));
        putValue(SHORT_DESCRIPTION, lp.getString(key));
        lp.addLocalizationListener(() -> {
            putValue(NAME, lp.getString(key));
            putValue(SHORT_DESCRIPTION, lp.getString(key));
        });
    }
}
