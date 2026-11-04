/*
 * @test
 * @summary Verifies OffsetDateTime.isBefore for offset-normalized instants, epoch second ordering, nanosecond ordering and equality.
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

    static void testSameInstantDifferentOffsetsIsNotBefore() {
        OffsetDateTime a = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0), ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(11, 0), ZoneOffset.ofHours(1));
        check(!a.isBefore(b), "same instant: a.isBefore(b) should be false");
        check(!b.isBefore(a), "same instant: b.isBefore(a) should be false");
    }

    static void testEarlierEpochSecondIsBefore() {
        OffsetDateTime earlier = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0), ZoneOffset.UTC);
        OffsetDateTime later = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0, 1), ZoneOffset.UTC);
        check(earlier.isBefore(later), "earlier epoch second should be before");
        check(!later.isBefore(earlier), "later epoch second should not be before");
    }

    static void testOffsetAffectsOrdering() {
        OffsetDateTime utc = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0), ZoneOffset.UTC);
        OffsetDateTime plusTwo = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(11, 0), ZoneOffset.ofHours(2));
        check(!utc.isBefore(plusTwo), "10:00Z is equal to 11:00+02:00, not before");
        OffsetDateTime plusTwoLater = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(11, 0, 1), ZoneOffset.ofHours(2));
        check(utc.isBefore(plusTwoLater), "10:00Z should be before 11:00:01+02:00");
    }

    static void testSameEpochSecondNanoOrdering() {
        OffsetDateTime a = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0, 0, 1), ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0, 0, 2), ZoneOffset.UTC);
        check(a.isBefore(b), "smaller nano within same second should be before");
        check(!b.isBefore(a), "larger nano within same second should not be before");
    }

    static void testEqualValuesAreNotBefore() {
        OffsetDateTime a = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0, 0, 5), ZoneOffset.UTC);
        OffsetDateTime b = OffsetDateTime.of(LocalDate.of(2018, 9, 1), LocalTime.of(10, 0, 0, 5), ZoneOffset.UTC);
        check(!a.isBefore(b), "equal values should not be before");
        check(!a.isBefore(a), "value should not be before itself");
    }

    public static void main(String[] args) {
        testSameInstantDifferentOffsetsIsNotBefore();
        testEarlierEpochSecondIsBefore();
        testOffsetAffectsOrdering();
        testSameEpochSecondNanoOrdering();
        testEqualValuesAreNotBefore();
    }
}
