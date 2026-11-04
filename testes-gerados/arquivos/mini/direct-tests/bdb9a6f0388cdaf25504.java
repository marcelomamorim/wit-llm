/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int) factory method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testInvalidMonthNull();
        testInvalidDayOfMonth();
        testInvalidHour();
        testInvalidMinute();
    }

    private static void testValidDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59);
        if (ldt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (ldt.getMonth() != Month.DECEMBER) throw new AssertionError("Month mismatch");
        if (ldt.getDayOfMonth() != 31) throw new AssertionError("DayOfMonth mismatch");
        if (ldt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (ldt.getMinute() != 59) throw new AssertionError("Minute mismatch");
        if (ldt.getSecond() != 0) throw new AssertionError("Second should be zero");
        if (ldt.getNano() != 0) throw new AssertionError("Nano should be zero");
    }

    private static void testInvalidMonthNull() {
        try {
            LocalDateTime.of(2018, null, 1, 0, 0);
            throw new AssertionError("Expected NullPointerException for null Month");
        } catch (NullPointerException expected) {
        }
    }

    private static void testInvalidDayOfMonth() {
        try {
            LocalDateTime.of(2018, Month.FEBRUARY, 30, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid day of month");
        } catch (java.time.DateTimeException expected) {
        }
    }

    private static void testInvalidHour() {
        try {
            LocalDateTime.of(2018, Month.JANUARY, 1, 24, 0);
            throw new AssertionError("Expected DateTimeException for invalid hour");
        } catch (java.time.DateTimeException expected) {
        }
    }

    private static void testInvalidMinute() {
        try {
            LocalDateTime.of(2018, Month.JANUARY, 1, 0, 60);
            throw new AssertionError("Expected DateTimeException for invalid minute");
        } catch (java.time.DateTimeException expected) {
        }
    }
}
