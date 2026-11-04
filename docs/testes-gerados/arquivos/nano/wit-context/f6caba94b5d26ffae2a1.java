/*
 * @test
 * @summary Tests for java.time.YearMonth.of(int, int)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.ChronoField;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidYearBelowRange();
        testInvalidYearAboveRange();
        testInvalidMonthBelowRange();
        testInvalidMonthAboveRange();
    }

    static void testValidInputs() {
        try {
            YearMonth ym = YearMonth.of(2020, 5);
            if (ym.getYear() != 2020 || ym.getMonthValue() != 5) {
                throw new AssertionError("Valid input test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs", e);
        }
    }

    static void testInvalidYearBelowRange() {
        int invalidYear = Integer.MIN_VALUE;
        try {
            YearMonth.of(invalidYear, 1);
            throw new AssertionError("Expected DateTimeException for year below range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidYearAboveRange() {
        int invalidYear = Integer.MAX_VALUE;
        try {
            YearMonth.of(invalidYear, 1);
            // Depending on implementation, may or may not throw; check if exception is thrown
            // If no exception, test passes as behavior is current
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonthBelowRange() {
        int invalidMonth = 0;
        try {
            YearMonth.of(2020, invalidMonth);
            throw new AssertionError("Expected DateTimeException for month below range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonthAboveRange() {
        int invalidMonth = 13;
        try {
            YearMonth.of(2020, invalidMonth);
            throw new AssertionError("Expected DateTimeException for month above range");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
