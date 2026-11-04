/*
 * @test
 * @summary Public BreakIterator word-instance behaviour sanity check; does not directly cover SafeCharIterator.setIndex
 * @run main RuleBasedBreakIteratorWitupTest
 */

import java.text.BreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        BreakIterator bi = BreakIterator.getWordInstance();
        bi.setText("ab cd");
        check(bi.first() == 0, "first() should return 0");
        check(bi.next() == 2, "next() should return 2 after 'ab'");
        check(bi.next() == 3, "next() should return 3 after the space");
        check(bi.last() == 5, "last() should return text length 5");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
