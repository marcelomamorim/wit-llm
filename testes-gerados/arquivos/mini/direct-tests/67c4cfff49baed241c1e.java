/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int, int, int) factory method
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
        testInvalidDate();
        testInvalidTime();
    }

    private static void testValidDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59, 59, 999999999);
        if (ldt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (ldt.getMonth() != Month.DECEMBER) throw new AssertionError("Month mismatch");
        if (ldt.getDayOfMonth() != 31) throw new AssertionError("Day mismatch");
        if (ldt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (ldt.getMinute() != 59) throw new AssertionError("Minute mismatch");
        if (ldt.getSecond() != 59) throw new AssertionError("Second mismatch");
        if (ldt.getNano() != 999999999) throw new AssertionError("Nano mismatch");
    }

    private static void testInvalidMonthNull() {
        try {
            LocalDateTime.of(2018, null, 1, 0, 0, 0, 0);
            throw new AssertionError("Expected NullPointerException for null Month");
        } catch (NullPointerException expected) {
        }
    }

    private static void testInvalidDate() {
        try {
            LocalDateTime.of(2018, Month.FEBRUARY, 30, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid day of month");
        } catch (java.time.DateTimeException expected) {
        }
    }

    private static void testInvalidTime() {
        try {
            LocalDateTime.of(2018, Month.JANUARY, 1, 24, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid hour");
        } catch (java.time.DateTimeException expected) {
        }
    }
}
