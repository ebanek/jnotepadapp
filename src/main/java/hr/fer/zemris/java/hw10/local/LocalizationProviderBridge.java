package hr.fer.zemris.java.hw10.local;

/**
 * Bridges the provider so that when components are removed, the singleton
 * doesn't have references to components, but to an instance of this class. Then
 * the garbage collecting of components is enabled. Delegates providing to the
 * provider it is connected to.
 * 
 * @author Erik Banek
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    /** True iff this bridge is connected to instance of localization provider. */
    private boolean connected;
    /** Current provider with which this bridge is connected. */
    private ILocalizationProvider provider;
    /** Listener that notifies this class of the change in the provider. */
    private ILocalizationListener listener;

    /**
     * Constructor of a disconnected bridge.
     * 
     * @param provider
     *            with which this bridge can be connected.
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.provider = provider;
        this.connected = false;
    }

    /**
     * Connects the bridge to the provider.
     */
    public void connect() {
        if (connected) {
            return;
        }
        this.listener = new ILocalizationListener() {
            @Override
            public void localizationChanged() {
                fire();
            }
        };
        provider.addLocalizationListener(listener);
        connected = true;
    }

    /**
     * Disconnects the bridge with the provider, thus enabling garbage
     * collecting.
     */
    public void disconnect() {
        if (!connected) {
            return;
        }
        provider.removeLocalizationListener(listener);
        listener = null;
        connected = false;
    }

    @Override
    public String getLanguage() {
        return provider.getLanguage();
    }

    @Override
    public String getString(String key) {
        return provider.getString(key);
    }
}
