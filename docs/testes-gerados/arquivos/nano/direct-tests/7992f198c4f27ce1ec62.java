/* @test    @summary Test constructor of RuleBasedCollator with valid rules string    @run main RuleBasedCollatorWitupTest */

import java.text.RuleBasedCollator;
import java.text.ParseException;

public class RuleBasedCollatorWitupTest {

    public static void main(String[] args) {
        testValidRules();
        testEmptyRules();
        testNullRules();
    }

    static void testValidRules() {
        String rules = "&a < b";
        try {
            RuleBasedCollator collator = new RuleBasedCollator(rules);
            if (collator == null) {
                throw new AssertionError("Constructor returned null for valid rules");
            }
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for valid rules", e);
        }
    }

    static void testEmptyRules() {
        String rules = "";
        try {
            RuleBasedCollator collator = new RuleBasedCollator(rules);
            if (collator == null) {
                throw new AssertionError("Constructor returned null for empty rules");
            }
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for empty rules", e);
        }
    }

    static void testNullRules() {
        String rules = null;
        try {
            new RuleBasedCollator(rules);
            throw new AssertionError("Expected NullPointerException for null rules");
        } catch (NullPointerException e) {
            // expected
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for null rules", e);
        }
    }
}
