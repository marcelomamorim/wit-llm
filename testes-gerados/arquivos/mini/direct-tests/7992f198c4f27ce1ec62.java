/*
 * @test
 * @summary Test RuleBasedCollator constructor with valid and invalid rules
 * @run main RuleBasedCollatorWitupTest
 */

import java.text.ParseException;
import java.text.RuleBasedCollator;

public class RuleBasedCollatorWitupTest {
    public static void main(String[] args) throws Exception {
        testValidRules();
        testInvalidRules();
    }

    private static void testValidRules() throws Exception {
        // Minimal valid rules string
        String rules = "&a < b";
        RuleBasedCollator collator = new RuleBasedCollator(rules);
        if (collator == null) {
            throw new RuntimeException("RuleBasedCollator constructor returned null");
        }
        // Check that collator compares 'a' less than 'b'
        int result = collator.compare("a", "b");
        if (result >= 0) {
            throw new RuntimeException("Expected 'a' < 'b' according to rules, but got " + result);
        }
    }

    private static void testInvalidRules() {
        // Invalid rules string that should cause ParseException
        String invalidRules = "&a <"; // incomplete rule
        try {
            new RuleBasedCollator(invalidRules);
            throw new RuntimeException("Expected ParseException for invalid rules but none thrown");
        } catch (ParseException e) {
            // Expected exception
        }
    }
}
