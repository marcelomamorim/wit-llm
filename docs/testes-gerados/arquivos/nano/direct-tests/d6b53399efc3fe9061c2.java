/*
 * @test
 * @summary Test ZoneRules.of(ZoneOffset) method for correctness and null handling
 * @run main ZoneRulesWitupTest
 */

import java.time.ZoneOffset;
import java.time.zone.ZoneRules;

public class ZoneRulesWitupTest {

    public static void main(String[] args) {
        testValidOffset();
        testNullOffset();
    }

    static void testValidOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneRules rules = ZoneRules.of(offset);
        if (rules == null) {
            throw new AssertionError("ZoneRules.of should not return null for valid offset");
        }
        if (!rules.equals(ZoneRules.of(offset))) {
            throw new AssertionError("ZoneRules.of should produce consistent results for same offset");
        }
    }

    static void testNullOffset() {
        try {
            ZoneRules.of(null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
