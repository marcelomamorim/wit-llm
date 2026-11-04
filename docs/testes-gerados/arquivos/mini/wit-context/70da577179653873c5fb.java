/*
 * @test
 * @summary Test Year.atMonth(int) method for valid and invalid month inputs
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.YearMonth;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testValidMonth();
        testInvalidMonthZero();
        testInvalidMonthThirteen();
    }

    private static void testValidMonth() {
        Year y = Year.of(2020);
        for (int month = 1; month <= 12; month++) {
            YearMonth ym = y.atMonth(month);
            if (ym.getYear() != 2020 || ym.getMonthValue() != month) {
                throw new AssertionError("YearMonth has wrong year or month: " + ym);
            }
        }
    }

    private static void testInvalidMonthZero() {
        Year y = Year.of(2020);
        try {
            y.atMonth(0);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMonthThirteen() {
        Year y = Year.of(2020);
        try {
            y.atMonth(13);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
