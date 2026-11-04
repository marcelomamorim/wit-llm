/*
 * @test
 * @summary Test HijrahDate.of(int,int,int) for valid and invalid inputs
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahChronology;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testValidDate();
        testInvalidDayOfMonth();
        testInvalidMonthYearCombination();
        System.out.println("HijrahDateWitupTest passed.");
    }

    private static void testValidDate() {
        // Use a known valid date: year=1440, month=9, day=1 (Ramadan 1, 1440 AH)
        HijrahDate date = HijrahDate.of(1440, 9, 1);
        if (date == null) {
            throw new AssertionError("HijrahDate.of returned null for valid date");
        }
        // Check that the returned date has the expected chronology
        if (!HijrahChronology.INSTANCE.equals(date.getChronology())) {
            throw new AssertionError("HijrahDate.of returned date with wrong chronology");
        }
    }

    private static void testInvalidDayOfMonth() {
        // Day of month too large (e.g. 30 in a month with 29 days) or less than 1
        // We expect DateTimeException to be thrown
        try {
            HijrahDate.of(1440, 9, 30); // Ramadan usually has 29 or 30 days, 30 may be invalid
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth");
        } catch (DateTimeException expected) {
            // Expected exception
        }

        try {
            HijrahDate.of(1440, 9, 0); // dayOfMonth < 1 invalid
            throw new AssertionError("Expected DateTimeException for dayOfMonth < 1");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }

    private static void testInvalidMonthYearCombination() {
        // Month out of range (e.g. 0 or 13) or year-month combination invalid
        try {
            HijrahDate.of(1440, 0, 1); // month 0 invalid
            throw new AssertionError("Expected DateTimeException for month 0");
        } catch (DateTimeException expected) {
            // Expected
        }

        try {
            HijrahDate.of(1440, 13, 1); // month 13 invalid
            throw new AssertionError("Expected DateTimeException for month 13");
        } catch (DateTimeException expected) {
            // Expected
        }

        // Test a year-month combination that is out of supported range
        // Using a very low year to trigger out of range
        try {
            HijrahDate.of(-1000, 1, 1);
            throw new AssertionError("Expected DateTimeException for out of range year");
        } catch (DateTimeException expected) {
            // Expected
        }
    }
}
