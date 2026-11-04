/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int, int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testNullMonthThrowsNPE();
        testInvalidYearThrowsDateTimeException();
        testInvalidDayOfMonthThrowsDateTimeException();
    }

    private static void testValidDateTime() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59, 59);
        if (dt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (dt.getMonth() != Month.DECEMBER) throw new AssertionError("Month mismatch");
        if (dt.getDayOfMonth() != 31) throw new AssertionError("Day mismatch");
        if (dt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (dt.getMinute() != 59) throw new AssertionError("Minute mismatch");
        if (dt.getSecond() != 59) throw new AssertionError("Second mismatch");
    }

    private static void testNullMonthThrowsNPE() {
        try {
            LocalDateTime.of(2018, null, 1, 0, 0, 0);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException expected) {
        }
    }

    private static void testInvalidYearThrowsDateTimeException() {
        // Year too low
        try {
            LocalDateTime.of(-1000000, Month.JANUARY, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid year (too low)");
        } catch (DateTimeException expected) {
        }
        // Year too high
        try {
            LocalDateTime.of(1000000, Month.JANUARY, 1, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid year (too high)");
        } catch (DateTimeException expected) {
        }
    }

    private static void testInvalidDayOfMonthThrowsDateTimeException() {
        // Day of month too low
        try {
            LocalDateTime.of(2018, Month.JANUARY, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth (too low)");
        } catch (DateTimeException expected) {
        }
        // Day of month too high
        try {
            LocalDateTime.of(2018, Month.JANUARY, 32, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth (too high)");
        } catch (DateTimeException expected) {
        }
    }
}
