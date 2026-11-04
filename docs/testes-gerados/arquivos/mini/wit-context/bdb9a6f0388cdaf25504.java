/*
 * @test
 * @summary Test LocalDateTime.of(int, Month, int, int, int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullMonthThrowsNPE();
        testYearOutOfRangeThrowsDateTimeException();
        testDayOfMonthOutOfRangeThrowsDateTimeException();
    }

    private static void testValidConstruction() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59);
        if (ldt.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (ldt.getMonth() != Month.DECEMBER) throw new AssertionError("Month mismatch");
        if (ldt.getDayOfMonth() != 31) throw new AssertionError("DayOfMonth mismatch");
        if (ldt.getHour() != 23) throw new AssertionError("Hour mismatch");
        if (ldt.getMinute() != 59) throw new AssertionError("Minute mismatch");
    }

    private static void testNullMonthThrowsNPE() {
        try {
            LocalDateTime.of(2018, null, 15, 10, 30);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testYearOutOfRangeThrowsDateTimeException() {
        // Year too low
        try {
            LocalDateTime.of(-1000000, Month.JANUARY, 1, 0, 0);
            throw new AssertionError("Expected DateTimeException for year too low");
        } catch (DateTimeException expected) {
            // expected
        }
        // Year too high
        try {
            LocalDateTime.of(1000000, Month.JANUARY, 1, 0, 0);
            throw new AssertionError("Expected DateTimeException for year too high");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testDayOfMonthOutOfRangeThrowsDateTimeException() {
        // Day too low
        try {
            LocalDateTime.of(2018, Month.JANUARY, 0, 0, 0);
            throw new AssertionError("Expected DateTimeException for dayOfMonth too low");
        } catch (DateTimeException expected) {
            // expected
        }
        // Day too high
        try {
            LocalDateTime.of(2018, Month.JANUARY, 32, 0, 0);
            throw new AssertionError("Expected DateTimeException for dayOfMonth too high");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
