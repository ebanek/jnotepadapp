package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.model.NotepadAction;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.local.swing.LJMenu;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 * The window in which the tabs, bar, toolbar and menu is shown.
 * 
 * @author Erik Banek
 */
public class JNotepadPPGUI extends JFrame implements INotepadListener {
    private static final long serialVersionUID = 1L;
    /** Tabs in the gui. */
    private JTabbedPane tabPane;
    /** Holds language information. */
    private ILocalizationProvider lp;
    /** Owner of this frame. */
    private JNotepadPP notepad;

    /**
     * Adds a tab to the gui.
     * 
     * @param tab
     *            tab that will be added to the gui.
     */
    public void addTab(JNotepadPPTab tab) {
        int index = tabPane.getTabCount();
        tabPane.insertTab("", null, new JScrollPane(tab.getEditor()), "", index);
        tab.getTitle().setVisible(true);
        tabPane.setTabComponentAt(index, tab.getTitle());
        setTab(index);
    }

    /**
     * Gets the tabbed pane of this notepad.
     * 
     * @return tabbed pane of this notepad, which holds the visual tabs.
     */
    public JTabbedPane getTabbedPane() {
        return tabPane;
    }

    /**
     * Initializes the gui of notepad.
     * 
     * @param actions
     *            which will be added to the gui.
     * @param lp
     *            language provider.
     * @param bar
     *            bar of notepad.
     * @param notepad
     *            owner of everything.
     */
    public void init(Map<String, NotepadAction> actions,
            ILocalizationProvider lp, JNotepadPPBar bar, JNotepadPP notepad) {
        this.notepad = notepad;
        this.lp = lp;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(bar, BorderLayout.SOUTH);
        panel.add(tabPane = new JTabbedPane(SwingConstants.TOP),
                BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        setTitle("JNotepad++");
        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(500, 500);

        initMenu(actions);
        initToolBar(actions);
    }

    /**
     * Initializes the menu.
     * 
     * @param actions
     *            which are added to the menu.
     */
    private void initMenu(Map<String, NotepadAction> actions) {
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        LJMenu menu = new LJMenu("file", lp);
        menu.setMnemonic(KeyEvent.VK_F);

        LJMenu menu5 = new LJMenu("edit", lp);
        menu5.setMnemonic(KeyEvent.VK_E);
        menu.add(new JMenuItem(actions.get("openNew")));
        menu.add(new JMenuItem(actions.get("openExisting")));
        menu.add(new JMenuItem(actions.get("close")));
        menu.add(new JMenuItem(actions.get("save")));
        menu.add(new JMenuItem(actions.get("saveAs")));
        menu5.add(new JMenuItem(actions.get("copy")));
        menu5.add(new JMenuItem(actions.get("paste")));
        menu5.add(new JMenuItem(actions.get("cut")));
        menu.add(new JMenuItem(actions.get("showStatz")));
        menu.add(new JMenuItem(actions.get("exit")));
        menubar.add(menu);
        menubar.add(menu5);

        LJMenu menu2 = new LJMenu("languages", lp);
        menu2.setMnemonic(KeyEvent.VK_L);
        menu2.add(new JMenuItem(actions.get("en")));
        menu2.add(new JMenuItem(actions.get("de")));
        menu2.add(new JMenuItem(actions.get("hr")));
        menubar.add(menu2);

        LJMenu menu3 = new LJMenu("tools", lp);
        menu3.setMnemonic(KeyEvent.VK_T);
        LJMenu submenuChange = new LJMenu("changeCase", lp);
        submenuChange.setMnemonic(KeyEvent.VK_C);
        menu3.add(submenuChange);
        submenuChange.add(new JMenuItem(actions.get("lowerCase")));
        submenuChange.add(new JMenuItem(actions.get("upperCase")));
        submenuChange.add(new JMenuItem(actions.get("invertCase")));

        LJMenu submenuSort = new LJMenu("sort", lp);
        submenuSort.setMnemonic(KeyEvent.VK_S);
        menu3.add(submenuSort);
        submenuSort.add(new JMenuItem(actions.get("ascending")));
        submenuSort.add(new JMenuItem(actions.get("descending")));

        menu3.add(new JMenuItem(actions.get("unique")));

        menubar.add(menu3);
    }

    /**
     * Initializes the toolbar of the notepad.
     * 
     * @param actions
     *            which are added to the toolbar of this frame.
     */
    private void initToolBar(Map<String, NotepadAction> actions) {
        JToolBar toolbar = new JToolBar();
        toolbar.add(actions.get("openNew"));
        toolbar.add(actions.get("openExisting"));
        toolbar.add(actions.get("close"));
        toolbar.add(actions.get("save"));
        toolbar.add(actions.get("saveAs"));
        toolbar.add(actions.get("copy"));
        toolbar.add(actions.get("paste"));
        toolbar.add(actions.get("cut"));
        toolbar.add(actions.get("showStatz"));
        toolbar.add(actions.get("exit"));
        getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Removes the tab in the tabbed pane with the given index.
     * 
     * @param index
     *            of the tab to be removed.
     */
    public void removeTab(int index) {
        tabPane.remove(index);
        setTab(index - 1);
    }

    /**
     * Sets the tab in the tabbed pane.
     * 
     * @param index
     *            of the tab in the pane to be set.
     */
    public void setTab(int index) {
        if (index == -1 && !(tabPane.getTabCount() == 0)) {
            index++;
        }
        tabPane.setSelectedIndex(index);
    }

    @Override
    public void update() {
        if (getTabbedPane().getSelectedIndex() == -1) {
            setTitle("JNotepad++");
            return;
        }

        JNotepadPPTab tab = notepad.getCurrentTab();
        if (tab == null) {
            setTitle(lp.getString("untitled") + " - JNotepad++");
            return;
        }
        Path path = tab.getPath();
        if (path == null) {
            setTitle(lp.getString("untitled") + " - JNotepad++");
            return;
        }

        String text = path.toString() + " - " + "JNotepad++";
        setTitle(text);
    }
}
