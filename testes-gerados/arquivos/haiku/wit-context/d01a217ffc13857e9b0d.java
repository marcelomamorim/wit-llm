/*
 * @test
 * @summary Witup-derived checks for ZonedDateTime.of(LocalDate, LocalTime, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testValidComposition();
        testGapShiftsForward();
        testNullDateThrowsNpe();
        testNullTimeThrowsNpe();
        testNullZoneThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidComposition() {
        LocalDate date = LocalDate.of(2018, 3, 10);
        LocalTime time = LocalTime.of(10, 30);
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
        check(date.equals(zdt.toLocalDate()), "local date must be preserved");
        check(time.equals(zdt.toLocalTime()), "local time must be preserved");
        check(zone.equals(zdt.getZone()), "zone must be preserved");
        check(zdt.getOffset().getTotalSeconds() == 0, "UTC offset must be zero");
    }

    static void testGapShiftsForward() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2018, 3, 11),
                LocalTime.of(2, 30),
                ZoneId.of("America/New_York"));
        check(LocalTime.of(3, 30).equals(zdt.toLocalTime()),
                "local time in DST gap must shift forward to 03:30");
        check(ZoneOffset.of("-04:00").equals(zdt.getOffset()),
                "offset after gap shift must be -04:00");
    }

    static void testNullDateThrowsNpe() {
        try {
            ZonedDateTime.of(null, LocalTime.of(10, 0), ZoneId.of("UTC"));
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("null date must throw NullPointerException");
    }

    static void testNullTimeThrowsNpe() {
        try {
            ZonedDateTime.of(LocalDate.of(2018, 1, 1), null, ZoneId.of("UTC"));
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("null time must throw NullPointerException");
    }

    static void testNullZoneThrowsNpe() {
        try {
            ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.of(10, 0), null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("null zone must throw NullPointerException");
    }
}
