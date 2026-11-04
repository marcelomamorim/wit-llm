/*
 * @test
 * @summary Test java.time.YearMonth.get(TemporalField) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testGetYear();
        testGetMonthOfYear();
        testGetYearOfEra();
        testGetEra();
        testGetProlepticMonth();
        testGetUnsupportedField();
    }

    private static void testGetYear() {
        YearMonth ym = YearMonth.of(2018, Month.SEPTEMBER);
        int year = ym.get(ChronoField.YEAR);
        if (year != 2018) {
            throw new AssertionError("Expected year 2018 but got " + year);
        }
    }

    private static void testGetMonthOfYear() {
        YearMonth ym = YearMonth.of(2018, Month.SEPTEMBER);
        int month = ym.get(ChronoField.MONTH_OF_YEAR);
        if (month != 9) {
            throw new AssertionError("Expected month 9 but got " + month);
        }
    }

    private static void testGetYearOfEra() {
        YearMonth ym = YearMonth.of(2018, Month.SEPTEMBER);
        int yoe = ym.get(ChronoField.YEAR_OF_ERA);
        if (yoe != 2018) {
            throw new AssertionError("Expected year of era 2018 but got " + yoe);
        }
        YearMonth ymBCE = YearMonth.of(-1, Month.JANUARY); // year -1 is 2 BCE
        int yoeBCE = ymBCE.get(ChronoField.YEAR_OF_ERA);
        if (yoeBCE != 2) {
            throw new AssertionError("Expected year of era 2 but got " + yoeBCE);
        }
    }

    private static void testGetEra() {
        YearMonth ym = YearMonth.of(2018, Month.SEPTEMBER);
        int era = ym.get(ChronoField.ERA);
        if (era != 1) { // 1 = CE
            throw new AssertionError("Expected era 1 (CE) but got " + era);
        }
        YearMonth ymBCE = YearMonth.of(-1, Month.JANUARY);
        int eraBCE = ymBCE.get(ChronoField.ERA);
        if (eraBCE != 0) { // 0 = BCE
            throw new AssertionError("Expected era 0 (BCE) but got " + eraBCE);
        }
    }

    private static void testGetProlepticMonth() {
        YearMonth ym = YearMonth.of(2018, Month.SEPTEMBER);
        int pm = ym.get(ChronoField.PROLEPTIC_MONTH);
        // proleptic month = year * 12 + month - 1
        int expected = 2018 * 12 + 9 - 1;
        if (pm != expected) {
            throw new AssertionError("Expected proleptic month " + expected + " but got " + pm);
        }
    }

    private static void testGetUnsupportedField() {
        YearMonth ym = YearMonth.of(2018, Month.SEPTEMBER);
        TemporalField unsupported = ChronoField.DAY_OF_MONTH;
        try {
            ym.get(unsupported);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for field DAY_OF_MONTH");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
