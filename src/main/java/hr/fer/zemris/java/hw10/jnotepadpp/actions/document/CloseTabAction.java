package hr.fer.zemris.java.hw10.jnotepadpp.actions.document;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPTab;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.ADocumentTabAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Closes the currently opened tab, prompting the user if modifications have not
 * been saved.
 * 
 * @author Erik Banek
 */
public class CloseTabAction extends ADocumentTabAction {
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
    public CloseTabAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(Action.SMALL_ICON, new ImageIcon("images/close.gif"));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JNotepadPPTab tab = notepad.getCurrentTab();
        if (tab == null) {
            return;
        }

        if (!tab.getSaved()) {
            if (notepad.getCurrentTab().getEditor().getText().isEmpty()) {
                notepad.closeTab(tab);
                return;
            }

            boolean b = (tab.getPath() == null);
            String text = b ? "" : (" " + tab.getPath().toFile().getName());

            int result = JOptionPane.showConfirmDialog(frame,
                    String.format(lp.getString("saveTabPrompt"), text));
            if (result == JOptionPane.YES_OPTION) {
                Action action = new SaveDocumentAction("x", lp, notepad, frame);
                action.actionPerformed(e);
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        notepad.closeTab(tab);
    }
}
