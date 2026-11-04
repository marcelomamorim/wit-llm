/*
 * @test
 * @summary Test java.time.LocalDate.getDayOfYear() method for correctness and exception safety
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testGetDayOfYearNormal();
        testGetDayOfYearInvalidMonth();
        System.out.println("All tests passed.");
    }

    private static void testGetDayOfYearNormal() {
        // Test known date: 2018-01-01 -> dayOfYear = 1
        LocalDate date = LocalDate.of(2018, 1, 1);
        int dayOfYear = date.getDayOfYear();
        if (dayOfYear != 1) {
            throw new AssertionError("Expected dayOfYear 1 for 2018-01-01 but got " + dayOfYear);
        }

        // Test known date: 2018-12-31 -> dayOfYear = 365 (non-leap year)
        date = LocalDate.of(2018, 12, 31);
        dayOfYear = date.getDayOfYear();
        if (dayOfYear != 365) {
            throw new AssertionError("Expected dayOfYear 365 for 2018-12-31 but got " + dayOfYear);
        }

        // Test leap year: 2020-02-29 -> dayOfYear = 60
        date = LocalDate.of(2020, 2, 29);
        dayOfYear = date.getDayOfYear();
        if (dayOfYear != 60) {
            throw new AssertionError("Expected dayOfYear 60 for 2020-02-29 but got " + dayOfYear);
        }
    }

    private static void testGetDayOfYearInvalidMonth() {
        // The method getDayOfYear() internally calls getMonth() which returns a valid Month enum.
        // Since LocalDate.of() validates month, it is impossible to create a LocalDate with invalid month.
        // Therefore, no DateTimeException is expected from getDayOfYear() on a valid LocalDate instance.
        // This test documents that no exception is thrown for valid LocalDate instances.

        LocalDate date = LocalDate.of(2018, 1, 1);
        try {
            int dayOfYear = date.getDayOfYear();
        } catch (DateTimeException ex) {
            throw new AssertionError("Unexpected DateTimeException for valid LocalDate: " + ex);
        }
    }
}
