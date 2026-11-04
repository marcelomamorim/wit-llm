/*
 * @test
 * @summary Verifies YearMonth.getMonth() returns the Month matching the stored month-of-year for every valid month
 * @run main YearMonthWitupTest
 */
import java.time.DateTimeException;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        for (int m = 1; m <= 12; m++) {
            YearMonth ym = YearMonth.of(2000, m);
            Month actual = ym.getMonth();
            Month expected = Month.of(m);
            if (actual != expected) {
                throw new RuntimeException("getMonth mismatch for month " + m + ": " + actual);
            }
        }

        YearMonth march = YearMonth.of(2000, Month.MARCH);
        if (march.getMonth() != Month.MARCH) {
            throw new RuntimeException("getMonth mismatch for Month.MARCH");
        }

        int[] invalidMonths = {0, 13, -1};
        for (int m : invalidMonths) {
            try {
                YearMonth.of(2000, m);
                throw new RuntimeException("Expected DateTimeException for month " + m);
            } catch (DateTimeException expected) {
                // expected
            }
        }
    }
}
