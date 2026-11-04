/*
 * @test
 * @summary Tests YearMonth.plusMonths behavior for zero, positive and negative offsets, year boundaries, round trips and out-of-range results
 * @run main YearMonthWitupTest
 */
import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        YearMonth ym = YearMonth.of(2020, 3);
        if (ym.plusMonths(0) != ym) {
            throw new AssertionError("plusMonths(0) should return this");
        }

        check(YearMonth.of(2020, 1).plusMonths(1), YearMonth.of(2020, 2));
        check(YearMonth.of(2020, 3).plusMonths(12), YearMonth.of(2021, 3));
        check(YearMonth.of(2020, 1).plusMonths(-1), YearMonth.of(2019, 12));
        check(YearMonth.of(2020, 1).plusMonths(-12), YearMonth.of(2019, 1));
        check(YearMonth.of(2020, 1).plusMonths(-13), YearMonth.of(2018, 12));
        check(YearMonth.of(2019, 12).plusMonths(1), YearMonth.of(2020, 1));
        check(YearMonth.of(0, 1).plusMonths(-1), YearMonth.of(-1, 12));

        YearMonth base = YearMonth.of(1999, 7);
        check(base.plusMonths(1000).plusMonths(-1000), base);

        expectDateTimeException(YearMonth.of(999999999, 12), 1);
        expectDateTimeException(YearMonth.of(-999999999, 1), -1);
        expectDateTimeException(YearMonth.of(2020, 1), Long.MAX_VALUE);
        expectDateTimeException(YearMonth.of(2020, 1), Long.MIN_VALUE);
    }

    static void check(YearMonth actual, YearMonth expected) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    static void expectDateTimeException(YearMonth ym, long monthsToAdd) {
        try {
            YearMonth result = ym.plusMonths(monthsToAdd);
            throw new AssertionError("expected DateTimeException for " + ym + " plus " + monthsToAdd + " but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
