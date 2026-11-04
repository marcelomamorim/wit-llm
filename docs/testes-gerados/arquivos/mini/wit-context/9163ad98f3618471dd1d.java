/*
 * @test
 * @summary Deterministic tests for java.time.Year.plusYears(long) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsNormal();
        testPlusYearsOverflowLow();
        testPlusYearsOverflowHigh();
    }

    private static void testPlusYearsZero() {
        Year y = Year.of(2000);
        Year result = y.plusYears(0L);
        if (result != y) {
            throw new AssertionError("plusYears(0) should return same instance");
        }
    }

    private static void testPlusYearsNormal() {
        Year y = Year.of(2000);
        Year result = y.plusYears(20L);
        if (result.getValue() != 2020) {
            throw new AssertionError("plusYears(20) should add 20 years");
        }
        result = y.plusYears(-10L);
        if (result.getValue() != 1990) {
            throw new AssertionError("plusYears(-10) should subtract 10 years");
        }
    }

    private static void testPlusYearsOverflowLow() {
        Year y = Year.of(Year.MIN_VALUE);
        long yearsToAdd = -1L;
        try {
            y.plusYears(yearsToAdd);
            throw new AssertionError("Expected DateTimeException for underflow not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testPlusYearsOverflowHigh() {
        Year y = Year.of(Year.MAX_VALUE);
        long yearsToAdd = 1L;
        try {
            y.plusYears(yearsToAdd);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
