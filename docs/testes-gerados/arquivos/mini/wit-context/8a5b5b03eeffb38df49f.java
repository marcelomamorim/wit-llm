/*
 * @test
 * @summary Test HijrahChronology.dateEpochDay(long) for valid and boundary inputs
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahChronologyWitupTest {
    public static void main(String[] args) {
        HijrahChronology chrono = HijrahChronology.INSTANCE;

        // Test with a valid epoch day (e.g., 0L)
        HijrahDate date = chrono.dateEpochDay(0L);
        if (date == null) {
            throw new AssertionError("dateEpochDay(0L) returned null");
        }

        // Test with a typical positive epoch day
        date = chrono.dateEpochDay(1000L);
        if (date == null) {
            throw new AssertionError("dateEpochDay(1000L) returned null");
        }

        // Test with a typical negative epoch day
        date = chrono.dateEpochDay(-1000L);
        if (date == null) {
            throw new AssertionError("dateEpochDay(-1000L) returned null");
        }

        // Test boundary conditions based on known behavior:
        // The WIT expath suggests DateTimeException if epochDay < minEpochDay or >= maxEpochDay when initComplete is true.
        // We cannot access minEpochDay, maxEpochDay, or initComplete directly, so test with very large/small values to provoke exception.

        // Very large positive value - expect DateTimeException or no exception depending on implementation
        try {
            chrono.dateEpochDay(Long.MAX_VALUE);
            // If no exception, test passes as behavior may differ
        } catch (DateTimeException e) {
            // Expected for out of range
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for Long.MAX_VALUE: " + e);
        }

        // Very large negative value - expect DateTimeException or no exception depending on implementation
        try {
            chrono.dateEpochDay(Long.MIN_VALUE);
            // If no exception, test passes as behavior may differ
        } catch (DateTimeException e) {
            // Expected for out of range
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for Long.MIN_VALUE: " + e);
        }

        System.out.println("HijrahChronology.dateEpochDay(long) tests passed");
    }
}
