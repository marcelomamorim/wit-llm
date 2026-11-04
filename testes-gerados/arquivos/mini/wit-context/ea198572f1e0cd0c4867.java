/*
 * @test
 * @summary Test YearMonth.withMonth(int) method for valid and invalid month values
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testWithMonthValid();
        testWithMonthInvalidLow();
        testWithMonthInvalidHigh();
    }

    private static void testWithMonthValid() {
        YearMonth ym = YearMonth.of(2020, 1);
        YearMonth result = ym.withMonth(12);
        if (result.getMonthValue() != 12) {
            throw new AssertionError("Expected month 12 but got " + result.getMonthValue());
        }
        if (result.getYear() != 2020) {
            throw new AssertionError("Year should remain unchanged");
        }
    }

    private static void testWithMonthInvalidLow() {
        YearMonth ym = YearMonth.of(2020, 6);
        try {
            ym.withMonth(0);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    private static void testWithMonthInvalidHigh() {
        YearMonth ym = YearMonth.of(2020, 6);
        try {
            ym.withMonth(13);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
