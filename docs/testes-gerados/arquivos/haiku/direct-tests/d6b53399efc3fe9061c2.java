/*
 * @test
 * @summary Verifies ZoneRules.of(ZoneOffset) rejects null and creates fixed-offset rules
 * @run main ZoneRulesWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.List;

public class ZoneRulesWitupTest {

    public static void main(String[] args) {
        testNullOffsetRejected();
        testFixedOffsetRulesReturnOffset();
        testUtcFixedRulesEqualityAndOffset();
        testNegativeFixedOffset();
    }

    static void testNullOffsetRejected() {
        try {
            ZoneRules.of(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null offset");
    }

    static void testFixedOffsetRulesReturnOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneRules rules = ZoneRules.of(offset);
        check(rules.isFixedOffset(), "rules should be fixed offset");
        check(offset.equals(rules.getOffset(Instant.EPOCH)), "getOffset(Instant) should return the fixed offset");
        check(offset.equals(rules.getStandardOffset(Instant.EPOCH)), "getStandardOffset(Instant) should return the fixed offset");
        List<ZoneOffset> valid = rules.getValidOffsets(LocalDateTime.of(2020, 1, 1, 0, 0));
        check(valid.size() == 1, "exactly one valid offset expected");
        check(offset.equals(valid.get(0)), "valid offset should be the fixed offset");
    }

    static void testUtcFixedRulesEqualityAndOffset() {
        ZoneRules rules = ZoneRules.of(ZoneOffset.UTC);
        check(rules.isFixedOffset(), "UTC rules should be fixed offset");
        check(ZoneOffset.UTC.equals(rules.getOffset(Instant.EPOCH)), "UTC offset expected");
        check(rules.equals(ZoneRules.of(ZoneOffset.UTC)), "rules for the same offset should be equal");
    }

    static void testNegativeFixedOffset() {
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(-5, -30);
        ZoneRules rules = ZoneRules.of(offset);
        check(rules.isFixedOffset(), "negative offset rules should be fixed");
        check(offset.equals(rules.getOffset(Instant.EPOCH)), "negative fixed offset should be returned");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
