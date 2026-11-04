/*
 * @test
 * @summary Test HijrahChronology.date(int,int,int) for valid and invalid inputs
 * @run main HijrahChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {
    public static void main(String[] args) {
        HijrahChronology chrono = HijrahChronology.INSTANCE;

        // Test valid date creation
        HijrahDate date = chrono.date(1442, 9, 1); // 9th month, 1st day
        if (date == null) {
            throw new AssertionError("HijrahDate.date returned null for valid input");
        }

        // Test invalid month (too low)
        try {
            chrono.date(1442, 0, 1);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException expected) {
        }

        // Test invalid month (too high)
        try {
            chrono.date(1442, 13, 1);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException expected) {
        }

        // Test invalid day (too low)
        try {
            chrono.date(1442, 9, 0);
            throw new AssertionError("Expected DateTimeException for day=0");
        } catch (DateTimeException expected) {
        }

        // Test invalid day (too high)
        try {
            // Use a day greater than the length of the month
            int invalidDay = chrono.getMonthLength(1442, 9) + 1;
            chrono.date(1442, 9, invalidDay);
            throw new AssertionError("Expected DateTimeException for day > month length");
        } catch (DateTimeException expected) {
        }
    }
}
