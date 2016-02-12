package hr.fer.zemris.java.hw10.jnotepadpp.actions.tools;

/**
 * Specifies a transformation that occurs on a String.
 * 
 * @author Erik Banek
 */
public interface ICaseChanger {
    /**
     * Changes a String.
     * 
     * @param textToChange
     *            String to be changed.
     * @return changed String.
     */
    public String change(String textToChange);
}
