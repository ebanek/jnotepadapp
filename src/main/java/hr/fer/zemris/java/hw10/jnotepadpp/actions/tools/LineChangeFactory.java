package hr.fer.zemris.java.hw10.jnotepadpp.actions.tools;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.local.ILocalizationProvider;

import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;

/**
 * Makes common actions that change currently selected lines.
 * 
 * @author Erik Banek
 */
public class LineChangeFactory {
    /**
     * Comparator of String that compares String according to current app
     * language. In ascending order.
     * 
     * @author Erik Banek
     */
    private static class Ascending implements Comparator<String> {
        /** Language provider of action. */
        private ILocalizationProvider lp;

        /**
         * Constructor.
         * 
         * @param lp
         *            language provider.
         */
        public Ascending(ILocalizationProvider lp) {
            this.lp = lp;
        }

        @Override
        public int compare(String o1, String o2) {
            String lang = lp.getLanguage();
            Locale locale = Locale.forLanguageTag(lang);
            Collator collator = Collator.getInstance(locale);
            return collator.compare(o1, o2);
        }

    }

    /**
     * Comparator of String that compares String according to current app
     * language. In descending order.
     * 
     * @author Erik Banek
     */
    private static class Descending implements Comparator<String> {
        /** Language provider. */
        private ILocalizationProvider lp;

        /**
         * Constructor.
         * 
         * @param lp
         *            language provider.
         */
        public Descending(ILocalizationProvider lp) {
            this.lp = lp;
        }

        @Override
        public int compare(String o1, String o2) {
            String lang = lp.getLanguage();
            Locale locale = Locale.forLanguageTag(lang);
            Collator collator = Collator.getInstance(locale);
            return -collator.compare(o1, o2);
        }
    }

    /**
     * Gets the action that sorts the currently selected lines according to
     * current language. In ascending order.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @return sort ascending action
     */
    public static LineChangeAction ascending(String key,
            ILocalizationProvider lp, JNotepadPP notepad) {
        LineChangeAction a = new LineChangeAction(key, lp, notepad, (list) -> {
            list.sort(new Ascending(lp));
            return addNewLineToLines(list);
        });
        a.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        a.setEnabled(false);
        return a;
    }

    /**
     * Gets the action that sorts the currently selected lines according to
     * current language. In descending order.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @return sort descending action
     */
    public static LineChangeAction descending(String key,
            ILocalizationProvider lp, JNotepadPP notepad) {
        LineChangeAction a = new LineChangeAction(key, lp, notepad, (list) -> {
            list.sort(new Descending(lp));
            return addNewLineToLines(list);
        });
        a.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        a.setEnabled(false);
        return a;
    }

    /**
     * Gets the action that makes the currently selected lines unique by keeping
     * only the first occurrence of each unique line.
     * 
     * @param key
     *            name of action.
     * @param lp
     *            language provider.
     * @param notepad
     *            owner of action.
     * @return make unique action
     */
    public static LineChangeAction unique(String key, ILocalizationProvider lp,
            JNotepadPP notepad) {
        LineChangeAction a = new LineChangeAction(key, lp, notepad, (list) -> {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(i).equals(list.get(j))) {
                        list.remove(j);
                    }
                }
            }
            return addNewLineToLines(list);
        });
        a.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        a.setEnabled(false);
        return a;
    }


    /**
     * Adds new line to Strings that do not already have newline at end.
     * 
     * @param lines
     *            List of Strings.
     * @return List of Strings with added newlines.
     */
    private static List<String> addNewLineToLines(List<String> lines) {
        List<String> newList = new ArrayList<>();
        for (String s : lines) {
            String a = s;
            if (!s.endsWith("\n")) {
                a = s.concat("\n");
            }
            newList.add(a);
        }
        return newList;
    }
}
