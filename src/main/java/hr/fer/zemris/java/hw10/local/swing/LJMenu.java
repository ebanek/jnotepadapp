package hr.fer.zemris.java.hw10.local.swing;

import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import javax.swing.JMenu;

/**
 * Localizable Swing menu.
 * 
 * @author Erik Banek
 */
public class LJMenu extends JMenu {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param key
     *            String holding the key to text of menu.
     * @param lp
     *            language provider.
     */
    public LJMenu(String key, ILocalizationProvider lp) {
        super(lp.getString(key));
        lp.addLocalizationListener(() -> {
            setText(lp.getString(key));
        });
    }
}
