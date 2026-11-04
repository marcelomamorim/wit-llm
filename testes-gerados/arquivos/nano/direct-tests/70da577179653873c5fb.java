/*
 * @test
 * @summary Test Year.atMonth(int) method for various valid and invalid inputs.
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtMonthValid();
        testAtMonthInvalid();
    }

    static void testAtMonthValid() {
        Year year = Year.of(2023);
        for (int month = 1; month <= 12; month++) {
            YearMonth ym = year.atMonth(month);
            assert ym.getYear() == 2023 : "Year mismatch";
            assert ym.getMonthValue() == month : "Month mismatch";
        }
    }

    static void testAtMonthInvalid() {
        Year year = Year.of(2023);
        int[] invalidMonths = {0, 13, -1, 100};
        for (int invalidMonth : invalidMonths) {
            try {
                year.atMonth(invalidMonth);
                assert false : "Expected exception for invalid month: " + invalidMonth;
            } catch (DateTimeException e) {
                // Expected exception
            }
        }
    }
}
