package hr.fer.zemris.java.hw10.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract that implements some actions associated with adding and removing
 * listeners.
 * 
 * @author Erik Banek
 */
public abstract class AbstractLocalizationProvider implements
        ILocalizationProvider {
    private List<ILocalizationListener> listeners;

    /**
     * Constructor of localization provider.
     */
    public AbstractLocalizationProvider() {
        listeners = new ArrayList<ILocalizationListener>();
    }

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all listeners of the change.
     */
    public void fire() {
        for (ILocalizationListener listener : this.listeners) {
            listener.localizationChanged();
        }
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }
}
