/*
 * @test
 * @summary Test java.time.Year.atDay(int) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.LocalDate;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testAtDayValid();
        testAtDayInvalidLow();
        testAtDayInvalidHigh();
    }

    private static void testAtDayValid() {
        Year y = Year.of(2020); // leap year
        LocalDate ld = y.atDay(60); // 29 Feb 2020
        if (!ld.equals(LocalDate.of(2020, 2, 29))) {
            throw new AssertionError("Expected 2020-02-29 but got " + ld);
        }
        // Test first day
        ld = y.atDay(1);
        if (!ld.equals(LocalDate.of(2020, 1, 1))) {
            throw new AssertionError("Expected 2020-01-01 but got " + ld);
        }
        // Test last day
        ld = y.atDay(366);
        if (!ld.equals(LocalDate.of(2020, 12, 31))) {
            throw new AssertionError("Expected 2020-12-31 but got " + ld);
        }
    }

    private static void testAtDayInvalidLow() {
        Year y = Year.of(2019); // non-leap year
        try {
            y.atDay(0);
            throw new AssertionError("Expected DateTimeException for dayOfYear=0");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testAtDayInvalidHigh() {
        Year y = Year.of(2019); // non-leap year
        try {
            y.atDay(366);
            throw new AssertionError("Expected DateTimeException for dayOfYear=366 in non-leap year");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
