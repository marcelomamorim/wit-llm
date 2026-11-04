/*
 * @test
 * @summary Verify YearMonth.minusMonths behavior for regular, zero, negative, boundary and overflow inputs
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testSimpleSubtraction();
        testZeroSubtraction();
        testNegativeSubtraction();
        testYearCrossing();
        testLongMinValueThrows();
        testOutOfRangeThrows();
        System.out.println("All YearMonth.minusMonths tests passed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testSimpleSubtraction() {
        YearMonth ym = YearMonth.of(2020, 5).minusMonths(1);
        check(ym.getYear() == 2020, "year expected 2020 but was " + ym.getYear());
        check(ym.getMonthValue() == 4, "month expected 4 but was " + ym.getMonthValue());
        check("2020-04".equals(ym.toString()), "expected 2020-04 but was " + ym);
    }

    static void testZeroSubtraction() {
        YearMonth base = YearMonth.of(2020, 5);
        YearMonth result = base.minusMonths(0);
        check(result.equals(base), "minusMonths(0) should equal original, was " + result);
    }

    static void testNegativeSubtraction() {
        YearMonth ym = YearMonth.of(2020, 5).minusMonths(-2);
        check("2020-07".equals(ym.toString()), "expected 2020-07 but was " + ym);
    }

    static void testYearCrossing() {
        YearMonth ym = YearMonth.of(2020, 1).minusMonths(1);
        check("2019-12".equals(ym.toString()), "expected 2019-12 but was " + ym);

        YearMonth twelve = YearMonth.of(2020, 6).minusMonths(12);
        check("2019-06".equals(twelve.toString()), "expected 2019-06 but was " + twelve);
    }

    static void testLongMinValueThrows() {
        boolean thrown = false;
        try {
            YearMonth.of(2020, 5).minusMonths(Long.MIN_VALUE);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "minusMonths(Long.MIN_VALUE) should throw DateTimeException");
    }

    static void testOutOfRangeThrows() {
        boolean thrown = false;
        try {
            YearMonth.of(-999999999, 1).minusMonths(1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "subtracting beyond minimum year should throw DateTimeException");
    }
}
