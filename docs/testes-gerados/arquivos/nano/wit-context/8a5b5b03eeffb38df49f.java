/*
 * @test
 * @summary Test for HijrahChronology.dateEpochDay(long)
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        testValidEpochDay();
        testEpochDayBelowMinimum();
        testEpochDayAboveMaximum();
    }

    static void testValidEpochDay() {
        long validEpochDay = 0L; // typical valid value
        try {
            HijrahDate date = HijrahChronology.INSTANCE.dateEpochDay(validEpochDay);
            if (date == null) {
                throw new AssertionError("Expected non-null HijrahDate for valid epochDay");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid epochDay", e);
        }
    }

    static void testEpochDayBelowMinimum() {
        long minEpochDay = Long.MIN_VALUE;
        try {
            HijrahChronology.INSTANCE.dateEpochDay(minEpochDay);
            // No exception expected, as method should handle Long.MIN_VALUE
        } catch (DateTimeException e) {
            // Expected if the implementation throws for invalid date
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for min epochDay", e);
        }
    }

    static void testEpochDayAboveMaximum() {
        long maxEpochDay = Long.MAX_VALUE;
        try {
            HijrahChronology.INSTANCE.dateEpochDay(maxEpochDay);
            // No exception expected, as method should handle Long.MAX_VALUE
        } catch (DateTimeException e) {
            // Expected if the implementation throws for invalid date
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for max epochDay", e);
        }
    }
}
