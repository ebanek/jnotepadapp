package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPTab;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.SaveDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.DocumentNotepadAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Exits the application, prompting the user if unsaved modifications to
 * documents are present.
 * 
 * @author Erik Banek
 */
public class ExitAction extends DocumentNotepadAction {
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
    public ExitAction(String key, ILocalizationProvider lp, JNotepadPP notepad,
            JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(Action.SMALL_ICON, new ImageIcon("images/exitall.gif"));
        putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("shift control W"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (notepad.getCurrentTab() == null) {
            frame.dispose();
            return;
        }
        int r = JOptionPane
                .showConfirmDialog(frame, lp.getString("exitPrompt"));
        if (r == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (r == JOptionPane.NO_OPTION) {
            frame.dispose();
            return;
        }

        while (notepad.getCurrentTab() != null) {
            JNotepadPPTab tab = notepad.getCurrentTab();
            if (!tab.getSaved()) {
                if (notepad.getCurrentTab().getEditor().getText().isEmpty()) {
                    notepad.closeTab(tab);
                    continue;
                }

                boolean b = (tab.getPath() == null);
                String text = b ? "" : (" " + tab.getPath().toFile().getName());

                int result = JOptionPane.showConfirmDialog(frame,
                        String.format(lp.getString("saveTabPrompt"), text));

                if (result == JOptionPane.YES_OPTION) {
                    Action action = new SaveDocumentAction("x", lp, notepad,
                            frame);
                    action.actionPerformed(e);
                } else if (result == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            notepad.closeTab(tab);
        }
        frame.dispose();
    }
}
