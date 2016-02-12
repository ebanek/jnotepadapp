package hr.fer.zemris.java.hw10.local;

/**
 * Specifies a component that does something when the language is changed.
 * 
 * @author Erik Banek
 */
public interface ILocalizationListener {
    /**
     * Updates information according to new language.
     */
    public void localizationChanged();
}
