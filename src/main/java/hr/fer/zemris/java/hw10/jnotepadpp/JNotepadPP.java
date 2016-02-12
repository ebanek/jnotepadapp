package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.LanguageChangeAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.ShowStatzAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.TabButtonClose;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.CloseTabAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.OpenExistingDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.OpenNewDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.SaveAsDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.document.SaveDocumentAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.edit.CopyTextAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.edit.CutTextAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.edit.PasteTextAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.NotepadAction;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.tools.ChangeCaseFactory;
import hr.fer.zemris.java.hw10.jnotepadpp.actions.tools.LineChangeFactory;
import hr.fer.zemris.java.hw10.local.LocalizationProvider;
import hr.fer.zemris.java.hw10.local.swing.FormLocalizationProvider;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Model of the notepad. Handles creating actions, adding, removing tabs, and
 * the frame through which it communicates with the user. Notifies all listeners
 * for each action that happens.
 * 
 * @author Erik Banek
 */
public class JNotepadPP {
    /**
     * Private DocListener that delegates its notifying to the notepad.
     * 
     * @author Erik Banek
     */
    private class DocListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateListeners();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateListeners();
            currentTab.updateSaved(false);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateListeners();
            currentTab.updateSaved(false);
        }

    }

    /**
     * Main program that starts a notepad session.
     * 
     * @param args
     *            ignored
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JNotepadPP notepad = new JNotepadPP();
            notepad.gui.setVisible(true);
        });
    }

    /** Localization provider of current notepad. */
    private FormLocalizationProvider flp;
    /** Current tab that the user has opened. */
    private JNotepadPPTab currentTab;
    /** Frame in which the notepad is shown. */
    private JNotepadPPGUI gui;
    /**
     * Map of all actions that the notepad currently implements, for use in
     * adding them to the frame of notepad.
     */
    private Map<String, NotepadAction> actions;
    /** Bar which show the information of the current editor. */
    private JNotepadPPBar bar;
    /**
     * List of tabs in notepad, one on one with notepads in the tabbed pane in
     * gui.
     */
    private List<JNotepadPPTab> tabs;
    /** All listeners of this notepad. */
    private List<INotepadListener> listeners;
    /** Custom document listener instance. */
    private final DocumentListener docListener = new DocListener();

    /**
     * Constructor. Initializes gui, bar, tab list and all actions.
     */
    public JNotepadPP() {
        listeners = new ArrayList<>();
        gui = new JNotepadPPGUI();
        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(),
                gui);
        generateActions();

        bar = new JNotepadPPBar(this, flp, gui);
        gui.init(actions, flp, bar, this);

        gui.getTabbedPane().addChangeListener((e) -> {
            updateListeners();
        });
        currentTab = null;
        tabs = new ArrayList<>();

        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actions.get("exit").actionPerformed(null);
            }
        });
        listeners.add(gui);
        listeners.add(bar);
    }

    /**
     * Adds a new tab to the notepad and initializes its components.
     * 
     * @param path
     *            path of new tab to be added.
     * @param startText
     *            starting text to be contained in the editor of the new tab.
     */
    public void addTab(Path path, String startText) {
        final JNotepadPPTab tab = ((path == null) ? new JNotepadPPTab(flp, this)
        : new JNotepadPPTab(startText, path, flp, this));

        tabs.add(tab);

        tab.getEditor().addCaretListener((e) -> {
            updateListeners();
        });
        tab.getEditor().getDocument().addDocumentListener(docListener);

        tab.getTitle().getButton()
        .setAction(new TabButtonClose(flp, this, gui, tab));

        tab.getTitle().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCurrentTab(tab);
            }
        });

        gui.addTab(tab);
        setCurrentTab(tab);
        updateListeners();
    }

    /**
     * Closes a tab in the notepad.
     * 
     * @param tab
     *            tab that should be closed in the current notepad.
     */
    public void closeTab(JNotepadPPTab tab) {
        int index = tabs.indexOf(tab);
        gui.removeTab(index);
        tabs.remove(tab);
        removeListener(tab);

        if (gui.getTabbedPane().getSelectedIndex() == -1) {
            currentTab = null;
        } else {
            setCurrentTab(tabs.get(gui.getTabbedPane().getSelectedIndex()));
        }

        updateListeners();
    }

    /**
     * Initializes the map of all currently implemented actions.
     */
    private void generateActions() {
        actions = new HashMap<>();
        actions.put("openNew", new OpenNewDocumentAction("openNew", flp, this,
                gui));
        actions.put("openExisting", new OpenExistingDocumentAction(
                "openExisting", flp, this, gui));
        actions.put("close", new CloseTabAction("close", flp, this, gui));
        actions.put("saveAs",
                new SaveAsDocumentAction("saveAs", flp, this, gui));
        actions.put("save", new SaveDocumentAction("save", flp, this, gui));
        actions.put("copy", new CopyTextAction("copy", flp, this));
        actions.put("paste", new PasteTextAction("paste", flp, this));
        actions.put("cut", new CutTextAction("cut", flp, this));
        actions.put("showStatz", new ShowStatzAction("showStatz", flp, this,
                gui));
        actions.put("exit", new ExitAction("exit", flp, this, gui));
        NotepadAction de = new LanguageChangeAction("de", flp, this);
        NotepadAction en = new LanguageChangeAction("en", flp, this);
        NotepadAction hr = new LanguageChangeAction("hr", flp, this);
        de.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        en.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        hr.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
        actions.put("de", de);
        actions.put("en", en);
        actions.put("hr", hr);
        actions.put("upperCase",
                ChangeCaseFactory.upperCase("upperCase", flp, this));
        actions.put("lowerCase",
                ChangeCaseFactory.lowerCase("lowerCase", flp, this));
        actions.put("invertCase",
                ChangeCaseFactory.invertCase("invertCase", flp, this));
        actions.put("unique", LineChangeFactory.unique("unique", flp, this));
        actions.put("ascending",
                LineChangeFactory.ascending("ascending", flp, this));
        actions.put("descending",
                LineChangeFactory.descending("descending", flp, this));

        for (NotepadAction listener : actions.values()) {
            listeners.add(listener);
        }
    }

    /**
     * Gets the current tab that is opened in the notepad.
     * 
     * @return current tab of notepad.
     */
    public JNotepadPPTab getCurrentTab() {
        return currentTab;
    }

    /**
     * Removes a notepad listener from the list of listeners.
     * 
     * @param listener
     *            to be removed from the list of listeners.
     */
    public void removeListener(INotepadListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the current tab of notepad to the argument.
     * 
     * @param tab
     *            new current tab of notepad.
     */
    public void setCurrentTab(JNotepadPPTab tab) {
        currentTab = tab;
        gui.setTab(tabs.indexOf(currentTab));
        updateListeners();
    }

    /**
     * Sets the path of current tab.
     * 
     * @param p
     *            new path that the current tab will be set to.
     */
    public void setPath(Path p) {
        currentTab.updatePath(p);
        updateListeners();
    }

    /**
     * Sets the is saved status of the current tab opened in the notepad.
     * 
     * @param b
     *            new value of the saved status of current tab.
     */
    public void setSaved(boolean b) {
        currentTab.updateSaved(b);
    }

    /**
     * Notifies all listeners that something in the notepad has changed.
     */
    public void updateListeners() {
        for (INotepadListener listener : listeners) {
            listener.update();
        }
    }
}
