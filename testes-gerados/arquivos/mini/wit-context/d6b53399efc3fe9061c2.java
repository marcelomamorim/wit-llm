/*
 * @test
 * @summary Test ZoneRules.of(ZoneOffset) method for null input and valid offset
 * @run main ZoneRulesWitupTest
 */

import java.time.zone.ZoneRules;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneRulesWitupTest {
    public static void main(String[] args) {
        testOfWithNullOffset();
        testOfWithValidOffset();
    }

    private static void testOfWithNullOffset() {
        try {
            ZoneRules.of(null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            if (!"offset".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testOfWithValidOffset() {
        ZoneOffset offset = ZoneOffset.UTC;
        ZoneRules rules = ZoneRules.of(offset);
        if (rules == null) {
            throw new AssertionError("ZoneRules.of returned null for valid offset");
        }
    }
}
