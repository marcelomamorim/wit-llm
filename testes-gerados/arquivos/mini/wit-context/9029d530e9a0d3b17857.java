/*
 * @test
 * @summary Deterministic tests for java.time.LocalDateTime.plusMonths(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusMonthsNormal();
        testPlusMonthsZero();
        testPlusMonthsOverflow();
    }

    private static void testPlusMonthsNormal() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 31, 10, 30);
        LocalDateTime result = ldt.plusMonths(1);
        // January 31 + 1 month -> February 28 (or 29 if leap year), time unchanged
        // 2018 is not leap year, so expect Feb 28
        if (result.getYear() != 2018 || result.getMonth() != Month.FEBRUARY || result.getDayOfMonth() != 28) {
            throw new AssertionError("Failed normal plusMonths: " + result);
        }
        if (result.getHour() != 10 || result.getMinute() != 30) {
            throw new AssertionError("Time changed unexpectedly: " + result);
        }
    }

    private static void testPlusMonthsZero() {
        LocalDateTime ldt = LocalDateTime.of(2020, Month.MARCH, 15, 5, 45);
        LocalDateTime result = ldt.plusMonths(0);
        // plus 0 months should return equal LocalDateTime
        if (!result.equals(ldt)) {
            throw new AssertionError("plusMonths(0) should return same date-time: " + result);
        }
    }

    private static void testPlusMonthsOverflow() {
        // Test that adding months beyond supported range throws DateTimeException
        LocalDateTime ldt = LocalDateTime.of(999999999, Month.DECEMBER, 31, 23, 59);
        try {
            // Adding 1 month to max year should overflow
            ldt.plusMonths(1);
            throw new AssertionError("Expected DateTimeException not thrown for overflow");
        } catch (DateTimeException e) {
            // Expected
        }

        ldt = LocalDateTime.of(-999999999, Month.JANUARY, 1, 0, 0);
        try {
            // Subtracting 1 month beyond min year should overflow
            ldt.plusMonths(-1);
            throw new AssertionError("Expected DateTimeException not thrown for underflow");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
