/*
 * @test
 * @summary YearMonth.range(YEAR_OF_ERA) boundaries and delegation to default range for other fields
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        check(YearMonth.of(2000, 1).range(ChronoField.YEAR_OF_ERA)
                .equals(ValueRange.of(1, 999999999)), "positive year YEAR_OF_ERA range");
        check(YearMonth.of(1, 12).range(ChronoField.YEAR_OF_ERA)
                .equals(ValueRange.of(1, 999999999)), "year 1 YEAR_OF_ERA range");
        check(YearMonth.of(0, 1).range(ChronoField.YEAR_OF_ERA)
                .equals(ValueRange.of(1, 1000000000)), "year 0 YEAR_OF_ERA range");
        check(YearMonth.of(-5, 3).range(ChronoField.YEAR_OF_ERA)
                .equals(ValueRange.of(1, 1000000000)), "negative year YEAR_OF_ERA range");
        check(YearMonth.of(-999999999, 6).range(ChronoField.YEAR_OF_ERA)
                .equals(ValueRange.of(1, 1000000000)), "minimum year YEAR_OF_ERA range");

        check(YearMonth.of(2000, 1).range(ChronoField.MONTH_OF_YEAR)
                .equals(ValueRange.of(1, 12)), "MONTH_OF_YEAR delegated range");
        check(YearMonth.of(2000, 1).range(ChronoField.YEAR)
                .equals(ValueRange.of(-999999999, 999999999)), "YEAR delegated range");

        boolean thrown = false;
        try {
            YearMonth.of(2000, 1).range(ChronoField.HOUR_OF_DAY);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "unsupported field must throw UnsupportedTemporalTypeException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
