/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.plusMonths(long)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsNormal();
        testPlusMonthsOverflowException();
    }

    private static void testPlusMonthsZero() {
        LocalDate date = LocalDate.of(2018, 5, 15);
        LocalDate result = date.plusMonths(0L);
        if (result != date) {
            throw new AssertionError("plusMonths(0) should return same instance");
        }
    }

    private static void testPlusMonthsNormal() {
        LocalDate date = LocalDate.of(2018, 1, 31);
        // Adding 1 month to Jan 31 should resolve to Feb 28 (non-leap year)
        LocalDate result = date.plusMonths(1L);
        if (!(result.getYear() == 2018 && result.getMonthValue() == 2 && result.getDayOfMonth() == 28)) {
            throw new AssertionError("plusMonths(1) from 2018-01-31 should be 2018-02-28 but was " + result);
        }
        // Adding -1 month to March 31 should resolve to Feb 28
        LocalDate date2 = LocalDate.of(2018, 3, 31);
        LocalDate result2 = date2.plusMonths(-1L);
        if (!(result2.getYear() == 2018 && result2.getMonthValue() == 2 && result2.getDayOfMonth() == 28)) {
            throw new AssertionError("plusMonths(-1) from 2018-03-31 should be 2018-02-28 but was " + result2);
        }
    }

    private static void testPlusMonthsOverflowException() {
        LocalDate date = LocalDate.of(1970, 1, 1);
        // The test tries to add a large number of months to cause DateTimeException due to year overflow
        // According to source, exception occurs if newYear is out of int range
        // We pick monthsToAdd to cause newYear > Integer.MAX_VALUE or < Integer.MIN_VALUE
        // Use a large positive value to trigger overflow
        long largeMonths = (long) Integer.MAX_VALUE * 12 + 12L; // definitely over max year
        try {
            date.plusMonths(largeMonths);
            throw new AssertionError("Expected DateTimeException for large positive monthsToAdd");
        } catch (DateTimeException expected) {
            // expected
        }
        // Use a large negative value to trigger underflow
        long largeNegativeMonths = (long) Integer.MIN_VALUE * 12 - 12L; // definitely below min year
        try {
            date.plusMonths(largeNegativeMonths);
            throw new AssertionError("Expected DateTimeException for large negative monthsToAdd");
        } catch (DateTimeException expected) {
            // expected
        }
        // Also test that adding 0 does not throw
        try {
            LocalDate result = date.plusMonths(0L);
            if (result != date) {
                throw new AssertionError("plusMonths(0) should return same instance");
            }
        } catch (Exception ex) {
            throw new AssertionError("plusMonths(0) should not throw, but threw " + ex);
        }
    }
}
