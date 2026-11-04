/* @test    @summary Test RuleBasedBreakIterator.first() method for correct behavior    @run main RuleBasedBreakIteratorWitupTest */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        testFirstMethod();
    }

    private static void testFirstMethod() {
        // Create a dummy rule file content or byte array, as the constructor requires a String and byte[]
        String ruleFile = ""; // empty rule file for testing
        byte[] ruleData = new byte[0]; // empty rule data
        RuleBasedBreakIterator iterator = new RuleBasedBreakIterator(ruleFile, ruleData);
        // The rangeStart is an internal variable; assuming default is 0, so first() should return setIndex(0)
        char result = iterator.first();
        // Expecting the result to be the character at rangeStart, which is 0
        if (result != 0) {
            throw new AssertionError("Expected first() to return 0, but got: " + (int) result);
        }
        // Additional check: calling first() again should still return 0
        char result2 = iterator.first();
        if (result2 != 0) {
            throw new AssertionError("Subsequent first() call did not return 0, got: " + (int) result2);
        }
        System.out.println("testFirstMethod passed.");
    }
}
