package hr.fer.zemris.java.hw10.jnotepadpp.actions.tools;

import java.util.List;

/**
 * Specifies a transformation which occurs on a list of lines.
 * 
 * @author Erik Banek
 */
public interface ILineChanger {
    /**
     * Makes a transformation on a list of lines.
     * 
     * @param lines
     *            to transform.
     * @return transformed lines.
     */
    public List<String> change(List<String> lines);
}
