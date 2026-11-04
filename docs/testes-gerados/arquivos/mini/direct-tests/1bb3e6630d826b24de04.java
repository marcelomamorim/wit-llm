/*
 * @test
 * @summary Test java.time.chrono.HijrahDate.toEpochDay() method
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testToEpochDayValidDate();
        testToEpochDayInvalidDate();
    }

    private static void testToEpochDayValidDate() {
        // Create a valid HijrahDate and check toEpochDay returns a long value
        HijrahDate date = HijrahDate.of(1440, 9, 1); // 1 Ramadan 1440 AH
        long epochDay = date.toEpochDay();
        // The epochDay should be a valid long value, no exception expected
        if (epochDay == Long.MIN_VALUE || epochDay == Long.MAX_VALUE) {
            throw new AssertionError("toEpochDay returned extreme value: " + epochDay);
        }
    }

    private static void testToEpochDayInvalidDate() {
        // Attempt to create an invalid HijrahDate and expect DateTimeException
        try {
            HijrahDate.of(1440, 13, 1); // Invalid month (13)
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (DateTimeException expected) {
            // Expected exception
        }

        try {
            HijrahDate.of(1440, 9, 30); // Possibly invalid day (Ramadan usually has 29 days)
            // toEpochDay call to check if exception is thrown
            HijrahDate date = HijrahDate.of(1440, 9, 30);
            date.toEpochDay();
        } catch (DateTimeException expected) {
            // Expected exception if day is invalid
        }
    }
}
