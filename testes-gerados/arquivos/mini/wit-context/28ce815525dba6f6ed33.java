/*
 * @test
 * @summary Test IsoChronology.dateYearDay(int, int) for valid and invalid inputs
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.DateTimeException;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testValidDateYearDay();
        testInvalidYearTooLow();
        testInvalidYearTooHigh();
        testInvalidDayOfYearTooLow();
        testInvalidDayOfYearTooHigh();
    }

    private static void testValidDateYearDay() {
        // Typical valid date: 2019, day 100
        LocalDate date = IsoChronology.INSTANCE.dateYearDay(2019, 100);
        if (date.getYear() != 2019 || date.getDayOfYear() != 100) {
            throw new AssertionError("Valid dateYearDay failed: " + date);
        }

        // Leap year day 366
        LocalDate leapDate = IsoChronology.INSTANCE.dateYearDay(2020, 366);
        if (leapDate.getYear() != 2020 || leapDate.getDayOfYear() != 366) {
            throw new AssertionError("Leap year dateYearDay failed: " + leapDate);
        }
    }

    private static void testInvalidYearTooLow() {
        // Year less than minimum supported by LocalDate (year < -999999999)
        // LocalDate supports from -999999999 to 999999999
        // Use year = -1000000000 to trigger exception
        try {
            IsoChronology.INSTANCE.dateYearDay(-1000000000, 1);
            throw new AssertionError("Expected DateTimeException for year too low");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidYearTooHigh() {
        // Year greater than maximum supported by LocalDate (year > 999999999)
        try {
            IsoChronology.INSTANCE.dateYearDay(1000000000, 1);
            throw new AssertionError("Expected DateTimeException for year too high");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidDayOfYearTooLow() {
        // Day of year less than 1
        try {
            IsoChronology.INSTANCE.dateYearDay(2019, 0);
            throw new AssertionError("Expected DateTimeException for dayOfYear too low");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidDayOfYearTooHigh() {
        // Day of year greater than max days in year (365 for 2019)
        try {
            IsoChronology.INSTANCE.dateYearDay(2019, 366);
            throw new AssertionError("Expected DateTimeException for dayOfYear too high in non-leap year");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
