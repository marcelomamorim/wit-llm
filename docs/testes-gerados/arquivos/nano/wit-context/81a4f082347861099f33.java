/*
 * @test
 * @summary Tests for java.time.Year.minusYears(long)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;

public class YearWitupTest {

    public static void main(String[] args) {
        testMinusYearsNormal();
        testMinusYearsLongMinValue();
        testMinusYearsZero();
        testMinusYearsLargePositive();
        testMinusYearsLargeNegative();
    }

    static void testMinusYearsNormal() {
        Year year = Year.of(2020);
        Year result = year.minusYears(5);
        if (result.getValue() != 2015) {
            throw new AssertionError("Expected 2015, got " + result.getValue());
        }
    }

    static void testMinusYearsLongMinValue() {
        Year year = Year.of(2020);
        Year result = year.minusYears(Long.MIN_VALUE);
        // Expecting plusYears(Long.MAX_VALUE).plusYears(1)
        Year expected = year.plusYears(Long.MAX_VALUE).plusYears(1);
        if (result.getValue() != expected.getValue()) {
            throw new AssertionError("Expected " + expected.getValue() + ", got " + result.getValue());
        }
    }

    static void testMinusYearsZero() {
        Year year = Year.of(2020);
        Year result = year.minusYears(0);
        if (result.getValue() != 2020) {
            throw new AssertionError("Expected 2020, got " + result.getValue());
        }
    }

    static void testMinusYearsLargePositive() {
        Year year = Year.of(2020);
        long largeYears = 1000000L;
        Year result = year.minusYears(largeYears);
        if (result.getValue() != 2020 - largeYears) {
            throw new AssertionError("Expected " + (2020 - largeYears) + ", got " + result.getValue());
        }
    }

    static void testMinusYearsLargeNegative() {
        Year year = Year.of(2020);
        long largeYears = -1000000L;
        Year result = year.minusYears(largeYears);
        if (result.getValue() != 2020 - largeYears) {
            throw new AssertionError("Expected " + (2020 - largeYears) + ", got " + result.getValue());
        }
    }
}
