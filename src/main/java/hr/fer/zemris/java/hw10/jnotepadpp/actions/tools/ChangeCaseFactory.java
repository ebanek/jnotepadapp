package hr.fer.zemris.java.hw10.jnotepadpp.actions.tools;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.KeyEvent;

import javax.swing.Action;

/**
 * Makes common actions that change currently selected text.
 * 
 * @author Erik Banek
 */
public class ChangeCaseFactory {
    /**
     * Gets the new action that inverts the case of currently selected String.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @return invert case action.
     */
    public static ChangeCaseAction invertCase(String key,
            ILocalizationProvider lp, JNotepadPP notepad) {
        ChangeCaseAction a = new ChangeCaseAction(key, lp, notepad, (s) -> {

            char[] charArray = s.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                Character c = charArray[i];
                if (Character.isLowerCase(c)) {
                    charArray[i] = Character.toUpperCase(c);
                } else if (Character.isUpperCase(c)) {
                    charArray[i] = Character.toLowerCase(c);
                }
            }
            return String.valueOf(charArray);
        });
        a.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
        a.setEnabled(false);
        return a;
    }

    /**
     * Gets the new action that changes the case of currently selected String to
     * lower.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @return to lower case action.
     */
    public static ChangeCaseAction lowerCase(String key,
            ILocalizationProvider lp, JNotepadPP notepad) {
        ChangeCaseAction a = new ChangeCaseAction(key, lp, notepad, (s) -> {

            char[] charArray = s.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                Character c = charArray[i];
                charArray[i] = Character.toLowerCase(c);
            }
            return String.valueOf(charArray);
        });
        a.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        a.setEnabled(false);
        return a;
    }

    /**
     * Gets the new action that changes the case of currently selected String to
     * upper.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @return to upper case action.
     */
    public static ChangeCaseAction upperCase(String key,
            ILocalizationProvider lp, JNotepadPP notepad) {
        ChangeCaseAction a = new ChangeCaseAction(key, lp, notepad, (s) -> {

            char[] charArray = s.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                Character c = charArray[i];
                charArray[i] = Character.toUpperCase(c);
            }
            return String.valueOf(charArray);
        });
        a.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        a.setEnabled(false);
        return a;
    }
}
