/*
 * @test
 * @summary Test YearMonth.range(TemporalField) for different fields.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testRangeYearOfEra();
        testRangeOtherField();
    }

    static void testRangeYearOfEra() {
        YearMonth ymPositive = YearMonth.of(2023, 5);
        ValueRange rangePositive = ymPositive.range(java.time.temporal.ChronoField.YEAR_OF_ERA);
        assert rangePositive.getMinimum() == 1 : "Expected minimum 1";
        assert rangePositive.getMaximum() == YearMonth.MAX_VALUE : "Expected maximum YearMonth.MAX_VALUE";

        YearMonth ymNegative = YearMonth.of(-2023, 5);
        ValueRange rangeNegative = ymNegative.range(java.time.temporal.ChronoField.YEAR_OF_ERA);
        assert rangeNegative.getMinimum() == 1 : "Expected minimum 1";
        assert rangeNegative.getMaximum() == YearMonth.MAX_VALUE : "Expected maximum YearMonth.MAX_VALUE";
    }

    static void testRangeOtherField() {
        YearMonth ym = YearMonth.of(2023, 5);
        // For a field not explicitly handled, should fallback to Temporal.super.range(field)
        // Since super.range() is not accessible, we test that no exception is thrown and a non-null ValueRange is returned
        try {
            ValueRange vr = ym.range(java.time.temporal.ChronoField.MONTH_OF_YEAR);
            assert vr != null : "ValueRange should not be null";
        } catch (Exception e) {
            assert false : "Exception thrown for field not explicitly handled: " + e;
        }
    }
}
