/*
 * @test
 * @summary Test java.time.LocalDate.minusYears(long) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testMinusYearsZero();
        testMinusYearsPositive();
        testMinusYearsNegative();
        testMinusYearsLongMinValue();
    }

    private static void testMinusYearsZero() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate result = date.minusYears(0L);
        if (!result.equals(date)) {
            throw new AssertionError("minusYears(0) should return the same date");
        }
    }

    private static void testMinusYearsPositive() {
        LocalDate date = LocalDate.of(2000, Month.FEBRUARY, 29);
        LocalDate expected = LocalDate.of(1999, Month.FEBRUARY, 28); // 1999 not leap year
        LocalDate result = date.minusYears(1L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(1) failed: expected " + expected + " but got " + result);
        }
    }

    private static void testMinusYearsNegative() {
        LocalDate date = LocalDate.of(2000, Month.MARCH, 1);
        LocalDate expected = LocalDate.of(2001, Month.MARCH, 1);
        LocalDate result = date.minusYears(-1L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(-1) failed: expected " + expected + " but got " + result);
        }
    }

    private static void testMinusYearsLongMinValue() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        // minusYears(Long.MIN_VALUE) internally calls plusYears(Long.MAX_VALUE).plusYears(1)
        LocalDate result = date.minusYears(Long.MIN_VALUE);
        LocalDate expected = date.plusYears(Long.MAX_VALUE).plusYears(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(Long.MIN_VALUE) failed: expected " + expected + " but got " + result);
        }
    }
}
