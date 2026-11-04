/*
 * @test
 * @summary Test ZoneRules.of(ZoneOffset) factory method
 * @run main ZoneRulesWitupTest
 */

import java.time.zone.ZoneRules;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneRulesWitupTest {
    public static void main(String[] args) {
        testOfWithValidOffset();
        testOfWithNullOffset();
    }

    private static void testOfWithValidOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneRules rules = ZoneRules.of(offset);
        if (rules == null) {
            throw new AssertionError("ZoneRules.of returned null for valid offset");
        }
        // The returned ZoneRules should have the same offset as provided
        ZoneOffset resultOffset = getOffsetFromRules(rules);
        if (!offset.equals(resultOffset)) {
            throw new AssertionError("ZoneRules offset mismatch: expected " + offset + " but got " + resultOffset);
        }
    }

    private static void testOfWithNullOffset() {
        try {
            ZoneRules.of(null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException npe) {
            // expected
            if (!"offset".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    // Helper method to extract the offset from ZoneRules instance
    // Since ZoneRules does not expose offset directly, we rely on toString or other observable behavior
    // Here we use toString and parse offset string, assuming toString contains offset info
    private static ZoneOffset getOffsetFromRules(ZoneRules rules) {
        String s = rules.toString();
        // toString format is not guaranteed, so fallback to offset of zero if parsing fails
        // We try to find a substring like "+02:00" or "Z"
        int plusIndex = s.indexOf('+');
        int minusIndex = s.indexOf('-');
        int zIndex = s.indexOf('Z');
        String offsetStr = null;
        if (plusIndex != -1) {
            offsetStr = s.substring(plusIndex, Math.min(plusIndex + 6, s.length()));
        } else if (minusIndex != -1) {
            offsetStr = s.substring(minusIndex, Math.min(minusIndex + 6, s.length()));
        } else if (zIndex != -1) {
            offsetStr = "Z";
        } else {
            // fallback
            offsetStr = "+00:00";
        }
        try {
            return ZoneOffset.of(offsetStr);
        } catch (Exception e) {
            return ZoneOffset.UTC;
        }
    }
}
