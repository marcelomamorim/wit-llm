/*
 * @test
 * @summary Public BreakIterator.last() behavior for character instances, including empty text
 * @run main RuleBasedBreakIteratorWitupTest
 */

import java.text.BreakIterator;
import java.util.Locale;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        BreakIterator bi = BreakIterator.getCharacterInstance(Locale.US);

        bi.setText("abc");
        int last = bi.last();
        if (last != 3) {
            throw new RuntimeException("Expected last() == 3 for \"abc\" but was " + last);
        }

        bi.setText("");
        last = bi.last();
        if (last != 0) {
            throw new RuntimeException("Expected last() == 0 for empty text but was " + last);
        }
    }
}
