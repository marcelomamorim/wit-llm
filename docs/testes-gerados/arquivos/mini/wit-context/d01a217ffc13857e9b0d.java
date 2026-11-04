/*
 * @test
 * @summary Test java.time.ZonedDateTime.of(LocalDate, LocalTime, ZoneId) for null arguments and normal behavior
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testOfNullDate();
        testOfNullTime();
        testOfNullZone();
        testOfValidInputs();
    }

    private static void testOfNullDate() {
        LocalTime time = LocalTime.NOON;
        ZoneId zone = ZoneId.of("UTC");
        try {
            ZonedDateTime.of(null, time, zone);
            throw new AssertionError("Expected NullPointerException for null date");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfNullTime() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        ZoneId zone = ZoneId.of("UTC");
        try {
            ZonedDateTime.of(date, null, zone);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfNullZone() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalTime time = LocalTime.NOON;
        try {
            ZonedDateTime.of(date, time, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfValidInputs() {
        LocalDate date = LocalDate.of(2018, 9, 1);
        LocalTime time = LocalTime.of(13, 45, 30, 123456789);
        ZoneId zone = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(date, time, zone);
        if (!zdt.toLocalDate().equals(date)) {
            throw new AssertionError("Date mismatch");
        }
        if (!zdt.toLocalTime().equals(time)) {
            throw new AssertionError("Time mismatch");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("Zone mismatch");
        }
    }
}
