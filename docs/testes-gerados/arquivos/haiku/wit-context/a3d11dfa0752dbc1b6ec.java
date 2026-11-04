/*
 * @test
 * @summary Witup-derived boundary and overflow checks for YearMonth.plusYears(long)
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testZeroReturnsSameValue() {
        YearMonth ym = YearMonth.of(2020, 2);
        check(ym.plusYears(0) == ym, "plusYears(0) should return this");
    }

    static void testRegularAddition() {
        YearMonth ym = YearMonth.of(2020, 2);
        check(ym.plusYears(3).equals(YearMonth.of(2023, 2)), "2020-02 + 3 years should be 2023-02");
        check(ym.plusYears(-5).equals(YearMonth.of(2015, 2)), "2020-02 - 5 years should be 2015-02");
    }

    static void testMaxYearBoundaryNoOverflow() {
        YearMonth ym = YearMonth.of(999999999, 12);
        check(ym.plusYears(0).equals(ym), "max year plusYears(0) should be unchanged");
        check(ym.plusYears(-1).equals(YearMonth.of(999999998, 12)), "max year minus 1 should be valid");
    }

    static void testMaxYearOverflowThrows() {
        boolean thrown = false;
        try {
            YearMonth.of(999999999, 12).plusYears(1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "plusYears(1) beyond max year should throw DateTimeException");
    }

    static void testMinYearUnderflowThrows() {
        boolean thrown = false;
        try {
            YearMonth.of(-999999999, 1).plusYears(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "plusYears(-1) below min year should throw DateTimeException");
    }

    static void testLongOverflowThrows() {
        boolean thrown = false;
        try {
            YearMonth.of(2020, 1).plusYears(Long.MAX_VALUE);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "plusYears(Long.MAX_VALUE) should throw DateTimeException");
    }

    public static void main(String[] args) {
        testZeroReturnsSameValue();
        testRegularAddition();
        testMaxYearBoundaryNoOverflow();
        testMaxYearOverflowThrows();
        testMinYearUnderflowThrows();
        testLongOverflowThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
