package hr.fer.zemris.java.hw10.local;

/**
 * Provides current lanugage information.
 * 
 * @author Erik Banek
 */
public interface ILocalizationProvider {
    /**
     * Adds a listener to the list of listeners of this provider.
     * 
     * @param listener
     *            to be added.
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Gets the current language String which is set in this provider.
     * 
     * @return language String.
     */
    String getLanguage();

    /**
     * Gets the String that is associated with given key in the current
     * language.
     * 
     * @param key
     *            whose value in current language is wanted.
     * @return String of value in current language.
     */
    String getString(String key);

    /**
     * Removes a localization listener from the list of listeners.
     * 
     * @param listener
     *            to be removed.
     */
    void removeLocalizationListener(ILocalizationListener listener);
}
