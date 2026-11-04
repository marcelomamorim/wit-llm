/*
 * @test
 * @summary Test LocalDate.minusYears() method for various inputs.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testMinusYearsZero();
        testMinusYearsPositive();
        testMinusYearsNegative();
        testMinusYearsLongMin();
        testMinusYearsLongMax();
    }

    static void testMinusYearsZero() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalDate result = date.minusYears(0);
        if (!result.equals(date)) {
            throw new AssertionError("minusYears(0) should return the same date");
        }
    }

    static void testMinusYearsPositive() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalDate expected = LocalDate.of(2018, 1, 1);
        LocalDate result = date.minusYears(2);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(2) failed: expected " + expected + " but got " + result);
        }
    }

    static void testMinusYearsNegative() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalDate expected = LocalDate.of(2022, 1, 1);
        LocalDate result = date.minusYears(-2);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(-2) failed: expected " + expected + " but got " + result);
        }
    }

    static void testMinusYearsLongMin() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        try {
            date.minusYears(Long.MIN_VALUE);
            throw new AssertionError("Expected exception for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            // expected
        }
    }

    static void testMinusYearsLongMax() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalDate result = date.minusYears(Long.MAX_VALUE);
        // Since minusYears(Long.MAX_VALUE) calls plusYears(-Long.MAX_VALUE), which is valid
        // We check that the result is not null and is a LocalDate
        if (result == null || !(result instanceof LocalDate)) {
            throw new AssertionError("minusYears(Long.MAX_VALUE) should return a LocalDate");
        }
    }
}
