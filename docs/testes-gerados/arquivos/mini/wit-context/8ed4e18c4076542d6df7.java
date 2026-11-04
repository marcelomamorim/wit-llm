/*
 * @test
 * @summary Test java.time.YearMonth.range(TemporalField) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testRangeWithYearOfEraFieldPositiveYear();
        testRangeWithYearOfEraFieldNonPositiveYear();
        testRangeWithOtherField();
    }

    private static void testRangeWithYearOfEraFieldPositiveYear() {
        YearMonth ym = YearMonth.of(2000, Month.JANUARY);
        TemporalField field = ChronoField.YEAR_OF_ERA;
        ValueRange range = ym.range(field);
        // For positive year, range should be 1 to Year.MAX_VALUE (999999999)
        if (range.getMinimum() != 1) {
            throw new AssertionError("Expected minimum 1 for YEAR_OF_ERA field");
        }
        if (range.getMaximum() != 999999999) {
            throw new AssertionError("Expected maximum 999999999 for YEAR_OF_ERA field with positive year");
        }
    }

    private static void testRangeWithYearOfEraFieldNonPositiveYear() {
        YearMonth ym = YearMonth.of(-1, Month.DECEMBER); // year <= 0
        TemporalField field = ChronoField.YEAR_OF_ERA;
        ValueRange range = ym.range(field);
        // For year <= 0, range should be 1 to Year.MAX_VALUE + 1 (1000000000)
        if (range.getMinimum() != 1) {
            throw new AssertionError("Expected minimum 1 for YEAR_OF_ERA field");
        }
        if (range.getMaximum() != 1000000000) {
            throw new AssertionError("Expected maximum 1000000000 for YEAR_OF_ERA field with non-positive year");
        }
    }

    private static void testRangeWithOtherField() {
        YearMonth ym = YearMonth.of(2020, Month.MARCH);
        TemporalField field = ChronoField.MONTH_OF_YEAR;
        ValueRange range = ym.range(field);
        // The range for MONTH_OF_YEAR is 1 to 12
        if (range.getMinimum() != 1) {
            throw new AssertionError("Expected minimum 1 for MONTH_OF_YEAR field");
        }
        if (range.getMaximum() != 12) {
            throw new AssertionError("Expected maximum 12 for MONTH_OF_YEAR field");
        }
    }
}
