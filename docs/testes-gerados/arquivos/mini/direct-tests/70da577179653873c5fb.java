/*
 * @test
 * @summary Test java.time.Year.atMonth(int) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {
    public static void main(String[] args) {
        testAtMonthValid();
        testAtMonthBoundary();
    }

    private static void testAtMonthValid() {
        Year y = Year.of(2020);
        YearMonth ym = y.atMonth(5);
        if (ym.getYear() != 2020) {
            throw new AssertionError("YearMonth year mismatch: expected 2020 but got " + ym.getYear());
        }
        if (ym.getMonthValue() != 5) {
            throw new AssertionError("YearMonth month mismatch: expected 5 but got " + ym.getMonthValue());
        }
    }

    private static void testAtMonthBoundary() {
        Year y = Year.of(1999);
        // Test lower boundary month = 1
        YearMonth ym1 = y.atMonth(1);
        if (ym1.getYear() != 1999 || ym1.getMonthValue() != 1) {
            throw new AssertionError("YearMonth boundary test failed for month 1");
        }
        // Test upper boundary month = 12
        YearMonth ym12 = y.atMonth(12);
        if (ym12.getYear() != 1999 || ym12.getMonthValue() != 12) {
            throw new AssertionError("YearMonth boundary test failed for month 12");
        }
    }
}
