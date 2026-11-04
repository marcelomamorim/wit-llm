/*
 * @test
 * @summary Test YearMonth.isValidDay(int) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testValidDays();
        testInvalidDays();
    }

    private static void testValidDays() {
        YearMonth ym = YearMonth.of(2020, 2); // Leap year February
        // Valid days in February 2020: 1 to 29
        for (int day = 1; day <= 29; day++) {
            if (!ym.isValidDay(day)) {
                throw new AssertionError("Expected valid day: " + day);
            }
        }
        // Check lengthOfMonth() boundary
        if (!ym.isValidDay(ym.lengthOfMonth())) {
            throw new AssertionError("Expected valid day at lengthOfMonth boundary");
        }
    }

    private static void testInvalidDays() {
        YearMonth ym = YearMonth.of(2019, 4); // April has 30 days
        // day 0 is invalid
        if (ym.isValidDay(0)) {
            throw new AssertionError("Day 0 should be invalid");
        }
        // day 31 is invalid for April
        if (ym.isValidDay(31)) {
            throw new AssertionError("Day 31 should be invalid for April");
        }
        // negative day
        if (ym.isValidDay(-1)) {
            throw new AssertionError("Negative day should be invalid");
        }
        // day greater than lengthOfMonth
        if (ym.isValidDay(32)) {
            throw new AssertionError("Day 32 should be invalid");
        }
    }
}
