/* @test    @summary Test setIndex method of sun.text.RuleBasedBreakIterator    @run main RuleBasedBreakIteratorWitupTest */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        testSetIndexWithinRange();
        testSetIndexBelowRange();
        testSetIndexAboveRange();
    }

    private static void testSetIndexWithinRange() {
        RuleBasedBreakIterator iterator = new RuleBasedBreakIterator("", new byte[0]);
        int validPosition = 0;
        try {
            char result = iterator.setIndex(validPosition);
            System.out.println("setIndex within range passed, result: " + result);
        } catch (Exception e) {
            throw new AssertionError("setIndex within range threw an exception", e);
        }
    }

    private static void testSetIndexBelowRange() {
        RuleBasedBreakIterator iterator = new RuleBasedBreakIterator("", new byte[0]);
        int invalidPosition = -1;
        boolean exceptionThrown = false;
        try {
            iterator.setIndex(invalidPosition);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("setIndex below range did not throw IllegalArgumentException");
        } else {
            System.out.println("setIndex below range correctly threw IllegalArgumentException");
        }
    }

    private static void testSetIndexAboveRange() {
        RuleBasedBreakIterator iterator = new RuleBasedBreakIterator("", new byte[0]);
        int invalidPosition = Integer.MAX_VALUE;
        boolean exceptionThrown = false;
        try {
            iterator.setIndex(invalidPosition);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("setIndex above range did not throw IllegalArgumentException");
        } else {
            System.out.println("setIndex above range correctly threw IllegalArgumentException");
        }
    }
}
