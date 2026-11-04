/*
 * @test
 * @summary Verifies OffsetDateTime.toEpochSecond() returns the expected epoch seconds for various offsets and values
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toEpochSecond() == 0L,
              "epoch zero at UTC");

        check(OffsetDateTime.of(1970, 1, 1, 1, 0, 0, 0, ZoneOffset.ofHours(1)).toEpochSecond() == 0L,
              "local 01:00 at +01:00 must be epoch zero");

        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(-5)).toEpochSecond() == 18000L,
              "local midnight at -05:00 must be 18000 seconds after epoch");

        check(OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toEpochSecond() == 946684800L,
              "2000-01-01T00:00Z must be 946684800");

        check(OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 999999999, ZoneOffset.UTC).toEpochSecond() == 0L,
              "nanoseconds must not affect epoch seconds");

        check(OffsetDateTime.of(1969, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC).toEpochSecond() == -1L,
              "one second before epoch must be -1");

        check(OffsetDateTime.of(LocalDateTime.of(2000, 1, 1, 0, 0), ZoneOffset.UTC).toEpochSecond() == 946684800L,
              "LocalDateTime factory must yield same epoch seconds");

        check(OffsetDateTime.of(LocalDate.of(1970, 1, 2), LocalTime.MIDNIGHT, ZoneOffset.UTC).toEpochSecond() == 86400L,
              "one day after epoch must be 86400 seconds");

        check(OffsetDateTime.of(1970, 1, 1, 2, 0, 0, 0, ZoneOffset.ofHours(2)).toEpochSecond()
              == OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC).toEpochSecond(),
              "same instant expressed with different offsets must have equal epoch seconds");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
