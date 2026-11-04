/*
 * @test
 * @summary Witup-derived tests for Year.atMonth(Month)
 * @run main YearWitupTest
 */

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {

    public static void main(String[] args) {
        testNullMonthThrowsNPE();
        testAtMonthReturnsExpectedYearMonth();
        testAtMonthAllMonthsForLeapYear();
        testBoundaryYears();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullMonthThrowsNPE() {
        try {
            Year.of(2007).atMonth(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null month");
    }

    static void testAtMonthReturnsExpectedYearMonth() {
        YearMonth ym = Year.of(2007).atMonth(Month.FEBRUARY);
        check(ym.getYear() == 2007, "year mismatch: " + ym.getYear());
        check(ym.getMonthValue() == 2, "month mismatch: " + ym.getMonthValue());
        check(ym.equals(YearMonth.of(2007, Month.FEBRUARY)), "expected equal to YearMonth.of(2007, FEBRUARY)");

        YearMonth dec = Year.of(2007).atMonth(Month.DECEMBER);
        check(dec.getMonthValue() == 12, "december mismatch: " + dec.getMonthValue());
        check(dec.getYear() == 2007, "year mismatch for december: " + dec.getYear());
    }

    static void testAtMonthAllMonthsForLeapYear() {
        Year leap = Year.of(2008);
        Month[] months = Month.values();
        for (Month m : months) {
            YearMonth ym = leap.atMonth(m);
            check(ym.getYear() == 2008, "leap year mismatch for " + m);
            check(ym.getMonth() == m, "month mismatch for " + m);
            check(ym.lengthOfMonth() == m.length(true), "length mismatch for " + m);
        }
    }

    static void testBoundaryYears() {
        YearMonth low = Year.of(-999999999).atMonth(Month.JANUARY);
        check(low.getYear() == -999999999, "lower boundary year mismatch: " + low.getYear());
        check(low.getMonthValue() == 1, "lower boundary month mismatch");

        YearMonth high = Year.of(999999999).atMonth(Month.DECEMBER);
        check(high.getYear() == 999999999, "upper boundary year mismatch: " + high.getYear());
        check(high.getMonthValue() == 12, "upper boundary month mismatch");
    }
}
