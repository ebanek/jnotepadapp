package hr.fer.zemris.java.hw10.jnotepadpp.actions.tools;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.ASelectedTextAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

/**
 * Changes the currently selected lines in notepad in way specified by the
 * {@code ILineChanger}.
 * 
 * @author Erik Banek
 */
public class LineChangeAction extends ASelectedTextAction {
    private static final long serialVersionUID = 1L;
    /** Changer which does the change that the Action wants on lines. */
    private ILineChanger changer;

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
    public LineChangeAction(String key, ILocalizationProvider lp,
            JNotepadPP notepad, ILineChanger changer) {
        super(key, lp, notepad);
        this.changer = changer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea editor = notepad.getCurrentTab().getEditor();
        Caret caret = editor.getCaret();
        Document doc = editor.getDocument();

        int start = Math.min(caret.getDot(), caret.getMark());
        int length = Math.max(caret.getDot() - start, caret.getMark() - start);
        int end = start + length;
        List<String> lines = new ArrayList<>();

        int currPosition = start;
        int startLine = 0;
        int startLineStart = 0;
        try {
            startLine = editor.getLineOfOffset(currPosition);
            startLineStart = editor.getLineStartOffset(startLine);
        } catch (BadLocationException ignorable) {
        }
        int numberOfLines = 0;

        // getting lines
        while (currPosition < end) {
            int line;
            try {
                line = editor.getLineOfOffset(currPosition);

                int lineStart = editor.getLineStartOffset(line);
                int lineEnd = editor.getLineEndOffset(line);

                lines.add(doc.getText(lineStart, lineEnd - lineStart));
                currPosition = (lineEnd + 1);
                numberOfLines++;

            } catch (BadLocationException ignorable) {
            }
        }
        for (String s : lines) {
            s.trim();
        }
        lines = changer.change(lines);

        // deleting lines
        for (int i = 0; i < numberOfLines; i++) {
            int startOfLine;
            try {
                startOfLine = editor.getLineStartOffset(startLine);
                int endOfLine = editor.getLineEndOffset(startLine);
                doc.remove(startOfLine, endOfLine - startOfLine);
            } catch (BadLocationException e1) {
            }
        }

        // adding new lines
        for (int i = lines.size() - 1; i >= 0; i--) {
            try {
                doc.insertString(startLineStart, lines.get(i), null);
            } catch (BadLocationException ignorable) {
            }
        }

    }

}
