/*
 * @test
 * @summary Test for LocalDate.getDayOfYear() method, including exception scenarios.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testGetDayOfYearValid();
        testGetDayOfYearInvalidMonth();
    }

    static void testGetDayOfYearValid() {
        LocalDate date = LocalDate.of(2021, Month.MARCH, 15);
        int dayOfYear = date.getDayOfYear();
        if (dayOfYear != 74) {
            throw new AssertionError("Expected day of year 74, but got " + dayOfYear);
        }
    }

    static void testGetDayOfYearInvalidMonth() {
        try {
            // Simulate invalid month by creating a LocalDate with invalid month
            // Since LocalDate.of() throws DateTimeException for invalid month, we test that
            LocalDate.of(2021, 13, 10);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
