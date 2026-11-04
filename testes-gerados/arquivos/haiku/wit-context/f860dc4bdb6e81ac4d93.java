/*
 * @test
 * @summary Witup-generated checks for YearMonth.minusMonths behavior
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        YearMonth ym = YearMonth.of(2020, 3);
        check(ym.minusMonths(1).equals(YearMonth.of(2020, 2)), 1);
        check(YearMonth.of(2020, 1).minusMonths(1).equals(YearMonth.of(2019, 12)), 2);
        check(ym.minusMonths(0).equals(ym), 3);
        check(ym.minusMonths(15).equals(YearMonth.of(2018, 12)), 4);
        expectDateTimeException(() -> YearMonth.of(-999999999, 1).minusMonths(1), 5);
        expectDateTimeException(() -> ym.minusMonths(Long.MIN_VALUE), 6);
    }

    private static void check(boolean condition, int id) {
        if (!condition) {
            throw new RuntimeException("fail " + id);
        }
    }

    private static void expectDateTimeException(Runnable action, int id) {
        try {
            action.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("expected DateTimeException " + id);
    }
}
