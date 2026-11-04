/*
 * @test
 * @summary Tests YearMonth.of(int year, int month) valid results and out-of-range rejection
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testBoundaryYears();
        testInvalidMonths();
        testInvalidYears();
    }

    static void testValidValues() {
        YearMonth ym = YearMonth.of(2020, 1);
        check(ym.getYear() == 2020, "year 2020");
        check(ym.getMonthValue() == 1, "month 1");
        check(ym.getMonth() == Month.JANUARY, "JANUARY");

        YearMonth dec = YearMonth.of(1999, 12);
        check(dec.getYear() == 1999, "year 1999");
        check(dec.getMonthValue() == 12, "month 12");
        check(dec.getMonth() == Month.DECEMBER, "DECEMBER");

        check(YearMonth.of(2000, 2).lengthOfMonth() == 29, "leap February length");
        check(YearMonth.of(2001, 2).lengthOfMonth() == 28, "non-leap February length");
    }

    static void testBoundaryYears() {
        YearMonth max = YearMonth.of(999999999, 12);
        check(max.getYear() == 999999999, "max year");
        check(max.getMonth() == Month.DECEMBER, "max year month");

        YearMonth min = YearMonth.of(-999999999, 1);
        check(min.getYear() == -999999999, "min year");
        check(min.getMonth() == Month.JANUARY, "min year month");
    }

    static void testInvalidMonths() {
        expectDateTimeException(2020, 0);
        expectDateTimeException(2020, 13);
        expectDateTimeException(2020, -1);
    }

    static void testInvalidYears() {
        expectDateTimeException(1000000000, 1);
        expectDateTimeException(-1000000000, 1);
    }

    static void expectDateTimeException(int year, int month) {
        try {
            YearMonth ym = YearMonth.of(year, month);
            throw new RuntimeException("Expected DateTimeException for year=" + year
                    + ", month=" + month + " but got " + ym);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
