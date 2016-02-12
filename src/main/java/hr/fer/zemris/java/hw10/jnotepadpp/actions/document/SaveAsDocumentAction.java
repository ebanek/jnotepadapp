package hr.fer.zemris.java.hw10.jnotepadpp.actions.document;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.ADocumentTabAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Sets the new path of current document and saves it.
 * 
 * @author Erik Banek
 */
public class SaveAsDocumentAction extends ADocumentTabAction {
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
    public SaveAsDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(Action.SMALL_ICON, new ImageIcon("images/saveas.gif"));
        putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("shift control S"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (notepad.getCurrentTab() == null) {
            return;
        }
        JFileChooser fc = new JFileChooser();

        fc.setDialogTitle(lp.getString("fcSaveAsTitle"));
        if (fc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(frame, lp.getString("nothingSaved"),
                    lp.getString("systemMessage"),
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Path p = fc.getSelectedFile().toPath();
        if (Files.exists(p)) {
            int r = JOptionPane.showConfirmDialog(frame, String.format(
                    lp.getString("fcAlreadyExists"), p.toString()), lp
                    .getString("warning"), JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (r != JOptionPane.YES_OPTION) {
                return;
            }
        }

        String text = notepad.getCurrentTab().getEditor().getText();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(p, bytes);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(frame, lp.getString("writingError"),
                    lp.getString("systemMessage"), JOptionPane.ERROR_MESSAGE);
        }

        notepad.setPath(p);
        notepad.setSaved(true);
    }
}
