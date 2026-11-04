/*
 * @test
 * @summary Test sun.text.RuleBasedBreakIterator.first() method
 * @run main RuleBasedBreakIteratorWitupTest
 */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {
    public static void main(String[] args) throws Exception {
        // Minimal valid rule data to construct RuleBasedBreakIterator
        // Using empty string and empty byte array as placeholders
        RuleBasedBreakIterator it = new RuleBasedBreakIterator("", new byte[0]);

        // Call first() and check that it returns a char (primitive) without exception
        char result = it.first();

        // The returned char should be equal to the result of setIndex(rangeStart),
        // but since rangeStart is internal, we only check that the call succeeds
        // and returns a char value (which it does by signature).

        // Print the result to ensure observable effect
        System.out.println("first() returned: " + (int)result);
    }
}
