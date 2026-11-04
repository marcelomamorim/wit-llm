/*
 * @test
 * @summary Witup-derived tests for YearMonth.withMonth(int)
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        checkValidMonths();
        checkInvalidMonthsThrow();
        checkYearPreserved();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkValidMonths() {
        for (int m = 1; m <= 12; m++) {
            YearMonth ym = YearMonth.of(2020, 6).withMonth(m);
            check(ym.getMonthValue() == m, "month not updated to " + m);
            check(ym.getYear() == 2020, "year changed when setting month " + m);
        }
    }

    static void checkInvalidMonthsThrow() {
        int[] invalid = {0, 13, -1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int m : invalid) {
            boolean thrown = false;
            try {
                YearMonth.of(2020, 6).withMonth(m);
            } catch (DateTimeException e) {
                thrown = true;
            }
            check(thrown, "expected DateTimeException for month " + m);
        }
    }

    static void checkYearPreserved() {
        YearMonth ym = YearMonth.of(-5, 1).withMonth(12);
        check(ym.getYear() == -5, "negative year not preserved");
        check(ym.getMonthValue() == 12, "month not set to 12");
    }
}
