/*
 * @test
 * @summary Verify observable behaviour of YearMonth.range(TemporalField), including YEAR_OF_ERA boundaries and unsupported fields
 * @run main YearMonthWitupTest
 */

import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        checkYearOfEraPositiveYear();
        checkYearOfEraYearZero();
        checkYearOfEraNegativeYear();
        checkMonthOfYearRange();
        checkYearRange();
        checkUnsupportedField();
    }

    static void checkYearOfEraPositiveYear() {
        YearMonth ym = YearMonth.of(2000, 1);
        ValueRange expected = ValueRange.of(1, Year.MAX_VALUE);
        if (!ym.range(ChronoField.YEAR_OF_ERA).equals(expected)) {
            fail("YEAR_OF_ERA for positive year: " + ym.range(ChronoField.YEAR_OF_ERA));
        }
        YearMonth first = YearMonth.of(1, 12);
        if (!first.range(ChronoField.YEAR_OF_ERA).equals(expected)) {
            fail("YEAR_OF_ERA for year 1: " + first.range(ChronoField.YEAR_OF_ERA));
        }
    }

    static void checkYearOfEraYearZero() {
        YearMonth ym = YearMonth.of(0, 1);
        ValueRange expected = ValueRange.of(1, (long) Year.MAX_VALUE + 1);
        if (!ym.range(ChronoField.YEAR_OF_ERA).equals(expected)) {
            fail("YEAR_OF_ERA for year 0: " + ym.range(ChronoField.YEAR_OF_ERA));
        }
    }

    static void checkYearOfEraNegativeYear() {
        YearMonth ym = YearMonth.of(-1, 6);
        ValueRange expected = ValueRange.of(1, (long) Year.MAX_VALUE + 1);
        if (!ym.range(ChronoField.YEAR_OF_ERA).equals(expected)) {
            fail("YEAR_OF_ERA for year -1: " + ym.range(ChronoField.YEAR_OF_ERA));
        }
    }

    static void checkMonthOfYearRange() {
        YearMonth ym = YearMonth.of(2000, 2);
        if (!ym.range(ChronoField.MONTH_OF_YEAR).equals(ValueRange.of(1, 12))) {
            fail("MONTH_OF_YEAR range: " + ym.range(ChronoField.MONTH_OF_YEAR));
        }
    }

    static void checkYearRange() {
        YearMonth ym = YearMonth.of(2000, 2);
        if (!ym.range(ChronoField.YEAR).equals(ChronoField.YEAR.range())) {
            fail("YEAR range: " + ym.range(ChronoField.YEAR));
        }
    }

    static void checkUnsupportedField() {
        YearMonth ym = YearMonth.of(2000, 2);
        try {
            ym.range(ChronoField.DAY_OF_MONTH);
            fail("expected UnsupportedTemporalTypeException for DAY_OF_MONTH");
        } catch (UnsupportedTemporalTypeException expected) {
            // expected
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
