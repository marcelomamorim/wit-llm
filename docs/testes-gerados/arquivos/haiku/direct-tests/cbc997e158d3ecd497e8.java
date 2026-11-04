/*
 * @test
 * @summary Verifies OffsetDateTime.toInstant() converts local date-time and offset to the correct UTC instant
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testUtcOffset();
        testPositiveOffset();
        testNegativeOffset();
        testNanosPreserved();
        testOffsetChangesInstant();
        testConsistencyWithEpochSecond();
    }

    static void testUtcOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        Instant expected = Instant.ofEpochSecond(1577836800L, 0);
        check(expected.equals(odt.toInstant()), "UTC offset conversion");
    }

    static void testPositiveOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 1, 2, 0, 0, 0, ZoneOffset.ofHours(2));
        Instant expected = Instant.ofEpochSecond(1577836800L, 0);
        check(expected.equals(odt.toInstant()), "positive offset conversion");
    }

    static void testNegativeOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2019, 12, 31, 19, 0, 0, 0, ZoneOffset.ofHours(-5));
        Instant expected = Instant.ofEpochSecond(1577836800L, 0);
        check(expected.equals(odt.toInstant()), "negative offset conversion");
    }

    static void testNanosPreserved() {
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 123456789, ZoneOffset.UTC);
        Instant instant = odt.toInstant();
        check(instant.getEpochSecond() == 1577836800L, "epoch second with nanos");
        check(instant.getNano() == 123456789, "nano of second preserved");
    }

    static void testOffsetChangesInstant() {
        OffsetDateTime utc = OffsetDateTime.of(2020, 6, 15, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime plusOne = OffsetDateTime.of(2020, 6, 15, 12, 0, 0, 0, ZoneOffset.ofHours(1));
        long diff = utc.toInstant().getEpochSecond() - plusOne.toInstant().getEpochSecond();
        check(diff == 3600L, "same local time with +01:00 must be one hour earlier in UTC");
    }

    static void testConsistencyWithEpochSecond() {
        OffsetDateTime odt = OffsetDateTime.of(1999, 12, 31, 23, 59, 59, 500, ZoneOffset.ofHoursMinutes(3, 30));
        Instant instant = odt.toInstant();
        check(instant.getEpochSecond() == odt.toEpochSecond(), "epoch second matches toEpochSecond()");
        check(instant.getNano() == odt.getNano(), "nano matches getNano()");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
