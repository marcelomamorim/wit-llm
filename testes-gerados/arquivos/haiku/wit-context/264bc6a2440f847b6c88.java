/*
 * @test
 * @summary Witup-derived checks for YearMonth.get(TemporalField) public behavior
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearMonthWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(long expected, long actual, String message) {
        if (expected != actual) {
            throw new RuntimeException(message + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        YearMonth ym = YearMonth.of(2020, 5);
        checkEquals(5, ym.get(ChronoField.MONTH_OF_YEAR), "MONTH_OF_YEAR");
        checkEquals(2020, ym.get(ChronoField.YEAR), "YEAR");
        checkEquals(2020, ym.get(ChronoField.YEAR_OF_ERA), "YEAR_OF_ERA");
        checkEquals(1, ym.get(ChronoField.ERA), "ERA");
        checkEquals(2020L * 12 + 4, ym.get(ChronoField.PROLEPTIC_MONTH), "PROLEPTIC_MONTH");

        YearMonth dec = YearMonth.of(2000, Month.DECEMBER);
        checkEquals(12, dec.get(ChronoField.MONTH_OF_YEAR), "DECEMBER MONTH_OF_YEAR");

        YearMonth minYear = YearMonth.of(-999_999_999, 1);
        checkEquals(-999_999_999, minYear.get(ChronoField.YEAR), "min YEAR");
        checkEquals(1_000_000_000, minYear.get(ChronoField.YEAR_OF_ERA), "min YEAR_OF_ERA");
        checkEquals(0, minYear.get(ChronoField.ERA), "min ERA");

        YearMonth maxYear = YearMonth.of(999_999_999, 12);
        checkEquals(999_999_999, maxYear.get(ChronoField.YEAR), "max YEAR");
        checkEquals(999_999_999, maxYear.get(ChronoField.YEAR_OF_ERA), "max YEAR_OF_ERA");
        checkEquals(12, maxYear.get(ChronoField.MONTH_OF_YEAR), "max MONTH_OF_YEAR");

        boolean thrown = false;
        try {
            ym.get(ChronoField.HOUR_OF_DAY);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "HOUR_OF_DAY should throw UnsupportedTemporalTypeException");
    }
}
