/*
 * @test
 * @summary OffsetTime.toEpochSecond(LocalDate) computes epoch seconds from local time and offset
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        checkEquals(0L,
                OffsetTime.of(LocalTime.MIDNIGHT, ZoneOffset.UTC).toEpochSecond(LocalDate.of(1970, 1, 1)),
                "midnight UTC at epoch");

        checkEquals(0L,
                OffsetTime.of(LocalTime.of(1, 0), ZoneOffset.ofHours(1)).toEpochSecond(LocalDate.of(1970, 1, 1)),
                "01:00 at +01:00 should be epoch zero");

        checkEquals(61200L,
                OffsetTime.of(LocalTime.NOON, ZoneOffset.ofHours(-5)).toEpochSecond(LocalDate.of(1970, 1, 1)),
                "12:00 at -05:00 should be 17:00 UTC");

        checkEquals(-86400L,
                OffsetTime.of(LocalTime.MIDNIGHT, ZoneOffset.UTC).toEpochSecond(LocalDate.of(1969, 12, 31)),
                "previous day should be minus one day");

        checkEquals(0L,
                OffsetTime.of(LocalTime.of(0, 0, 0, 999999999), ZoneOffset.UTC).toEpochSecond(LocalDate.of(1970, 1, 1)),
                "nanoseconds must not affect epoch seconds");

        LocalDate[] dates = {
            LocalDate.of(1969, 12, 31),
            LocalDate.of(1970, 1, 1),
            LocalDate.of(2018, 9, 1),
            LocalDate.of(2000, 2, 29)
        };
        LocalTime[] times = {
            LocalTime.MIDNIGHT,
            LocalTime.NOON,
            LocalTime.of(23, 59, 59, 999999999)
        };
        ZoneOffset[] offsets = {
            ZoneOffset.UTC,
            ZoneOffset.ofHours(-12),
            ZoneOffset.ofHours(14),
            ZoneOffset.ofHoursMinutesSeconds(5, 30, 0)
        };
        for (LocalDate d : dates) {
            for (LocalTime t : times) {
                for (ZoneOffset o : offsets) {
                    long expected = LocalDateTime.of(d, t).toEpochSecond(o);
                    long actual = OffsetTime.of(t, o).toEpochSecond(d);
                    checkEquals(expected, actual, "mismatch with LocalDateTime for " + d + " " + t + " " + o);
                }
            }
        }

        boolean npeThrown = false;
        try {
            OffsetTime.of(LocalTime.NOON, ZoneOffset.UTC).toEpochSecond(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("null date must throw NullPointerException");
        }
    }

    static void checkEquals(long expected, long actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but got " + actual);
        }
    }
}
