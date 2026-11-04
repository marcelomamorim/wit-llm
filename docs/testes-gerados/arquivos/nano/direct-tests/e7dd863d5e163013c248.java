/*
 * @test
 * @summary Tests for YearMonth.isValidDay(int)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidDay();
        testInvalidDayTooLow();
        testInvalidDayTooHigh();
    }

    public static void testValidDay() {
        YearMonth ym = YearMonth.of(2023, 2); // February 2023, non-leap year
        boolean result = ym.isValidDay(28);
        if (!result) {
            throw new AssertionError("Expected day 28 to be valid for February 2023");
        }
    }

    public static void testInvalidDayTooLow() {
        YearMonth ym = YearMonth.of(2023, 1); // January
        boolean result = ym.isValidDay(0);
        if (result) {
            throw new AssertionError("Expected day 0 to be invalid");
        }
    }

    public static void testInvalidDayTooHigh() {
        YearMonth ym = YearMonth.of(2023, 4); // April
        boolean result = ym.isValidDay(31); // April has 30 days
        if (result) {
            throw new AssertionError("Expected day 31 to be invalid for April");
        }
    }
}
