package hr.fer.zemris.java.hw10.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class that holds the current language set information.
 * 
 * @author Erik Banek
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
    /** Instance of this singleton class. */
    private static final LocalizationProvider instance = new LocalizationProvider();

    /**
     * Gets the singleton.
     * 
     * @return instance of this singleton.
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /** Current language of provider. */
    private String language;
    /** Current bundle of the language that is set. */
    private ResourceBundle bundle;

    /**
     * Constructor of singleton class.
     */
    private LocalizationProvider() {
        this.language = "en";
        Locale locale = Locale.forLanguageTag(language);
        this.bundle = ResourceBundle.getBundle(
                "hr.fer.zemris.java.hw10.local.files.prijevodi", locale);
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public String getString(String key) {
        return this.bundle.getString(key);
    }

    /**
     * Sets the new language for the provider.
     * 
     * @param language
     *            new language to be set.
     */
    public void setLanguage(String language) {
        this.language = language;
        Locale locale = Locale.forLanguageTag(language);
        this.bundle = ResourceBundle.getBundle(
                "hr.fer.zemris.java.hw10.local.files.prijevodi", locale);
        fire();
    }
}
