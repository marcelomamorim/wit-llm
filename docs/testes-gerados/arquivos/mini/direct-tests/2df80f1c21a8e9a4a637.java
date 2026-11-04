/*
 * @test
 * @summary Test java.time.OffsetDateTime.toEpochSecond() method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testEpochSecondAtEpoch();
        testEpochSecondPositiveOffset();
        testEpochSecondNegativeOffset();
        testEpochSecondWithTime();
    }

    private static void testEpochSecondAtEpoch() {
        OffsetDateTime odt = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        long epochSecond = odt.toEpochSecond();
        if (epochSecond != 0L) {
            throw new AssertionError("Expected 0 epoch second at 1970-01-01T00:00:00Z but got " + epochSecond);
        }
    }

    private static void testEpochSecondPositiveOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, offset);
        long epochSecond = odt.toEpochSecond();
        // Local time 1970-01-01T00:00:00+02:00 is 1969-12-31T22:00:00Z
        if (epochSecond != -7200L) {
            throw new AssertionError("Expected -7200 epoch second for offset +02:00 but got " + epochSecond);
        }
    }

    private static void testEpochSecondNegativeOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(-3);
        OffsetDateTime odt = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, offset);
        long epochSecond = odt.toEpochSecond();
        // Local time 1970-01-01T00:00:00-03:00 is 1970-01-01T03:00:00Z
        if (epochSecond != 10800L) {
            throw new AssertionError("Expected 10800 epoch second for offset -03:00 but got " + epochSecond);
        }
    }

    private static void testEpochSecondWithTime() {
        ZoneOffset offset = ZoneOffset.UTC;
        OffsetDateTime odt = OffsetDateTime.of(2020, 2, 29, 23, 59, 59, 999_999_999, offset);
        long epochSecond = odt.toEpochSecond();
        // 2020-02-29T23:59:59Z epoch second calculation
        long expected = 1583020799L;
        if (epochSecond != expected) {
            throw new AssertionError("Expected " + expected + " epoch second for 2020-02-29T23:59:59Z but got " + epochSecond);
        }
    }
}
