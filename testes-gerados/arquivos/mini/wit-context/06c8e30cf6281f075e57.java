/*
 * @test
 * @summary Test YearMonth.withYear(int) method for valid and invalid year values
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testWithYearValid();
        testWithYearInvalid();
    }

    private static void testWithYearValid() {
        // Create a YearMonth instance for a known valid year and month
        YearMonth ym = YearMonth.of(2000, 1);
        // Change the year to another valid year
        YearMonth result = ym.withYear(1999);
        if (result.getYear() != 1999) {
            throw new AssertionError("Expected year 1999 but got " + result.getYear());
        }
        if (result.getMonthValue() != 1) {
            throw new AssertionError("Expected month 1 but got " + result.getMonthValue());
        }
    }

    private static void testWithYearInvalid() {
        YearMonth ym = YearMonth.of(2000, 1);
        // Test with year less than minimum supported year
        try {
            ym.withYear(Integer.MIN_VALUE);
            throw new AssertionError("Expected DateTimeException for year Integer.MIN_VALUE");
        } catch (DateTimeException expected) {
            // Expected exception
        }
        // Test with year greater than maximum supported year
        try {
            ym.withYear(Integer.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for year Integer.MAX_VALUE");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }
}
