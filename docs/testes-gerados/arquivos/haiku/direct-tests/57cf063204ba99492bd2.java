/*
 * @test
 * @summary Verifies OffsetDateTime.isAfter compares instants across offsets and nanoseconds
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ZoneOffset utc = ZoneOffset.UTC;
        ZoneOffset plusOne = ZoneOffset.ofHours(1);
        ZoneOffset plusTwo = ZoneOffset.ofHours(2);

        OffsetDateTime base = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, utc);
        OffsetDateTime later = OffsetDateTime.of(2020, 1, 1, 10, 0, 1, 0, utc);
        OffsetDateTime earlier = OffsetDateTime.of(2020, 1, 1, 9, 59, 59, 0, utc);
        OffsetDateTime sameCopy = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, utc);

        // Equal instants must not be after each other
        check(!base.isAfter(sameCopy), "equal instants: isAfter must be false");
        check(!sameCopy.isAfter(base), "equal instants (reverse): isAfter must be false");

        // Ordering by epoch seconds
        check(later.isAfter(base), "later second must be after base");
        check(!base.isAfter(later), "base must not be after later second");
        check(base.isAfter(earlier), "base must be after earlier second");
        check(!earlier.isAfter(base), "earlier must not be after base");

        // Same instant with different offsets: 10:00Z == 11:00+01:00 == 12:00+02:00
        OffsetDateTime plusOneSameInstant = OffsetDateTime.of(2020, 1, 1, 11, 0, 0, 0, plusOne);
        OffsetDateTime plusTwoSameInstant = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 0, plusTwo);
        check(!plusOneSameInstant.isAfter(base), "same instant with +01:00 must not be after UTC");
        check(!base.isAfter(plusOneSameInstant), "UTC must not be after same instant with +01:00");
        check(!plusTwoSameInstant.isAfter(base), "same instant with +02:00 must not be after UTC");
        check(!base.isAfter(plusTwoSameInstant), "UTC must not be after same instant with +02:00");

        // Different offsets, different instants
        OffsetDateTime laterWithOffset = OffsetDateTime.of(2020, 1, 1, 11, 0, 1, 0, plusOne);
        check(laterWithOffset.isAfter(base), "later instant with offset must be after UTC base");
        check(!base.isAfter(laterWithOffset), "UTC base must not be after later instant with offset");

        // Same epoch second, nanosecond-of-second decides
        OffsetDateTime nanoLow = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 1, utc);
        OffsetDateTime nanoHigh = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 2, utc);
        check(nanoHigh.isAfter(nanoLow), "higher nano must be after lower nano");
        check(!nanoLow.isAfter(nanoHigh), "lower nano must not be after higher nano");
        check(!nanoLow.isAfter(nanoLow), "value must not be after itself");

        // Nanosecond comparison across offsets within the same second
        OffsetDateTime nanoHighOffset = OffsetDateTime.of(2020, 1, 1, 11, 0, 0, 2, plusOne);
        check(nanoHighOffset.isAfter(nanoLow), "offset value with higher nano must be after lower nano UTC");
        check(!nanoLow.isAfter(nanoHighOffset), "lower nano UTC must not be after higher nano with offset");

        // Null argument: other.toEpochSecond() dereferences other
        boolean npe = false;
        try {
            base.isAfter(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "isAfter(null) must throw NullPointerException");
    }
}
