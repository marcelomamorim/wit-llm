/*
 * @test
 * @summary Deterministic tests for java.time.chrono.HijrahDate.toEpochDay()
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testValidToEpochDay();
        testToEpochDayInvalidMonthTooHigh();
        testToEpochDayInvalidMonthTooLow();
        testToEpochDayInvalidDayTooHigh();
        testToEpochDayInvalidDayTooLow();
    }

    private static void testValidToEpochDay() {
        // Use a valid date to check normal behavior
        HijrahDate date = HijrahDate.of(1440, 9, 1); // 1 Ramadan 1440 AH
        long epochDay = date.toEpochDay();
        if (epochDay == 0L) {
            throw new AssertionError("toEpochDay() returned 0 for a valid date, unexpected");
        }
    }

    private static void testToEpochDayInvalidMonthTooHigh() {
        // Month too high: monthOfYear > 12 (Hijrah calendar months max 12)
        // According to WIT, this should throw DateTimeException if conditions met
        try {
            HijrahDate date = HijrahDate.of(1440, 13, 1);
            date.toEpochDay();
            throw new AssertionError("Expected DateTimeException for month too high");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testToEpochDayInvalidMonthTooLow() {
        // Month too low: monthOfYear < 1
        try {
            HijrahDate date = HijrahDate.of(1440, 0, 1);
            date.toEpochDay();
            throw new AssertionError("Expected DateTimeException for month too low");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testToEpochDayInvalidDayTooHigh() {
        // Day too high: dayOfMonth > length of month
        // Use month 9 (Ramadan) which normally has 30 days
        try {
            HijrahDate date = HijrahDate.of(1440, 9, 31);
            date.toEpochDay();
            throw new AssertionError("Expected DateTimeException for day too high");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testToEpochDayInvalidDayTooLow() {
        // Day too low: dayOfMonth < 1
        try {
            HijrahDate date = HijrahDate.of(1440, 9, 0);
            date.toEpochDay();
            throw new AssertionError("Expected DateTimeException for day too low");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
