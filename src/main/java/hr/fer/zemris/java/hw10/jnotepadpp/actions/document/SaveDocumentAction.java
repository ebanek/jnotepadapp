package hr.fer.zemris.java.hw10.jnotepadpp.actions.document;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPTab;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.ADocumentTabAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Saves the document to current path if it has one, otherwise prompts the user
 * to choose it.
 * 
 * @author Erik Banek
 */
public class SaveDocumentAction extends ADocumentTabAction {
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
    public SaveDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(Action.SMALL_ICON, new ImageIcon("images/save.gif"));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JNotepadPPTab tab = notepad.getCurrentTab();
        if (tab == null) {
            return;
        }

        if (tab.getPath() == null) {
            Action saveAs = new SaveAsDocumentAction("x", lp, notepad, frame);
            saveAs.actionPerformed(e);
        } else {
            try {
                Files.write(
                        tab.getPath(),
                        tab.getEditor().getText()
                                .getBytes(StandardCharsets.UTF_8));
                notepad.setSaved(true);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame,
                        lp.getString("writingError"),
                        lp.getString("systemMessage"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
