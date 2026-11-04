/*
 * @test
 * @summary White-box-derived behavioral tests for OffsetDateTime.isBefore covering null argument, nanosecond ordering, equal instants and offset normalization
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNullOtherThrowsNPE();
        testNanoOrdering();
        testEqualInstantsAreNotBefore();
        testEarlierEpochSecondIsBefore();
        testOffsetNormalization();
    }

    static void testNullOtherThrowsNPE() {
        OffsetDateTime a = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        try {
            a.isBefore(null);
            throw new RuntimeException("expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNanoOrdering() {
        OffsetDateTime a = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 1, ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 2, ZoneOffset.UTC);
        check(a.isBefore(b), "1ns should be before 2ns at same second");
        check(!b.isBefore(a), "2ns should not be before 1ns at same second");
    }

    static void testEqualInstantsAreNotBefore() {
        OffsetDateTime a = OffsetDateTime.of(2020, 6, 15, 12, 30, 45, 500, ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(2020, 6, 15, 12, 30, 45, 500, ZoneOffset.UTC);
        check(!a.isBefore(b), "equal values should not be before each other");
        check(!b.isBefore(a), "equal values should not be before each other (reverse)");
    }

    static void testEarlierEpochSecondIsBefore() {
        OffsetDateTime a = OffsetDateTime.of(2019, 12, 31, 23, 59, 59, 999999999, ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        check(a.isBefore(b), "earlier date should be before later date");
        check(!b.isBefore(a), "later date should not be before earlier date");
    }

    static void testOffsetNormalization() {
        OffsetDateTime a = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, ZoneOffset.ofHours(2));
        OffsetDateTime b = OffsetDateTime.of(2020, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC);
        check(a.isBefore(b), "10:00+02:00 is 08:00Z and should be before 09:00Z");
        check(!b.isBefore(a), "09:00Z should not be before 08:00Z");

        OffsetDateTime c = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime d = OffsetDateTime.of(2020, 1, 1, 11, 0, 0, 0, ZoneOffset.ofHours(1));
        check(!c.isBefore(d), "10:00Z and 11:00+01:00 are the same instant");
        check(!d.isBefore(c), "11:00+01:00 and 10:00Z are the same instant (reverse)");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
