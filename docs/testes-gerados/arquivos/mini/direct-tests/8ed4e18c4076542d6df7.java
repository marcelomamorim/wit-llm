/*
 * @test
 * @summary Test java.time.YearMonth range(TemporalField) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testRangeYearOfEraPositiveYear();
        testRangeYearOfEraZeroYear();
        testRangeYearOfEraNegativeYear();
        testRangeOtherField();
    }

    private static void testRangeYearOfEraPositiveYear() {
        YearMonth ym = YearMonth.of(1, 1); // positive year
        ValueRange range = ym.range(ChronoField.YEAR_OF_ERA);
        // For positive year, range should be 1 to Year.MAX_VALUE (999999999)
        if (range.getMinimum() != 1 || range.getMaximum() != 999999999) {
            throw new AssertionError("range YEAR_OF_ERA for positive year failed: " + range);
        }
    }

    private static void testRangeYearOfEraZeroYear() {
        YearMonth ym = YearMonth.of(0, 1); // year zero
        ValueRange range = ym.range(ChronoField.YEAR_OF_ERA);
        // For year <= 0, range should be 1 to Year.MAX_VALUE + 1 (1000000000)
        if (range.getMinimum() != 1 || range.getMaximum() != 1000000000) {
            throw new AssertionError("range YEAR_OF_ERA for year zero failed: " + range);
        }
    }

    private static void testRangeYearOfEraNegativeYear() {
        YearMonth ym = YearMonth.of(-1, 1); // negative year
        ValueRange range = ym.range(ChronoField.YEAR_OF_ERA);
        // For year <= 0, range should be 1 to Year.MAX_VALUE + 1 (1000000000)
        if (range.getMinimum() != 1 || range.getMaximum() != 1000000000) {
            throw new AssertionError("range YEAR_OF_ERA for negative year failed: " + range);
        }
    }

    private static void testRangeOtherField() {
        YearMonth ym = YearMonth.of(2000, 2);
        // For a field other than YEAR_OF_ERA, range should delegate to Temporal.super.range(field)
        // ChronoField.MONTH_OF_YEAR is supported and should return 1 to 12
        ValueRange range = ym.range(ChronoField.MONTH_OF_YEAR);
        if (range.getMinimum() != 1 || range.getMaximum() != 12) {
            throw new AssertionError("range MONTH_OF_YEAR failed: " + range);
        }

        // Test a field not supported by YearMonth to check exception
        TemporalField unsupportedField = ChronoField.ERA;
        try {
            ym.range(unsupportedField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for ERA field");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
