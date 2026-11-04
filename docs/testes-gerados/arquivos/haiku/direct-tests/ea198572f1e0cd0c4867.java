/*
 * @test
 * @summary Tests observable behavior of YearMonth.withMonth(int)
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidMonthsUpdateMonthAndKeepYear();
        testBoundaryMonths();
        testNegativeYearIsPreserved();
        testInvalidMonthsThrow();
    }

    static void testValidMonthsUpdateMonthAndKeepYear() {
        YearMonth base = YearMonth.of(2020, 1);
        for (int m = 1; m <= 12; m++) {
            YearMonth result = base.withMonth(m);
            check(result.getYear() == 2020, "year must be preserved for month " + m);
            check(result.getMonthValue() == m, "month must be " + m);
        }
    }

    static void testBoundaryMonths() {
        YearMonth base = YearMonth.of(1999, 6);
        check(base.withMonth(1).getMonthValue() == 1, "withMonth(1) must give January");
        check(base.withMonth(12).getMonthValue() == 12, "withMonth(12) must give December");
        check(base.withMonth(12).getYear() == 1999, "year must remain 1999");
    }

    static void testNegativeYearIsPreserved() {
        YearMonth base = YearMonth.of(-5, 6);
        YearMonth result = base.withMonth(2);
        check(result.getYear() == -5, "negative year must be preserved");
        check(result.getMonthValue() == 2, "month must be 2");
    }

    static void testInvalidMonthsThrow() {
        YearMonth base = YearMonth.of(2020, 5);
        int[] invalid = {0, 13, -1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int m : invalid) {
            boolean thrown = false;
            try {
                base.withMonth(m);
            } catch (DateTimeException e) {
                thrown = true;
            }
            check(thrown, "withMonth(" + m + ") must throw DateTimeException");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
