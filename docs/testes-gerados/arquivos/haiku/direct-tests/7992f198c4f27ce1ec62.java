/*
 * @test
 * @summary Verifies that RuleBasedCollator(String) builds a working collator with canonical decomposition and ordered rules
 * @run main RuleBasedCollatorWitupTest
 */

import java.text.Collator;
import java.text.RuleBasedCollator;

public class RuleBasedCollatorWitupTest {
    public static void main(String[] args) throws Exception {
        RuleBasedCollator c = new RuleBasedCollator("& a < b < c");

        if (c.compare("a", "b") >= 0) {
            throw new RuntimeException("a should sort before b");
        }
        if (c.compare("b", "c") >= 0) {
            throw new RuntimeException("b should sort before c");
        }
        if (c.compare("c", "a") <= 0) {
            throw new RuntimeException("c should sort after a");
        }
        if (c.getDecomposition() != Collator.CANONICAL_DECOMPOSITION) {
            throw new RuntimeException("Constructor should delegate with CANONICAL_DECOMPOSITION");
        }

        String composed = "\u00E1";
        String decomposed = "a\u0301";
        if (c.compare(composed, decomposed) != 0) {
            throw new RuntimeException("Canonically equivalent strings should compare equal");
        }

        if (c.getRules() == null) {
            throw new RuntimeException("getRules() should not return null");
        }
    }
}
