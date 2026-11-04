/*
 * @test
 * @summary Witup-generated checks for the RuleBasedCollator(String rules) constructor
 * @run main RuleBasedCollatorWitupTest
 */

import java.text.Collator;
import java.text.RuleBasedCollator;

public class RuleBasedCollatorWitupTest {

    public static void main(String[] args) throws Exception {
        RuleBasedCollator c = new RuleBasedCollator("< b < a");
        check(c.compare("b", "a") < 0, "rule order b < a not honored");
        check(c.getDecomposition() == Collator.CANONICAL_DECOMPOSITION,
              "single-arg constructor must use CANONICAL_DECOMPOSITION");

        RuleBasedCollator empty = new RuleBasedCollator("");
        check(empty.compare("x", "x") == 0, "equal strings must compare equal");

        RuleBasedCollator copy = new RuleBasedCollator(c.getRules());
        check(copy.compare("b", "a") < 0, "rules round-trip lost ordering");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
