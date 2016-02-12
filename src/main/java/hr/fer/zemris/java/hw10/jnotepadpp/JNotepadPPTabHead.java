package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Renders the title in the tabbed pane of tab.
 * 
 * @author Erik Banek
 */
public class JNotepadPPTabHead extends JLabel {
    private static final long serialVersionUID = 1L;
    /** Dimension of the title bar. */
    private static final Dimension DIMENSION = new Dimension(140, 20);
    /** Dimension of the button that closes the tab of this title. */
    private static final Dimension BUTTON_DIMENSION = new Dimension(12, 12);
    /** Language provider. */
    private ILocalizationProvider lp;
    /** Icon which tells the user the saved status of tab. */
    private ImageIcon icon;
    /** Button which closes the tab of this title. */
    private JButton button;

    /**
     * Constructs a new title with the given provider.
     * 
     * @param lp
     *            language provider.
     */
    public JNotepadPPTabHead(ILocalizationProvider lp) {
        this.lp = lp;
        setIcon(false);
        setIcon(icon);

        button = new JButton();
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(BUTTON_DIMENSION);

        setLayout(new BorderLayout());
        add(button, BorderLayout.LINE_END);
        setPreferredSize(DIMENSION);
    }

    /**
     * Gets the closing button of current tab.
     * 
     * @return closing button.
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Sets the icon according to the saved status of tab.
     * 
     * @param saved
     *            status according to which the icon will be set.
     */
    public void setIcon(boolean saved) {
        icon = saved ? new ImageIcon("images/green.png", "File saved")
                : new ImageIcon("images/red.png", "Unsaved");
        setIcon(icon);
        revalidate();
    }

    /**
     * Sets the text of title according to the new path of tab.
     * 
     * @param path
     *            new path of tab.
     */
    public void setNewText(Path path) {
        if (path == null) {
            setText(lp.getString("untitled"));
            setToolTipText(null);
        } else {
            setToolTipText(path.toString());
            setText(path.toFile().getName());
        }
    }
}
