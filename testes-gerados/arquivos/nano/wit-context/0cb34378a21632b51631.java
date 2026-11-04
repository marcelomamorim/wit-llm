/*
 * @test
 * @summary Tests for LocalDate.plusMonths(long)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsOverflow();
        testPlusMonthsResolvePreviousValid();
    }

    static void testPlusMonthsZero() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 15);
        LocalDate result = date.plusMonths(0);
        if (!result.equals(date)) {
            throw new AssertionError("plusMonths(0) should return the same date");
        }
    }

    static void testPlusMonthsPositive() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 15);
        LocalDate result = date.plusMonths(3);
        LocalDate expected = LocalDate.of(2020, Month.APRIL, 15);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(3) did not produce expected date");
        }
    }

    static void testPlusMonthsNegative() {
        LocalDate date = LocalDate.of(2020, Month.MAY, 10);
        LocalDate result = date.plusMonths(-4);
        LocalDate expected = LocalDate.of(2020, Month.JANUARY, 10);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(-4) did not produce expected date");
        }
    }

    static void testPlusMonthsOverflow() {
        try {
            // Using a large value to trigger potential overflow
            LocalDate.of(999999999, Month.JANUARY, 1).plusMonths(Long.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for overflow");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }

    static void testPlusMonthsResolvePreviousValid() {
        // Testing the resolvePreviousValid method indirectly via plusMonths
        LocalDate date = LocalDate.of(2021, Month.MARCH, 31);
        LocalDate result = date.plusMonths(1); // Should handle invalid day in month
        // The expected date depends on resolvePreviousValid logic, assuming it adjusts to last valid day
        LocalDate expected = LocalDate.of(2021, Month.APRIL, 30);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(1) did not resolve to last valid day in month");
        }
    }
}
