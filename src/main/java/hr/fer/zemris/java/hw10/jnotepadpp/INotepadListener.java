package hr.fer.zemris.java.hw10.jnotepadpp;

/**
 * Specifies a notepad listener that receives updates from the notepad every
 * time anything changes.
 * 
 * @author Erik Banek
 */
public interface INotepadListener {
    /**
     * Updates this listener.
     */
    default public void update() {
    }
}
