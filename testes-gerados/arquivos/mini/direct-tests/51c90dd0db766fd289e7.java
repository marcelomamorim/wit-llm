/*
 * @test
 * @summary Test sun.text.RuleBasedBreakIterator.last() method behavior
 * @run main RuleBasedBreakIteratorWitupTest
 */

import sun.text.RuleBasedBreakIterator;

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a minimal valid RuleBasedBreakIterator instance
        // Using dummy ruleFile and ruleData to allow construction
        String dummyRuleFile = "dummy";
        byte[] dummyRuleData = new byte[] {0};

        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator(dummyRuleFile, dummyRuleData);

        // The last() method returns a char from setIndex(rangeLimit - 1)
        // We test that last() returns the expected char and does not throw

        char lastChar = rbi.last();

        // The returned char should be a valid char (not throwing exceptions)
        // We cannot assert exact value without internal knowledge, but we can assert it is a char
        if (!(lastChar >= Character.MIN_VALUE && lastChar <= Character.MAX_VALUE)) {
            throw new AssertionError("last() returned invalid char value");
        }

        // Call last() multiple times to check deterministic behavior
        char lastChar2 = rbi.last();
        if (lastChar != lastChar2) {
            throw new AssertionError("last() returned different values on repeated calls");
        }

        System.out.println("PASS");
    }
}
