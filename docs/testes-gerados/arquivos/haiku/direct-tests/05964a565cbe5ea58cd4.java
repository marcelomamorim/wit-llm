/*
 * @test
 * @summary Verifies YearMonth.atDay(int) returns the expected LocalDate and rejects invalid days
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        LocalDate d = YearMonth.of(2020, 2).atDay(29);
        check(d.getYear() == 2020 && d.getMonthValue() == 2 && d.getDayOfMonth() == 29, "leap day 2020-02-29");

        d = YearMonth.of(2019, Month.JANUARY).atDay(1);
        check(d.getYear() == 2019 && d.getMonthValue() == 1 && d.getDayOfMonth() == 1, "first day 2019-01-01");

        d = YearMonth.of(2019, 12).atDay(31);
        check(d.getYear() == 2019 && d.getMonthValue() == 12 && d.getDayOfMonth() == 31, "last day 2019-12-31");

        d = YearMonth.of(2019, 2).atDay(28);
        check(d.getYear() == 2019 && d.getMonthValue() == 2 && d.getDayOfMonth() == 28, "non-leap February 28");

        expectDateTimeException(() -> YearMonth.of(2019, 2).atDay(29), "Feb 29 in non-leap year must fail");
        expectDateTimeException(() -> YearMonth.of(2019, 1).atDay(0), "day 0 must fail");
        expectDateTimeException(() -> YearMonth.of(2019, 1).atDay(32), "day 32 must fail");
        expectDateTimeException(() -> YearMonth.of(2019, 4).atDay(31), "April 31 must fail");
    }

    private static void expectDateTimeException(Runnable r, String msg) {
        try {
            r.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(msg);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
