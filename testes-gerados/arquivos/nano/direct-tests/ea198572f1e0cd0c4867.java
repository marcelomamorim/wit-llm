/*
 * @test
 * @summary Test YearMonth.withMonth(int) method for valid and invalid inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testWithMonthValid();
        testWithMonthInvalid();
    }

    static void testWithMonthValid() {
        YearMonth ym = YearMonth.of(2023, 1);
        for (int m = 1; m <= 12; m++) {
            YearMonth result = ym.withMonth(m);
            assert result.getMonthValue() == m : "Expected month: " + m + ", but got: " + result.getMonthValue();
            assert result.getYear() == 2023 : "Year should remain unchanged";
        }
    }

    static void testWithMonthInvalid() {
        YearMonth ym = YearMonth.of(2023, 1);
        int[] invalidMonths = {0, 13, -1, 100};
        for (int invalidMonth : invalidMonths) {
            try {
                ym.withMonth(invalidMonth);
                assert false : "Expected exception for invalid month: " + invalidMonth;
            } catch (DateTimeException e) {
                // Expected exception
            }
        }
    }
}
