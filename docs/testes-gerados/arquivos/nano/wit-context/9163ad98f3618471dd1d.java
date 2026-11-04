/*
 * @test
 * @summary Tests for java.time.Year.plusYears(long)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;

public class YearWitupTest {

    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsWithinRange();
        testPlusYearsExceedingMax();
        testPlusYearsBelowMin();
    }

    static void testPlusYearsZero() {
        Year y = Year.of(2020);
        Year result = y.plusYears(0);
        if (result != y) {
            throw new AssertionError("plusYears(0) should return the same instance");
        }
    }

    static void testPlusYearsWithinRange() {
        Year y = Year.of(2020);
        Year result = y.plusYears(5);
        if (result.getValue() != 2025) {
            throw new AssertionError("Expected year 2025, got " + result.getValue());
        }
    }

    static void testPlusYearsExceedingMax() {
        Year y = Year.of(Year.MAX_VALUE - 1);
        try {
            y.plusYears(2);
            throw new AssertionError("Expected DateTimeException for exceeding max year");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testPlusYearsBelowMin() {
        Year y = Year.of(Year.MIN_VALUE + 1);
        try {
            y.plusYears(-2);
            throw new AssertionError("Expected DateTimeException for below min year");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
