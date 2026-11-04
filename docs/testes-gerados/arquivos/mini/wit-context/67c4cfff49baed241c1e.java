/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int, int, int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidDateTime();
        testNullMonth();
        testInvalidDayOfMonth();
        testInvalidYear();
    }

    private static void testValidDateTime() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59, 59, 999999999);
        if (dt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (dt.getMonth() != Month.DECEMBER) throw new AssertionError("Month mismatch");
        if (dt.getDayOfMonth() != 31) throw new AssertionError("Day mismatch");
        if (dt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (dt.getMinute() != 59) throw new AssertionError("Minute mismatch");
        if (dt.getSecond() != 59) throw new AssertionError("Second mismatch");
        if (dt.getNano() != 999999999) throw new AssertionError("Nano mismatch");
    }

    private static void testNullMonth() {
        try {
            LocalDateTime.of(2018, null, 15, 12, 0, 0, 0);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException expected) {
        }
    }

    private static void testInvalidDayOfMonth() {
        // Day 32 is invalid for any month
        try {
            LocalDateTime.of(2018, Month.JANUARY, 32, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth");
        } catch (DateTimeException expected) {
        }
        // Day 0 is invalid
        try {
            LocalDateTime.of(2018, Month.JANUARY, 0, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for dayOfMonth zero");
        } catch (DateTimeException expected) {
        }
    }

    private static void testInvalidYear() {
        // Year too large (greater than LocalDate.MAX year)
        int tooLargeYear = LocalDate.MAX.getYear() + 1;
        try {
            LocalDateTime.of(tooLargeYear, Month.JANUARY, 1, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for year too large");
        } catch (DateTimeException expected) {
        }
        // Year too small (less than LocalDate.MIN year)
        int tooSmallYear = LocalDate.MIN.getYear() - 1;
        try {
            LocalDateTime.of(tooSmallYear, Month.JANUARY, 1, 0, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for year too small");
        } catch (DateTimeException expected) {
        }
    }
}
