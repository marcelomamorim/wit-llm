/*
 * @test
 * @summary Checks first() of the word BreakIterator on empty and non-empty text, observed through the public java.text API
 * @run main RuleBasedBreakIteratorWitupTest
 */

import java.text.BreakIterator;
import java.util.Locale;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        BreakIterator bi = BreakIterator.getWordInstance(Locale.US);

        bi.setText("hello world");
        int first = bi.first();
        if (first != 0) {
            throw new RuntimeException("first() expected 0 but was " + first);
        }
        if (bi.current() != 0) {
            throw new RuntimeException("current() after first() expected 0 but was " + bi.current());
        }

        bi.setText("");
        int firstEmpty = bi.first();
        if (firstEmpty != 0) {
            throw new RuntimeException("first() on empty text expected 0 but was " + firstEmpty);
        }
        if (bi.next() != BreakIterator.DONE) {
            throw new RuntimeException("next() on empty text expected DONE");
        }

        bi.setText("abc def");
        bi.last();
        int again = bi.first();
        if (again != 0) {
            throw new RuntimeException("first() after last() expected 0 but was " + again);
        }
    }
}
