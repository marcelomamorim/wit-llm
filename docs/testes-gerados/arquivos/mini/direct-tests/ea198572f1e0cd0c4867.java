/*
 * @test
 * @summary Test YearMonth.withMonth(int) method for valid and invalid inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testWithMonthValid();
        testWithMonthInvalid();
    }

    private static void testWithMonthValid() {
        YearMonth ym = YearMonth.of(2020, 1);
        YearMonth result = ym.withMonth(12);
        if (result.getYear() != 2020 || result.getMonthValue() != 12) {
            throw new AssertionError("Expected year 2020 and month 12, got " + result);
        }
        // Test with minimum valid month
        result = ym.withMonth(1);
        if (result.getMonthValue() != 1) {
            throw new AssertionError("Expected month 1, got " + result.getMonthValue());
        }
    }

    private static void testWithMonthInvalid() {
        YearMonth ym = YearMonth.of(2020, 6);
        try {
            ym.withMonth(0);
            throw new AssertionError("Expected DateTimeException for month 0");
        } catch (DateTimeException e) {
            // Expected
        }
        try {
            ym.withMonth(13);
            throw new AssertionError("Expected DateTimeException for month 13");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
