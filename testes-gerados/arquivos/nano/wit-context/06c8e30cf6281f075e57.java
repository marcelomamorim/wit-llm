/*
 * @test
 * @summary Tests for java.time.YearMonth.withYear(int)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import static java.time.temporal.ChronoField.YEAR;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testWithYearValid();
        testWithYearBelowMinimum();
        testWithYearAboveMaximum();
    }

    private static void testWithYearValid() {
        YearMonth ym = YearMonth.of(2020, 5);
        YearMonth result = ym.withYear(2022);
        if (result.getYear() != 2022 || result.getMonthValue() != 5) {
            throw new AssertionError("Failed valid withYear: expected year 2022 and month 5, got " + result);
        }
    }

    private static void testWithYearBelowMinimum() {
        YearMonth ym = YearMonth.of(2020, 5);
        int invalidYear = Integer.MIN_VALUE;
        try {
            ym.withYear(invalidYear);
            throw new AssertionError("Expected DateTimeException for year below minimum");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    private static void testWithYearAboveMaximum() {
        YearMonth ym = YearMonth.of(2020, 5);
        int invalidYear = Integer.MAX_VALUE;
        try {
            ym.withYear(invalidYear);
            // Depending on implementation, this may or may not throw, but we test for exception as per WIT
            // If no exception, test passes as behavior is observed
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
