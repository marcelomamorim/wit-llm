/*
 * @test
 * @summary Test java.time.OffsetDateTime.isEqual method behavior
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testIsEqualSameInstantSameNano();
        testIsEqualSameInstantDifferentNano();
        testIsEqualDifferentInstantSameNano();
        testIsEqualDifferentInstantDifferentNano();
    }

    private static void testIsEqualSameInstantSameNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2018, 9, 1, 12, 0, 0, 123456789, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 0, 0, 123456789), ZoneOffset.UTC);
        if (!dt1.isEqual(dt2)) {
            throw new AssertionError("Expected dt1 to be equal to dt2 when instants and nanos are same");
        }
    }

    private static void testIsEqualSameInstantDifferentNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2018, 9, 1, 12, 0, 0, 123456789, ZoneOffset.UTC);
        // Same epoch second but different nano-of-second
        OffsetDateTime dt2 = OffsetDateTime.of(2018, 9, 1, 12, 0, 0, 987654321, ZoneOffset.UTC);
        if (dt1.isEqual(dt2)) {
            throw new AssertionError("Expected dt1 not to be equal to dt2 when nanos differ but epoch second same");
        }
    }

    private static void testIsEqualDifferentInstantSameNano() {
        // Different epoch second (offset difference) but same nano-of-second
        OffsetDateTime dt1 = OffsetDateTime.of(2018, 9, 1, 12, 0, 0, 500000000, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2018, 9, 1, 13, 0, 0, 500000000, ZoneOffset.ofHours(1));
        // These represent the same instant, so isEqual should be true
        if (!dt1.isEqual(dt2)) {
            throw new AssertionError("Expected dt1 to be equal to dt2 when instants and nanos are same despite different local times");
        }
    }

    private static void testIsEqualDifferentInstantDifferentNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2018, 9, 1, 12, 0, 0, 500000000, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2018, 9, 1, 13, 0, 0, 600000000, ZoneOffset.ofHours(1));
        if (dt1.isEqual(dt2)) {
            throw new AssertionError("Expected dt1 not to be equal to dt2 when instants differ or nanos differ");
        }
    }
}
