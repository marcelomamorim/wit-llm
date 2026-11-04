/*
 * @test
 * @summary Witup-derived checks for YearMonth.plusMonths(long) boundary and exception behavior
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testZeroReturnsSameInstance();
        testPositiveAddition();
        testNegativeAddition();
        testYearRollover();
        testUpperBoundaryAccepted();
        testUpperBoundaryExceeded();
        testLowerBoundaryExceeded();
        testOverflowingMonthsToAdd();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectDateTimeException(Runnable action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(message + ": no DateTimeException thrown");
    }

    static void testZeroReturnsSameInstance() {
        YearMonth ym = YearMonth.of(2020, 5);
        check(ym.plusMonths(0) == ym, "plusMonths(0) should return this");
    }

    static void testPositiveAddition() {
        YearMonth result = YearMonth.of(2020, 1).plusMonths(13);
        check(result.equals(YearMonth.of(2021, 2)), "2020-01 + 13 months should be 2021-02, got " + result);
    }

    static void testNegativeAddition() {
        YearMonth result = YearMonth.of(2020, 1).plusMonths(-1);
        check(result.equals(YearMonth.of(2019, 12)), "2020-01 - 1 month should be 2019-12, got " + result);
    }

    static void testYearRollover() {
        YearMonth result = YearMonth.of(2020, 12).plusMonths(1);
        check(result.equals(YearMonth.of(2021, 1)), "2020-12 + 1 month should be 2021-01, got " + result);
    }

    static void testUpperBoundaryAccepted() {
        YearMonth result = YearMonth.of(999999999, 1).plusMonths(11);
        check(result.equals(YearMonth.of(999999999, 12)),
              "999999999-01 + 11 months should be 999999999-12, got " + result);
    }

    static void testUpperBoundaryExceeded() {
        YearMonth ym = YearMonth.of(999999999, 12);
        expectDateTimeException(() -> ym.plusMonths(1),
                                "999999999-12 + 1 month should exceed the year range");
        expectDateTimeException(() -> ym.plusMonths(12),
                                "999999999-12 + 12 months should exceed the year range");
    }

    static void testLowerBoundaryExceeded() {
        YearMonth ym = YearMonth.of(-999999999, 1);
        expectDateTimeException(() -> ym.plusMonths(-1),
                                "-999999999-01 - 1 month should exceed the year range");
    }

    static void testOverflowingMonthsToAdd() {
        YearMonth ym = YearMonth.of(2020, 1);
        expectDateTimeException(() -> ym.plusMonths(Long.MAX_VALUE),
                                "plusMonths(Long.MAX_VALUE) should throw DateTimeException");
    }
}
