/*
 * @test
 * @summary Checks observable results of LocalDate.toEpochSecond(LocalTime, ZoneOffset)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class LocalDateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        check(LocalDate.of(1970, 1, 1).toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) == 0L,
              "epoch zero at UTC midnight");
        check(LocalDate.of(1970, 1, 2).toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) == 86400L,
              "one day after epoch");
        check(LocalDate.of(1969, 12, 31).toEpochSecond(LocalTime.of(23, 59, 59), ZoneOffset.UTC) == -1L,
              "one second before epoch");
        check(LocalDate.of(2000, 1, 1).toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) == 946684800L,
              "year 2000 start");
        check(LocalDate.of(1970, 1, 1).toEpochSecond(LocalTime.of(12, 30, 15), ZoneOffset.UTC) == 45015L,
              "time of day is added");
        check(LocalDate.of(1970, 1, 1).toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.ofHours(1)) == -3600L,
              "positive offset subtracts seconds");
        check(LocalDate.of(1970, 1, 1).toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.ofHours(-5)) == 18000L,
              "negative offset adds seconds");
        check(LocalDate.of(1970, 1, 1).toEpochSecond(LocalTime.of(1, 0, 0), ZoneOffset.ofHours(1)) == 0L,
              "time and offset cancel out");

        boolean npeForTime = false;
        try {
            LocalDate.of(1970, 1, 1).toEpochSecond(null, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            npeForTime = true;
        }
        check(npeForTime, "null time must throw NullPointerException");

        boolean npeForOffset = false;
        try {
            LocalDate.of(1970, 1, 1).toEpochSecond(LocalTime.MIDNIGHT, null);
        } catch (NullPointerException e) {
            npeForOffset = true;
        }
        check(npeForOffset, "null offset must throw NullPointerException");
    }
}
