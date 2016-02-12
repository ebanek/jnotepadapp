package hr.fer.zemris.java.hw10.local.swing;

import hr.fer.zemris.java.hw10.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.local.LocalizationProviderBridge;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Localization bridge that disconnects itself according to some JFrame.
 * 
 * @author Erik Banek
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
    /**
     * Constructor.
     * 
     * @param provider
     *            to which the bridge delegates the job of language providing.
     * @param frame
     *            whose disposal means disconnection of the bridge.
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }
        });
    }
}
