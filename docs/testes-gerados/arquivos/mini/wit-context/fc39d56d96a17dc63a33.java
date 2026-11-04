/*
 * @test
 * @summary Test java.time.LocalDate.withDayOfYear(int) method for valid and invalid inputs
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithDayOfYearValid();
        testWithDayOfYearInvalid();
    }

    private static void testWithDayOfYearValid() {
        LocalDate date = LocalDate.of(2018, 1, 1);
        // Same dayOfYear returns same instance
        LocalDate result = date.withDayOfYear(1);
        if (result != date) {
            throw new AssertionError("Expected same instance when dayOfYear is unchanged");
        }

        // Change dayOfYear to a valid value
        LocalDate changed = date.withDayOfYear(32); // Feb 1, 2018
        if (changed.getDayOfYear() != 32) {
            throw new AssertionError("Expected dayOfYear 32 but got " + changed.getDayOfYear());
        }
        if (changed.getYear() != 2018) {
            throw new AssertionError("Year should remain 2018");
        }
    }

    private static void testWithDayOfYearInvalid() {
        LocalDate date = LocalDate.of(2018, 1, 1);
        // dayOfYear 0 is invalid, expect DateTimeException
        try {
            date.withDayOfYear(0);
            throw new AssertionError("Expected DateTimeException for dayOfYear=0");
        } catch (DateTimeException expected) {
        }

        // dayOfYear greater than max days in year (365 for 2018) invalid
        try {
            date.withDayOfYear(366);
            throw new AssertionError("Expected DateTimeException for dayOfYear=366 in non-leap year");
        } catch (DateTimeException expected) {
        }

        // For leap year, dayOfYear 366 is valid
        LocalDate leapDate = LocalDate.of(2020, 1, 1);
        LocalDate validLeap = leapDate.withDayOfYear(366);
        if (validLeap.getDayOfYear() != 366) {
            throw new AssertionError("Expected dayOfYear 366 for leap year");
        }

        // dayOfYear negative invalid
        try {
            date.withDayOfYear(-1);
            throw new AssertionError("Expected DateTimeException for negative dayOfYear");
        } catch (DateTimeException expected) {
        }
    }
}
