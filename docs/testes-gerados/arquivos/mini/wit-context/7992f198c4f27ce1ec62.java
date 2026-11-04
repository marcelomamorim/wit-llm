/*
 * @test
 * @summary Test RuleBasedCollator(String rules) constructor for valid and invalid inputs
 * @run main RuleBasedCollatorWitupTest
 */

import java.text.RuleBasedCollator;
import java.text.ParseException;
import java.text.Collator;

public class RuleBasedCollatorWitupTest {
    public static void main(String[] args) throws Exception {
        testValidRules();
        testNullRules();
        testEmptyRules();
    }

    private static void testValidRules() throws Exception {
        // Basic valid rules string: simple ordering
        String rules = "&a < b < c";
        RuleBasedCollator collator = new RuleBasedCollator(rules);
        // Check that collator is not null and can compare strings
        if (collator == null) {
            throw new RuntimeException("Constructor returned null");
        }
        int result = collator.compare("a", "b");
        if (result >= 0) {
            throw new RuntimeException("Expected 'a' < 'b' according to rules");
        }
    }

    private static void testNullRules() {
        try {
            new RuleBasedCollator(null);
            throw new RuntimeException("Expected NullPointerException for null rules");
        } catch (NullPointerException e) {
            // Expected
        } catch (ParseException e) {
            // Also acceptable if implementation throws ParseException for null
        }
    }

    private static void testEmptyRules() {
        try {
            new RuleBasedCollator("");
            // Empty rules string is allowed and creates a collator
        } catch (ParseException e) {
            // Acceptable if empty rules cause ParseException
        }
    }
}
