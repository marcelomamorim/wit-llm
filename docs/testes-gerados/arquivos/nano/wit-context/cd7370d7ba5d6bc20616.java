/*
 * @test
 * @summary Test for YearMonth.lengthOfMonth() method, covering normal and exceptional cases.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testLengthOfMonthValid();
        testLengthOfMonthInvalidMonth();
    }

    static void testLengthOfMonthValid() {
        YearMonth ym = YearMonth.of(2023, 2); // February 2023
        int length = ym.lengthOfMonth();
        if (length != 28 && length != 29) {
            throw new AssertionError("Unexpected length of month: " + length);
        }
    }

    static void testLengthOfMonthInvalidMonth() {
        try {
            YearMonth ym = YearMonth.of(2023, 13); // Invalid month
            ym.lengthOfMonth();
            throw new AssertionError("Expected exception not thrown for invalid month");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
