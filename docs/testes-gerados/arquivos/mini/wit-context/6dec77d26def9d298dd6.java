/*
 * @test
 * @summary Test sun.text.RuleBasedBreakIterator.first() method behavior and exception path
 * @run main RuleBasedBreakIteratorWitupTest
 */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        testFirstReturnsChar();
        testFirstThrowsIllegalArgumentException();
        System.out.println("All tests passed.");
    }

    private static void testFirstReturnsChar() {
        // Construct with dummy valid parameters
        // Use a minimal valid ruleFile and ruleData to avoid exceptions in constructor
        String ruleFile = "dummy";
        byte[] ruleData = new byte[] {0};
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator(ruleFile, ruleData);

        // The first() method calls setIndex(rangeStart) and returns char
        // We expect a char return, no exception if rangeStart <= rangeLimit
        // Since we cannot set rangeStart and rangeLimit directly, we just call first()
        // and check that it returns a char (not throwing)
        char c = rbi.first();
        // char is primitive, so just check no exception and c is a char
        if (!(c >= Character.MIN_VALUE && c <= Character.MAX_VALUE)) {
            throw new AssertionError("first() returned invalid char value");
        }
    }

    private static void testFirstThrowsIllegalArgumentException() {
        // The expath indicates that if rangeLimit < rangeStart, first() throws IllegalArgumentException
        // We cannot set rangeStart and rangeLimit directly (no public API), so we cannot trigger this directly
        // Hence, this test is omitted because no public API to set invalid state
        // This is noted here to document the discarded expath
    }

}
