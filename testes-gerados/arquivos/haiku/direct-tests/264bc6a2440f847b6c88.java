/*
 * @test
 * @summary Verifies YearMonth.get(TemporalField) returns the expected int values for supported fields and rejects unsupported fields
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testSupportedFieldsOf2007December();
        testEpochYearZeroEraAndYearOfEra();
        testNegativeYearEraAndYearOfEra();
        testUnsupportedFieldThrows();
    }

    static void testSupportedFieldsOf2007December() {
        YearMonth ym = YearMonth.of(2007, 12);
        check(ym.get(ChronoField.YEAR) == 2007, "YEAR");
        check(ym.get(ChronoField.MONTH_OF_YEAR) == 12, "MONTH_OF_YEAR");
        check(ym.get(ChronoField.PROLEPTIC_MONTH) == 2007 * 12 + 11, "PROLEPTIC_MONTH");
        check(ym.get(ChronoField.ERA) == 1, "ERA AD");
        check(ym.get(ChronoField.YEAR_OF_ERA) == 2007, "YEAR_OF_ERA");
    }

    static void testEpochYearZeroEraAndYearOfEra() {
        YearMonth ym = YearMonth.of(0, 1);
        check(ym.get(ChronoField.YEAR) == 0, "year zero YEAR");
        check(ym.get(ChronoField.ERA) == 0, "year zero ERA");
        check(ym.get(ChronoField.YEAR_OF_ERA) == 1, "year zero YEAR_OF_ERA");
    }

    static void testNegativeYearEraAndYearOfEra() {
        YearMonth ym = YearMonth.of(-1, Month.MARCH);
        check(ym.get(ChronoField.YEAR) == -1, "negative YEAR");
        check(ym.get(ChronoField.MONTH_OF_YEAR) == 3, "negative MONTH_OF_YEAR");
        check(ym.get(ChronoField.ERA) == 0, "negative ERA BCE");
        check(ym.get(ChronoField.YEAR_OF_ERA) == 2, "negative YEAR_OF_ERA");
    }

    static void testUnsupportedFieldThrows() {
        YearMonth ym = YearMonth.of(2007, 12);
        boolean thrown = false;
        try {
            ym.get(ChronoField.DAY_OF_MONTH);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "DAY_OF_MONTH should throw UnsupportedTemporalTypeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
