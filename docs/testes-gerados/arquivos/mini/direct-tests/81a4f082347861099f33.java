/*
 * @test
 * @summary Test java.time.Year.minusYears(long) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;

public class YearWitupTest {
    public static void main(String[] args) {
        testMinusYearsZero();
        testMinusYearsPositive();
        testMinusYearsNegative();
        testMinusYearsLongMinValue();
    }

    private static void testMinusYearsZero() {
        Year y = Year.of(2000);
        Year result = y.minusYears(0L);
        if (!result.equals(y)) {
            throw new AssertionError("minusYears(0) should return the same year");
        }
    }

    private static void testMinusYearsPositive() {
        Year y = Year.of(2000);
        Year result = y.minusYears(10L);
        Year expected = y.plusYears(-10L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(10) should be equal to plusYears(-10)");
        }
    }

    private static void testMinusYearsNegative() {
        Year y = Year.of(2000);
        Year result = y.minusYears(-5L);
        Year expected = y.plusYears(5L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(-5) should be equal to plusYears(5)");
        }
    }

    private static void testMinusYearsLongMinValue() {
        Year y = Year.of(2000);
        // According to the method, minusYears(Long.MIN_VALUE) == plusYears(Long.MAX_VALUE).plusYears(1)
        Year result = y.minusYears(Long.MIN_VALUE);
        Year expected = y.plusYears(Long.MAX_VALUE).plusYears(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusYears(Long.MIN_VALUE) should equal plusYears(Long.MAX_VALUE).plusYears(1)");
        }
    }
}
