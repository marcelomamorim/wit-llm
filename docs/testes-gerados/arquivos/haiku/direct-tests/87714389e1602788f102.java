/*
 * @test
 * @summary Verifies YearMonth.getMonth() returns the Month matching the month-of-year used to construct the value
 * @run main YearMonthWitupTest
 */

import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        for (int m = 1; m <= 12; m++) {
            YearMonth ym = YearMonth.of(2018, m);
            Month actual = ym.getMonth();
            Month expected = Month.of(m);
            if (actual != expected) {
                throw new RuntimeException("getMonth() for month " + m + " returned " + actual + ", expected " + expected);
            }
        }

        if (YearMonth.of(2018, Month.JANUARY).getMonth() != Month.JANUARY) {
            throw new RuntimeException("of(int, Month) JANUARY: getMonth() mismatch");
        }
        if (YearMonth.of(2018, Month.DECEMBER).getMonth() != Month.DECEMBER) {
            throw new RuntimeException("of(int, Month) DECEMBER: getMonth() mismatch");
        }

        if (YearMonth.of(-1, 12).getMonth() != Month.DECEMBER) {
            throw new RuntimeException("negative year: getMonth() mismatch");
        }
        if (YearMonth.of(999999999, 1).getMonth() != Month.JANUARY) {
            throw new RuntimeException("maximum year: getMonth() mismatch");
        }
    }
}
