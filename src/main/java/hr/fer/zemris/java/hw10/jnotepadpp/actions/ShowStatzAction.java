package hr.fer.zemris.java.hw10.jnotepadpp.actions;

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
 * Shows info to the user about the currently opened document.
 * 
 * @author Erik Banek
 */
public class ShowStatzAction extends ADocumentTabAction {
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
    public ShowStatzAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, JFrame frame) {
        super(key, lp, notepad, frame);
        putValue(Action.SMALL_ICON, new ImageIcon("images/info.gif"));
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JNotepadPPTab tab = notepad.getCurrentTab();
        if (tab == null) {
            return;
        }
        int charCount = 0;
        int nonBlankCount = 0;
        String text = tab.getEditor().getText();
        for (Character c : text.toCharArray()) {
            charCount++;
            if (!Character.isWhitespace(c)) {
                nonBlankCount++;
            }
        }
        int lineCount = tab.getEditor().getLineCount();

        JOptionPane.showMessageDialog(frame, String.format(
                lp.getString("statisticMessage"), charCount, nonBlankCount,
                lineCount), lp.getString("statisticTitle"),
                JOptionPane.INFORMATION_MESSAGE);
    }
}
