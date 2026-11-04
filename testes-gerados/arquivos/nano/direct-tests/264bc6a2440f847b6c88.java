/*
 * @test
 * @summary Tests the get(TemporalField) method of YearMonth for various TemporalFields.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testGetWithYear();
        testGetWithMonth();
        testGetWithProlepticMonth();
        testGetWithEra();
        testGetWithYearOfEra();
        testGetWithUnsupportedField();
    }

    static void testGetWithYear() {
        YearMonth ym = YearMonth.of(2023, 3);
        int year = ym.get(ChronoField.YEAR);
        assert year == 2023 : "Expected year 2023, got " + year;
    }

    static void testGetWithMonth() {
        YearMonth ym = YearMonth.of(2023, 3);
        int month = ym.get(ChronoField.MONTH_OF_YEAR);
        assert month == 3 : "Expected month 3, got " + month;
    }

    static void testGetWithProlepticMonth() {
        YearMonth ym = YearMonth.of(2023, 3);
        int prolepticMonth = ym.get(ChronoField.PROLEPTIC_MONTH);
        int expected = 2023 * 12 + 3 - 1;
        assert prolepticMonth == expected : "Expected " + expected + ", got " + prolepticMonth;
    }

    static void testGetWithEra() {
        YearMonth ym = YearMonth.of(2023, 3);
        int era = ym.get(ChronoField.ERA);
        assert era == 1 : "Expected era 1, got " + era;
    }

    static void testGetWithYearOfEra() {
        YearMonth ym = YearMonth.of(2023, 3);
        int yoe = ym.get(ChronoField.YEAR_OF_ERA);
        assert yoe == 2023 : "Expected year of era 2023, got " + yoe;
    }

    static void testGetWithUnsupportedField() {
        YearMonth ym = YearMonth.of(2023, 3);
        try {
            ym.get(ChronoField.DAY_OF_MONTH);
            assert false : "Expected UnsupportedTemporalTypeException";
        } catch (java.time.temporal.UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
