/*
 * @test
 * @summary Witup-derived observable tests for OffsetDateTime.toEpochSecond
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toEpochSecond(), 0L, "epoch UTC midnight");
        check(OffsetDateTime.of(1970, 1, 1, 1, 0, 0, 0, ZoneOffset.ofHours(1)).toEpochSecond(), 0L, "epoch +01:00 at 01:00");
        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(1)).toEpochSecond(), -3600L, "offset +01:00 shifts to previous hour");
        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(-5)).toEpochSecond(), 18000L, "offset -05:00 shifts forward");
        check(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toEpochSecond(), 946684800L, "year 2000 start");
        check(OffsetDateTime.of(1969, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC).toEpochSecond(), -1L, "one second before epoch");
        check(OffsetDateTime.of(1970, 1, 2, 0, 0, 0, 0, ZoneOffset.UTC).toEpochSecond(), 86400L, "one day after epoch");
        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 999999999, ZoneOffset.UTC).toEpochSecond(), 0L, "nanoseconds are ignored");
    }

    static void check(long actual, long expected, String label) {
        if (actual != expected) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }
}
