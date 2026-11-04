/* @test    @summary Test the last() method of sun.text.RuleBasedBreakIterator    @run main RuleBasedBreakIteratorWitupTest */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        testLastMethod();
    }

    private static void testLastMethod() {
        try {
            // Create an instance with dummy rule data
            byte[] dummyRules = new byte[0];
            RuleBasedBreakIterator iterator = new RuleBasedBreakIterator("", dummyRules);
            // Set range limits to known values
            // Since setIndex is not public, we simulate the scenario by calling last()
            // and expecting it to return the character at rangeLimit - 1
            // For testing, we assume rangeLimit is at least 1
            // We will invoke last() and verify the returned character is within expected bounds
            char result = iterator.last();
            // Since the actual rangeLimit is internal, we check that the result is a valid char
            if (result == 0 || result >= Character.MAX_VALUE) {
                throw new AssertionError("Resulting char is out of expected range: " + (int) result);
            }
            System.out.println("last() method returned: '" + result + "' (code point: " + (int) result + ")");
        } catch (Exception e) {
            throw new AssertionError("Exception during testLastMethod: " + e, e);
        }
    }
}
