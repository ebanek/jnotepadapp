package hr.fer.zemris.java.hw10.jnotepadpp.actions.tools;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.ASelectedTextAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

/**
 * Changes the currently selected String in notepad in way specified by the
 * {@code ICaseChanger}.
 * 
 * @author Erik Banek
 */
public class ChangeCaseAction extends ASelectedTextAction {
    private static final long serialVersionUID = 1L;
    /** Changer which does the change that the Action wants on String. */
    private ICaseChanger changer;

    /**
     * Constructor.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @param changer
     *            which does the change.
     */
    public ChangeCaseAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, ICaseChanger changer) {
        super(key, lp, notepad);
        this.changer = changer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea editor = notepad.getCurrentTab().getEditor();
        Caret caret = editor.getCaret();
        int start = Math.min(caret.getDot(), caret.getMark());
        int length = Math.max(caret.getDot() - start, caret.getMark() - start);

        Document doc = editor.getDocument();

        try {
            String text = doc.getText(start, length);
            doc.remove(start, length);
            text = changer.change(text);
            doc.insertString(start, text, null);
        } catch (BadLocationException ignorable) {

        }
    }

}
