package hr.fer.zemris.java.hw10.jnotepadpp.actions.document;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.DocumentNotepadAction;
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
 * Opens a new tab associated with some user choosen document.
 * 
 * @author Erik Banek
 */
public class OpenExistingDocumentAction extends DocumentNotepadAction {
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
    public OpenExistingDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(Action.SMALL_ICON, new ImageIcon("images/open.gif"));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Path path = null;
        JFileChooser fc = new JFileChooser();

        fc.setApproveButtonText(lp.getString("approve"));
        fc.setToolTipText(lp.getString("fcToolTip"));
        fc.setName(lp.getString("fcOpenExistingName"));
        fc.setDialogTitle(lp.getString("fcOpenExistingDialogTitle"));

        if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        path = fc.getSelectedFile().toPath();
        if (!Files.isReadable(path)) {
            JOptionPane.showMessageDialog(
                    frame,
                    String.format(lp.getString("fcUnexistingDialog"),
                            path.toString()), lp.getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String startText = null;
        try {
            byte[] bytes = Files.readAllBytes(path);
            startText = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(frame,
                    String.format(lp.getString("fcIOError"), path),
                    lp.getString("error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        notepad.addTab(path, startText);
    }

}
