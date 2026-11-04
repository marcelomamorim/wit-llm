/*
 * @test
 * @summary Test LocalDate.plusMonths method for various inputs
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testPlusMonthsZero();
        testPlusMonthsPositive();
        testPlusMonthsNegative();
        testPlusMonthsLarge();
        testPlusMonthsOverflow();
    }

    static void testPlusMonthsZero() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        LocalDate result = date.plusMonths(0);
        if (!result.equals(date)) {
            throw new AssertionError("plusMonths(0) should return the same date");
        }
    }

    static void testPlusMonthsPositive() {
        LocalDate date = LocalDate.of(2021, 3, 10);
        LocalDate result = date.plusMonths(5);
        LocalDate expected = LocalDate.of(2021, 8, 10);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(5) failed: expected " + expected + " but got " + result);
        }
    }

    static void testPlusMonthsNegative() {
        LocalDate date = LocalDate.of(2021, 3, 10);
        LocalDate result = date.plusMonths(-4);
        LocalDate expected = LocalDate.of(2021, -1, 10);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(-4) failed: expected " + expected + " but got " + result);
        }
    }

    static void testPlusMonthsLarge() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalDate result = date.plusMonths(12000); // 1000 years
        LocalDate expected = LocalDate.of(2100, 1, 1);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMonths(12000) failed: expected " + expected + " but got " + result);
        }
    }

    static void testPlusMonthsOverflow() {
        LocalDate date = LocalDate.of(9999, 12, 31);
        try {
            LocalDate result = date.plusMonths(Long.MAX_VALUE);
            // No exception expected, just check that result is not null
            if (result == null) {
                throw new AssertionError("plusMonths(Long.MAX_VALUE) returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("plusMonths(Long.MAX_VALUE) threw an exception: " + e);
        }
    }
}
