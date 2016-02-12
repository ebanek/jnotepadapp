package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.local.ILocalizationListener;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Abstract representation of a tab (an opened document) in the gui of the
 * notepad.
 * 
 * @author Erik Banek
 */
public class JNotepadPPTab implements ILocalizationListener, INotepadListener {
    /** Editor in which the user edits the text of current opened document. */
    private JTextArea editor;
    /** Path of the current opened document, can be null. */
    private Path path;
    /** Component that renders the title of current tab in the tabbed pane. */
    private JNotepadPPTabHead title;
    /** Tells if modifications in current document have been saved. */
    private boolean saved;

    /**
     * Constructor of new tab that has no current path associated with it.
     * Delegates job.
     * 
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of tab.
     */
    public JNotepadPPTab(ILocalizationProvider lp, JNotepadPP notepad) {
        this("", null, lp, notepad);
    }

    /**
     * Constructor.
     * 
     * @param startText
     *            that will be contained in the editor of newly opened tab.
     * @param path
     *            path with which the current tab is associated.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of tab.
     */
    public JNotepadPPTab(String startText, Path path, ILocalizationProvider lp,
            JNotepadPP notepad) {
        lp.addLocalizationListener(this);
        this.editor = new JTextArea();
        editor.setText(startText);
        this.title = new JNotepadPPTabHead(lp);
        updatePath(path);
        updateSaved(path != null);
    }

    /**
     * Gets the editor of the tab.
     * 
     * @return editor of tab.
     */
    public JTextArea getEditor() {
        return editor;
    }

    /**
     * Gets the path of tab.
     * 
     * @return path of tab.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Gets the saved status of tab.
     * 
     * @return saved status.
     */
    public boolean getSaved() {
        return saved;
    }

    /**
     * Gets the component that renders the title of this tab in tabbed pane.
     * 
     * @return title component.
     */
    public JNotepadPPTabHead getTitle() {
        return title;
    }

    @Override
    public void localizationChanged() {
        title.setNewText(this.path);
        title.revalidate();
    }

    @Override
    public void update() {
        localizationChanged();
        updatePath(path);
    }

    /**
     * Sets the path this tab is associated with.
     * 
     * @param path
     *            with which this tab will be newly associated.
     */
    public void updatePath(Path path) {
        this.path = path;
        title.setNewText(path);
    }

    /**
     * Updates the saved status of current tab.
     * 
     * @param saved
     *            new saved status.
     */
    public void updateSaved(boolean saved) {
        this.saved = saved;
        title.setIcon(saved);
        title.revalidate();
    }
}
