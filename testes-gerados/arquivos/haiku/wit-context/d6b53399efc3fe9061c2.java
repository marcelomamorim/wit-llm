/*
 * @test
 * @summary ZoneRules.of(ZoneOffset) rejects null and returns fixed-offset rules for a given offset
 * @run main ZoneRulesWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.List;

public class ZoneRulesWitupTest {

    public static void main(String[] args) {
        testNullOffsetThrowsNPE();
        testFixedOffsetRules();
    }

    static void testNullOffsetThrowsNPE() {
        try {
            ZoneRules.of((ZoneOffset) null);
            throw new RuntimeException("NullPointerException expected for null offset");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFixedOffsetRules() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneRules rules = ZoneRules.of(offset);
        check(rules.isFixedOffset(), "rules should be fixed offset");
        check(offset.equals(rules.getOffset(Instant.EPOCH)), "offset at epoch mismatch");
        check(offset.equals(rules.getStandardOffset(Instant.EPOCH)), "standard offset mismatch");
        check(!rules.isDaylightSavings(Instant.EPOCH), "fixed rules must not be daylight savings");
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 0, 0);
        List<ZoneOffset> valid = rules.getValidOffsets(ldt);
        check(valid.size() == 1 && offset.equals(valid.get(0)), "valid offsets mismatch");
        check(rules.getTransition(ldt) == null, "fixed rules must have no transition");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
