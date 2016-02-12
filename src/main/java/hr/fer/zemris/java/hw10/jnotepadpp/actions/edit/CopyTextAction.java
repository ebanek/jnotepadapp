package hr.fer.zemris.java.hw10.jnotepadpp.actions.edit;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPTab;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.ASelectedTextAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * Copies currently selected text to the clipboard.
 * 
 * @author Erik Banek
 */
public class CopyTextAction extends ASelectedTextAction {
    private static final long serialVersionUID = 1L;

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
    public CopyTextAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad) {
        super(key, lp, notepad);
        putValue(Action.SMALL_ICON, new ImageIcon("images/copy.gif"));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JNotepadPPTab tab = notepad.getCurrentTab();
        if (tab != null) {
            tab.getEditor().copy();
        }
    }
}
