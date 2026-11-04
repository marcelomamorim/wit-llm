/*
 * @test
 * @summary Tests YearMonth.minusYears(long) observable results, including the Long.MIN_VALUE overflow path
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        check(YearMonth.of(2020, 5).minusYears(3).equals(YearMonth.of(2017, 5)), 1);
        check(YearMonth.of(2020, 5).minusYears(0).equals(YearMonth.of(2020, 5)), 2);
        check(YearMonth.of(2020, 5).minusYears(-2).equals(YearMonth.of(2022, 5)), 3);
        check(YearMonth.of(2020, 1).minusYears(2020).equals(YearMonth.of(0, 1)), 4);
        check(YearMonth.of(2020, 12).minusYears(1).getMonthValue() == 12, 5);

        boolean thrown = false;
        try {
            YearMonth.of(2020, 1).minusYears(Long.MIN_VALUE);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, 6);
    }

    static void check(boolean condition, int id) {
        if (!condition) {
            throw new RuntimeException("YearMonthWitupTest case failed: " + id);
        }
    }
}
