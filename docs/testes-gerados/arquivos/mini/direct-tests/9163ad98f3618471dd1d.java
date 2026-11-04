/*
 * @test
 * @summary Test java.time.Year.plusYears(long) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflowTooLarge();
        testPlusYearsOverflowTooSmall();
    }

    private static void testPlusYearsZero() {
        Year y = Year.of(2000);
        Year result = y.plusYears(0);
        if (result != y) {
            throw new AssertionError("plusYears(0) should return the same instance");
        }
    }

    private static void testPlusYearsPositive() {
        Year y = Year.of(2000);
        Year result = y.plusYears(20);
        if (result.getValue() != 2020) {
            throw new AssertionError("plusYears(20) from 2000 should be 2020, got " + result.getValue());
        }
    }

    private static void testPlusYearsNegative() {
        Year y = Year.of(2000);
        Year result = y.plusYears(-50);
        if (result.getValue() != 1950) {
            throw new AssertionError("plusYears(-50) from 2000 should be 1950, got " + result.getValue());
        }
    }

    private static void testPlusYearsOverflowTooLarge() {
        Year y = Year.of(Integer.MAX_VALUE);
        try {
            y.plusYears(1);
            throw new AssertionError("Expected DateTimeException for overflow beyond Integer.MAX_VALUE");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testPlusYearsOverflowTooSmall() {
        Year y = Year.of(Integer.MIN_VALUE);
        try {
            y.plusYears(-1);
            throw new AssertionError("Expected DateTimeException for overflow below Integer.MIN_VALUE");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
