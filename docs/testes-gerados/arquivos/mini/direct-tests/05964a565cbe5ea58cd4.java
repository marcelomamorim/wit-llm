/*
 * @test
 * @summary Test java.time.YearMonth.atDay(int) method
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testAtDayValid();
        testAtDayInvalid();
    }

    private static void testAtDayValid() {
        YearMonth ym = YearMonth.of(2020, Month.FEBRUARY);
        LocalDate ld = ym.atDay(29);
        if (ld.getYear() != 2020 || ld.getMonthValue() != 2 || ld.getDayOfMonth() != 29) {
            throw new AssertionError("Expected 2020-02-29 but got " + ld);
        }
        // Test a normal day
        ld = ym.atDay(1);
        if (ld.getYear() != 2020 || ld.getMonthValue() != 2 || ld.getDayOfMonth() != 1) {
            throw new AssertionError("Expected 2020-02-01 but got " + ld);
        }
    }

    private static void testAtDayInvalid() {
        YearMonth ym = YearMonth.of(2019, 2); // Not a leap year
        boolean thrown = false;
        try {
            ym.atDay(29); // Invalid day for Feb 2019
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for invalid day 29 in Feb 2019");
        }

        thrown = false;
        try {
            ym.atDay(0); // Day zero invalid
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for day 0");
        }

        thrown = false;
        try {
            ym.atDay(32); // Day too large
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for day 32");
        }
    }
}
