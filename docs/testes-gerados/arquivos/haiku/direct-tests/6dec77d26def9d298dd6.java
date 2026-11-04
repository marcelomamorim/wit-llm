/*
 * @test
 * @summary Verifies that first() on a RuleBasedBreakIterator (via the public BreakIterator API) returns the start of the text and resets the position.
 * @run main RuleBasedBreakIteratorWitupTest
 */

import java.text.BreakIterator;
import java.util.Locale;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        BreakIterator bi = BreakIterator.getWordInstance(Locale.US);

        bi.setText("Hello world");
        check(bi.first() == 0, "first() on fresh iterator should return 0");
        check(bi.current() == 0, "current() after first() should be 0");

        bi.next();
        bi.next();
        check(bi.first() == 0, "first() after next() should return 0");
        check(bi.current() == 0, "current() after first() following next() should be 0");

        bi.setText("");
        check(bi.first() == 0, "first() on empty text should return 0");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
