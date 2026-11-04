/*
 * @test
 * @summary Test sun.text.RuleBasedBreakIterator.setIndex(int) method
 * @run main RuleBasedBreakIteratorWitupTest
 */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        testSetIndexWithinRange();
        testSetIndexBelowRange();
        testSetIndexAboveRange();
    }

    private static void testSetIndexWithinRange() throws Exception {
        // Create a minimal RuleBasedBreakIterator with rangeStart=0 and rangeLimit=10
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator("", new byte[0]);

        // Use reflection to set rangeStart and rangeLimit to known values
        java.lang.reflect.Field fStart = RuleBasedBreakIterator.class.getDeclaredField("rangeStart");
        java.lang.reflect.Field fLimit = RuleBasedBreakIterator.class.getDeclaredField("rangeLimit");
        java.lang.reflect.Field fCurrent = RuleBasedBreakIterator.class.getDeclaredField("currentIndex");
        fStart.setAccessible(true);
        fLimit.setAccessible(true);
        fCurrent.setAccessible(true);
        fStart.setInt(rbi, 0);
        fLimit.setInt(rbi, 10);

        // Set currentIndex to 0 initially
        fCurrent.setInt(rbi, 0);

        // Call setIndex with a valid index inside the range
        char c = rbi.setIndex(5);

        // The returned char should be equal to current()
        // current() returns char at currentIndex, which is 5
        // We cannot assert exact char value, but we can assert currentIndex updated
        int currentIndex = fCurrent.getInt(rbi);
        if (currentIndex != 5) {
            throw new AssertionError("currentIndex not updated correctly");
        }
    }

    private static void testSetIndexBelowRange() throws Exception {
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator("", new byte[0]);

        java.lang.reflect.Field fStart = RuleBasedBreakIterator.class.getDeclaredField("rangeStart");
        java.lang.reflect.Field fLimit = RuleBasedBreakIterator.class.getDeclaredField("rangeLimit");
        fStart.setAccessible(true);
        fLimit.setAccessible(true);
        fStart.setInt(rbi, 0);
        fLimit.setInt(rbi, 10);

        try {
            rbi.setIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for index below range");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetIndexAboveRange() throws Exception {
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator("", new byte[0]);

        java.lang.reflect.Field fStart = RuleBasedBreakIterator.class.getDeclaredField("rangeStart");
        java.lang.reflect.Field fLimit = RuleBasedBreakIterator.class.getDeclaredField("rangeLimit");
        fStart.setAccessible(true);
        fLimit.setAccessible(true);
        fStart.setInt(rbi, 0);
        fLimit.setInt(rbi, 10);

        try {
            rbi.setIndex(11);
            throw new AssertionError("Expected IllegalArgumentException for index above range");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
