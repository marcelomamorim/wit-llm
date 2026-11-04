/*
 * @test
 * @summary Test LocalDate.getDayOfYear() correctness for leap and non-leap years
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        // Test a non-leap year date: 2019-03-01
        LocalDate date1 = LocalDate.of(2019, Month.MARCH, 1);
        int dayOfYear1 = date1.getDayOfYear();
        if (dayOfYear1 != 60) {
            throw new AssertionError("Expected dayOfYear 60 for 2019-03-01 but got " + dayOfYear1);
        }

        // Test a leap year date: 2020-03-01
        LocalDate date2 = LocalDate.of(2020, Month.MARCH, 1);
        int dayOfYear2 = date2.getDayOfYear();
        if (dayOfYear2 != 61) {
            throw new AssertionError("Expected dayOfYear 61 for 2020-03-01 but got " + dayOfYear2);
        }

        // Test first day of year non-leap: 2019-01-01
        LocalDate date3 = LocalDate.of(2019, Month.JANUARY, 1);
        int dayOfYear3 = date3.getDayOfYear();
        if (dayOfYear3 != 1) {
            throw new AssertionError("Expected dayOfYear 1 for 2019-01-01 but got " + dayOfYear3);
        }

        // Test last day of year non-leap: 2019-12-31
        LocalDate date4 = LocalDate.of(2019, Month.DECEMBER, 31);
        int dayOfYear4 = date4.getDayOfYear();
        if (dayOfYear4 != 365) {
            throw new AssertionError("Expected dayOfYear 365 for 2019-12-31 but got " + dayOfYear4);
        }

        // Test last day of year leap: 2020-12-31
        LocalDate date5 = LocalDate.of(2020, Month.DECEMBER, 31);
        int dayOfYear5 = date5.getDayOfYear();
        if (dayOfYear5 != 366) {
            throw new AssertionError("Expected dayOfYear 366 for 2020-12-31 but got " + dayOfYear5);
        }
    }
}
