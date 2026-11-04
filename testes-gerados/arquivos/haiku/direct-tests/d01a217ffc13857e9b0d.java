/*
 * @test
 * @summary Witup-style tests for ZonedDateTime.of(LocalDate, LocalTime, ZoneId)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testBasicFields();
        testGapIsAdjusted();
        testOverlapUsesEarlierOffset();
        testNullDate();
        testNullZone();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicFields() {
        LocalDate date = LocalDate.of(2018, 6, 15);
        LocalTime time = LocalTime.of(10, 20, 30, 400);
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
        check(zdt.toLocalDate().equals(date), "local date mismatch");
        check(zdt.toLocalTime().equals(time), "local time mismatch");
        check(zdt.getZone().equals(zone), "zone mismatch");
        check(zdt.getOffset().equals(ZoneOffset.UTC), "offset mismatch for UTC");
    }

    static void testGapIsAdjusted() {
        // 2018-03-11 02:30 does not exist in America/New_York (DST gap)
        LocalDate date = LocalDate.of(2018, 3, 11);
        LocalTime time = LocalTime.of(2, 30);
        ZonedDateTime zdt = ZonedDateTime.of(date, time, ZoneId.of("America/New_York"));
        check(zdt.getHour() == 3, "gap: hour should move to 3, was " + zdt.getHour());
        check(zdt.getMinute() == 30, "gap: minute should remain 30");
        check(zdt.getOffset().getTotalSeconds() == -4 * 3600, "gap: offset should be -04:00");
    }

    static void testOverlapUsesEarlierOffset() {
        // 2018-11-04 01:30 occurs twice in America/New_York; earlier offset (EDT) is used
        LocalDate date = LocalDate.of(2018, 11, 4);
        LocalTime time = LocalTime.of(1, 30);
        ZonedDateTime zdt = ZonedDateTime.of(date, time, ZoneId.of("America/New_York"));
        check(zdt.getHour() == 1 && zdt.getMinute() == 30, "overlap: local time changed");
        check(zdt.getOffset().getTotalSeconds() == -4 * 3600, "overlap: expected earlier offset -04:00");
    }

    static void testNullDate() {
        boolean thrown = false;
        try {
            ZonedDateTime.of(null, LocalTime.NOON, ZoneId.of("UTC"));
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null date should throw NullPointerException");
    }

    static void testNullZone() {
        boolean thrown = false;
        try {
            ZonedDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.NOON, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null zone should throw NullPointerException");
    }
}
