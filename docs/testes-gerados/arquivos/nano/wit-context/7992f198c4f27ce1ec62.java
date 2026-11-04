/* @test    @summary Test RuleBasedCollator constructor with valid and invalid rules    @run main RuleBasedCollatorWitupTest */

import java.text.RuleBasedCollator;
import java.text.ParseException;

public class RuleBasedCollatorWitupTest {

    public static void main(String[] args) throws Exception {
        testValidRules();
        testInvalidRules();
    }

    private static void testValidRules() {
        String validRules = "&a < b"; // simple valid rule
        try {
            RuleBasedCollator collator = new RuleBasedCollator(validRules);
            System.out.println("Valid rules: constructor succeeded.");
        } catch (ParseException e) {
            throw new RuntimeException("Valid rules: constructor threw ParseException", e);
        }
    }

    private static void testInvalidRules() {
        String invalidRules = "&a <"; // invalid rule, should throw ParseException
        try {
            new RuleBasedCollator(invalidRules);
            throw new AssertionError("Invalid rules: constructor did not throw ParseException");
        } catch (ParseException e) {
            System.out.println("Invalid rules: constructor threw ParseException as expected.");
        }
    }
}
