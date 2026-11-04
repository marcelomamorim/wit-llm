/*
 * @test
 * @summary Verifies RuleBasedBreakIterator.last() leaves the iterator consistent with the returned position
 * @modules java.base/sun.text
 * @run main RuleBasedBreakIteratorWitupTest
 */

import java.text.BreakIterator;
import java.util.Locale;
import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        checkLast("hello world");
        checkLast("a");
        checkLast("");
    }

    static void checkLast(String text) {
        BreakIterator bi = BreakIterator.getWordInstance(Locale.US);
        if (!(bi instanceof RuleBasedBreakIterator)) {
            throw new RuntimeException("Expected RuleBasedBreakIterator but was " + bi.getClass());
        }
        bi.setText(text);
        int end = bi.last();
        if (end < 0 || end > text.length()) {
            throw new RuntimeException("last() out of range for '" + text + "': " + end);
        }
        if (bi.current() != end) {
            throw new RuntimeException("current() " + bi.current() + " != last() " + end + " for '" + text + "'");
        }
        if (text.isEmpty() && end != 0) {
            throw new RuntimeException("last() on empty text must be 0 but was " + end);
        }
    }
}
