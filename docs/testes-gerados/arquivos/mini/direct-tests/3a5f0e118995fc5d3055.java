/*
 * @test
 * @summary Test java.time.chrono.HijrahChronology.date(int,int,int) method
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {
    public static void main(String[] args) {
        HijrahChronology chrono = HijrahChronology.INSTANCE;

        // Test with typical valid date
        HijrahDate date1 = chrono.date(1442, 9, 1);
        if (date1 == null) {
            throw new AssertionError("date returned null for valid input");
        }

        // Test with minimum valid year, month, day
        HijrahDate date2 = chrono.date(1, 1, 1);
        if (date2 == null) {
            throw new AssertionError("date returned null for minimum valid input");
        }

        // Test with a large year value
        HijrahDate date3 = chrono.date(1500, 12, 29);
        if (date3 == null) {
            throw new AssertionError("date returned null for large year input");
        }

        // Test with month and day at boundary values
        HijrahDate date4 = chrono.date(1442, 1, 29);
        if (date4 == null) {
            throw new AssertionError("date returned null for boundary month/day");
        }

        // No exception expected for these calls, so if any exception occurs test fails
        System.out.println("All HijrahChronology.date tests passed.");
    }
}
