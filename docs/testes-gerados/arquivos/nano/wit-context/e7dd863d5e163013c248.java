/*
 * @test
 * @summary Test for YearMonth.isValidDay(int)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidDay();
        testInvalidDayTooLow();
        testInvalidDayTooHigh();
        testInvalidMonthBelowRange();
        testInvalidMonthAboveRange();
    }

    static void testValidDay() {
        YearMonth ym = YearMonth.of(2023, Month.MAY);
        int validDay = 15;
        boolean result = ym.isValidDay(validDay);
        if (!result) {
            throw new AssertionError("Expected true for valid day: " + validDay);
        }
    }

    static void testInvalidDayTooLow() {
        YearMonth ym = YearMonth.of(2023, Month.MAY);
        int invalidDay = 0;
        boolean result = ym.isValidDay(invalidDay);
        if (result) {
            throw new AssertionError("Expected false for day below 1: " + invalidDay);
        }
    }

    static void testInvalidDayTooHigh() {
        YearMonth ym = YearMonth.of(2023, Month.MAY);
        int invalidDay = ym.lengthOfMonth() + 1;
        boolean result = ym.isValidDay(invalidDay);
        if (result) {
            throw new AssertionError("Expected false for day above length of month: " + invalidDay);
        }
    }

    static void testInvalidMonthBelowRange() {
        try {
            YearMonth.of(2023, 0);
            throw new AssertionError("Expected exception for month below 1");
        } catch (Exception e) {
            // Expected exception
        }
    }

    static void testInvalidMonthAboveRange() {
        try {
            YearMonth.of(2023, 13);
            throw new AssertionError("Expected exception for month above 12");
        } catch (Exception e) {
            // Expected exception
        }
    }
}
