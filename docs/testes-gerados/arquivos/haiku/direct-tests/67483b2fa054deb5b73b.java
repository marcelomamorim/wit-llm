/*
 * @test
 * @summary Verifies RuleBasedBreakIterator.setIndex bounds checking and returned character
 * @modules java.base/sun.text
 * @run main RuleBasedBreakIteratorWitupTest
 */

import java.text.BreakIterator;
import java.text.StringCharacterIterator;
import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        BreakIterator bi = BreakIterator.getWordInstance();
        if (!(bi instanceof RuleBasedBreakIterator)) {
            return;
        }
        RuleBasedBreakIterator rbbi = (RuleBasedBreakIterator) bi;
        String text = "abc";
        rbbi.setText(text);

        char c = rbbi.setIndex(0);
        if (c != 'a') {
            throw new RuntimeException("Expected 'a' at index 0 but got: " + c);
        }

        boolean thrownBelow = false;
        try {
            rbbi.setIndex(-1);
        } catch (IllegalArgumentException e) {
            thrownBelow = true;
        }
        if (!thrownBelow) {
            throw new RuntimeException("Expected IllegalArgumentException for index -1");
        }

        boolean thrownAbove = false;
        try {
            rbbi.setIndex(text.length() + 1);
        } catch (IllegalArgumentException e) {
            thrownAbove = true;
        }
        if (!thrownAbove) {
            throw new RuntimeException("Expected IllegalArgumentException for index above end");
        }
    }
}
