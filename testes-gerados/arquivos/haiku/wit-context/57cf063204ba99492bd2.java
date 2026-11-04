/*
 * @test
 * @summary WITUP-derived tests for OffsetDateTime.isAfter
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        OffsetDateTime odt = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneOffset.UTC);
        boolean thrown = false;
        try {
            odt.isAfter(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "isAfter(null) must throw NullPointerException");
    }

    static void testSameInstantDifferentOffsetsIsNotAfter() {
        OffsetDateTime a = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneOffset.ofHours(1));
        OffsetDateTime b = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneOffset.ofHours(2));
        check(a.isAfter(b), "10:15:30+01:00 must be after 10:15:30+02:00");
        check(!b.isAfter(a), "10:15:30+02:00 must not be after 10:15:30+01:00");
    }

    static void testEqualValuesAreNotAfter() {
        OffsetDateTime a = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 5, ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 5, ZoneOffset.UTC);
        check(!a.isAfter(b), "equal values must not be after each other");
        check(!b.isAfter(a), "equal values must not be after each other (reverse)");
    }

    static void testNanosTieBreak() {
        OffsetDateTime a = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 1, ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneOffset.UTC);
        check(a.isAfter(b), "larger nano of same second must be after");
        check(!b.isAfter(a), "smaller nano of same second must not be after");
    }

    static void testDifferentEpochSecondsAcrossDays() {
        OffsetDateTime earlier = OffsetDateTime.of(2007, 12, 3, 23, 59, 59, 0, ZoneOffset.UTC);
        OffsetDateTime later = OffsetDateTime.of(2007, 12, 4, 0, 0, 0, 0, ZoneOffset.UTC);
        check(later.isAfter(earlier), "next day must be after previous day");
        check(!earlier.isAfter(later), "previous day must not be after next day");
    }

    static void testOffsetAffectsOrdering() {
        OffsetDateTime utc = OffsetDateTime.of(LocalDate.of(2007, 12, 3), LocalTime.of(12, 0), ZoneOffset.UTC);
        OffsetDateTime minus5 = OffsetDateTime.of(LocalDate.of(2007, 12, 3), LocalTime.of(12, 0), ZoneOffset.ofHours(-5));
        check(minus5.isAfter(utc), "12:00-05:00 is later in instant than 12:00Z");
        check(!utc.isAfter(minus5), "12:00Z is not later than 12:00-05:00");
    }

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testSameInstantDifferentOffsetsIsNotAfter();
        testEqualValuesAreNotAfter();
        testNanosTieBreak();
        testDifferentEpochSecondsAcrossDays();
        testOffsetAffectsOrdering();
    }
}
