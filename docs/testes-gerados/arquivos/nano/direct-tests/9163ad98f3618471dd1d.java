/*
 * @test
 * @summary Test the plusYears method of java.time.Year for various inputs.
 * @run main YearWitupTest
 */

import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
    }

    static void testPlusYearsZero() {
        Year currentYear = Year.of(2023);
        Year result = currentYear.plusYears(0);
        if (result != currentYear) {
            throw new AssertionError("plusYears(0) should return the same instance");
        }
    }

    static void testPlusYearsPositive() {
        Year currentYear = Year.of(2020);
        Year result = currentYear.plusYears(5);
        Year expected = Year.of(2025);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPlusYearsNegative() {
        Year currentYear = Year.of(2025);
        Year result = currentYear.plusYears(-3);
        Year expected = Year.of(2022);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testPlusYearsOverflow() {
        Year currentYear = Year.of(Integer.MAX_VALUE - 1);
        Year result = currentYear.plusYears(1);
        Year expected = Year.of(Integer.MAX_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
        // Test overflow beyond Integer.MAX_VALUE
        try {
            currentYear.plusYears(2);
            throw new AssertionError("Expected overflow to throw an exception or handle overflow");
        } catch (Exception e) {
            // Expected behavior: overflow handling
        }
    }
}
