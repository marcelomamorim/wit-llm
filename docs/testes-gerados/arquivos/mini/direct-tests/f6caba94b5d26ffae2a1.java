/*
 * @test
 * @summary Test YearMonth.of(int year, int month) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.ValueRange;
import java.time.temporal.ChronoField;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testValidYearMonth();
        testYearTooLow();
        testYearTooHigh();
        testMonthTooLow();
        testMonthTooHigh();
    }

    private static void testValidYearMonth() {
        YearMonth ym = YearMonth.of(2018, 12);
        if (ym.getYear() != 2018) {
            throw new AssertionError("Year should be 2018 but was " + ym.getYear());
        }
        if (ym.getMonthValue() != 12) {
            throw new AssertionError("Month should be 12 but was " + ym.getMonthValue());
        }
    }

    private static void testYearTooLow() {
        try {
            YearMonth.of((int)ChronoField.YEAR.range().getMinimum() - 1, 1);
            throw new AssertionError("Expected exception for year too low");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testYearTooHigh() {
        try {
            YearMonth.of((int)ChronoField.YEAR.range().getMaximum() + 1, 1);
            throw new AssertionError("Expected exception for year too high");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testMonthTooLow() {
        try {
            YearMonth.of(2000, (int)ChronoField.MONTH_OF_YEAR.range().getMinimum() - 1);
            throw new AssertionError("Expected exception for month too low");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testMonthTooHigh() {
        try {
            YearMonth.of(2000, (int)ChronoField.MONTH_OF_YEAR.range().getMaximum() + 1);
            throw new AssertionError("Expected exception for month too high");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
