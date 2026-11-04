/*
 * @test
 * @summary Test java.time.LocalDate.plusMonths(long) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsYearOverflow();
        testPlusMonthsMonthBoundary();
    }

    private static void testPlusMonthsZero() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 15);
        LocalDate result = date.plusMonths(0);
        if (result != date) {
            throw new AssertionError("plusMonths(0) should return same instance");
        }
    }

    private static void testPlusMonthsPositive() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 31);
        LocalDate result = date.plusMonths(1);
        // January 31 + 1 month -> February 28 (or 29 if leap year), here 2018 is not leap
        if (!(result.getYear() == 2018 && result.getMonthValue() == 2 && result.getDayOfMonth() == 28)) {
            throw new AssertionError("plusMonths(1) from 2018-01-31 should be 2018-02-28 but was " + result);
        }
    }

    private static void testPlusMonthsNegative() {
        LocalDate date = LocalDate.of(2018, Month.MARCH, 31);
        LocalDate result = date.plusMonths(-1);
        // March 31 - 1 month -> February 28 (2018 not leap)
        if (!(result.getYear() == 2018 && result.getMonthValue() == 2 && result.getDayOfMonth() == 28)) {
            throw new AssertionError("plusMonths(-1) from 2018-03-31 should be 2018-02-28 but was " + result);
        }
    }

    private static void testPlusMonthsYearOverflow() {
        LocalDate date = LocalDate.of(YearMax, Month.DECEMBER, 31);
        try {
            date.plusMonths(1);
            throw new AssertionError("Expected exception for year overflow not thrown");
        } catch (ArithmeticException | DateTimeException e) {
            // Expected: overflow or invalid year
        } catch (RuntimeException e) {
            // Accept any runtime exception as overflow indication
        }
    }

    private static void testPlusMonthsMonthBoundary() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 30);
        LocalDate result = date.plusMonths(1);
        // January 30 + 1 month -> February 28 (2018 not leap)
        if (!(result.getYear() == 2018 && result.getMonthValue() == 2 && result.getDayOfMonth() == 28)) {
            throw new AssertionError("plusMonths(1) from 2018-01-30 should be 2018-02-28 but was " + result);
        }
    }

    // Constants for test
    private static final int YearMax = 999999999;
}