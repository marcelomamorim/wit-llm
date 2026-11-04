/*
 * @test
 * @summary Test java.time.YearMonth.of(int, Month) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.util.Objects;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testOfWithValidMonth();
        testOfWithNullMonth();
    }

    private static void testOfWithValidMonth() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        if (ym.getYear() != 2020) {
            throw new AssertionError("Year should be 2020 but was " + ym.getYear());
        }
        if (ym.getMonth() != Month.JANUARY) {
            throw new AssertionError("Month should be JANUARY but was " + ym.getMonth());
        }
    }

    private static void testOfWithNullMonth() {
        try {
            YearMonth.of(2020, null);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            // expected
            if (!"month".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message should be 'month' but was '" + e.getMessage() + "'");
            }
        }
    }
}
