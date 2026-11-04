/*
 * @test
 * @summary Test java.time.Year.atMonth(Month) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.Month;
import java.time.YearMonth;

public class YearWitupTest {
    public static void main(String[] args) {
        testAtMonthBasic();
        testAtMonthAllMonths();
    }

    private static void testAtMonthBasic() {
        Year y = Year.of(2020);
        YearMonth ym = y.atMonth(Month.JANUARY);
        if (ym.getYear() != 2020) {
            throw new AssertionError("YearMonth year mismatch: expected 2020 but got " + ym.getYear());
        }
        if (ym.getMonth() != Month.JANUARY) {
            throw new AssertionError("YearMonth month mismatch: expected JANUARY but got " + ym.getMonth());
        }
    }

    private static void testAtMonthAllMonths() {
        Year y = Year.of(1999);
        for (Month m : Month.values()) {
            YearMonth ym = y.atMonth(m);
            if (ym.getYear() != 1999) {
                throw new AssertionError("YearMonth year mismatch for month " + m + ": expected 1999 but got " + ym.getYear());
            }
            if (ym.getMonth() != m) {
                throw new AssertionError("YearMonth month mismatch: expected " + m + " but got " + ym.getMonth());
            }
        }
    }
}
