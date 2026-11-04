/*
 * @test
 * @summary Test java.time.chrono.HijrahChronology.dateEpochDay(long) method
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;

public class HijrahChronologyWitupTest {
    public static void main(String[] args) {
        HijrahChronology chrono = HijrahChronology.INSTANCE;

        // Test with epochDay 0 (Unix epoch day)
        HijrahDate date0 = chrono.dateEpochDay(0L);
        if (date0 == null) {
            throw new AssertionError("dateEpochDay(0) returned null");
        }

        // Test with a positive epochDay
        HijrahDate datePositive = chrono.dateEpochDay(18517L); // corresponds roughly to 2020-09-01 Gregorian
        if (datePositive == null) {
            throw new AssertionError("dateEpochDay(18517) returned null");
        }

        // Test with a negative epochDay
        HijrahDate dateNegative = chrono.dateEpochDay(-1000L);
        if (dateNegative == null) {
            throw new AssertionError("dateEpochDay(-1000) returned null");
        }

        // Test with Long.MAX_VALUE
        HijrahDate dateMax = chrono.dateEpochDay(Long.MAX_VALUE);
        if (dateMax == null) {
            throw new AssertionError("dateEpochDay(Long.MAX_VALUE) returned null");
        }

        // Test with Long.MIN_VALUE
        HijrahDate dateMin = chrono.dateEpochDay(Long.MIN_VALUE);
        if (dateMin == null) {
            throw new AssertionError("dateEpochDay(Long.MIN_VALUE) returned null");
        }

        System.out.println("All HijrahChronology.dateEpochDay tests passed.");
    }
}
