/*
 * @test
 * @summary Test lengthOfMonth method of YearMonth
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testLengthOfMonthNonLeapYear();
        testLengthOfMonthLeapYear();
    }

    private static void testLengthOfMonthNonLeapYear() {
        YearMonth ym = YearMonth.of(2019, Month.FEBRUARY);
        int length = ym.lengthOfMonth();
        if (length != 28) {
            throw new AssertionError("Expected 28 days in February 2019, got " + length);
        }
    }

    private static void testLengthOfMonthLeapYear() {
        YearMonth ym = YearMonth.of(2020, Month.FEBRUARY);
        int length = ym.lengthOfMonth();
        if (length != 29) {
            throw new AssertionError("Expected 29 days in February 2020 (leap year), got " + length);
        }
    }
}
