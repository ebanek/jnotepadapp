package hr.fer.zemris.java.hw10.jnotepadpp.actions.document;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.DocumentNotepadAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 * Opens a new tab with no associated path.
 * 
 * @author Erik Banek
 */
public class OpenNewDocumentAction extends DocumentNotepadAction {

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
    public OpenNewDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(SMALL_ICON, new ImageIcon("images/newDoc.gif"));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        notepad.addTab(null, "");
    }

}
