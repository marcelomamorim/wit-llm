/*
 * @test
 * @summary Test java.time.chrono.IsoChronology.dateEpochDay(long) method
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        IsoChronology iso = IsoChronology.INSTANCE;

        // Test with epochDay 0 (1970-01-01)
        LocalDate date = iso.dateEpochDay(0L);
        if (!date.equals(LocalDate.of(1970, 1, 1))) {
            throw new AssertionError("Expected 1970-01-01 for epochDay 0, got " + date);
        }

        // Test with a positive epochDay (e.g. 18262 = 2020-01-01)
        date = iso.dateEpochDay(18262L);
        if (!date.equals(LocalDate.of(2020, 1, 1))) {
            throw new AssertionError("Expected 2020-01-01 for epochDay 18262, got " + date);
        }

        // Test with a negative epochDay (e.g. -1 = 1969-12-31)
        date = iso.dateEpochDay(-1L);
        if (!date.equals(LocalDate.of(1969, 12, 31))) {
            throw new AssertionError("Expected 1969-12-31 for epochDay -1, got " + date);
        }

        // Test with a large positive epochDay (e.g. 3652058 = 9999-12-31)
        date = iso.dateEpochDay(3652058L);
        if (!date.equals(LocalDate.of(9999, 12, 31))) {
            throw new AssertionError("Expected 9999-12-31 for epochDay 3652058, got " + date);
        }

        // Test with a large negative epochDay (e.g. -719528 = year -9999-01-01)
        date = iso.dateEpochDay(-719528L);
        if (!date.equals(LocalDate.of(-9999, 1, 1))) {
            throw new AssertionError("Expected -9999-01-01 for epochDay -719528, got " + date);
        }

        System.out.println("All tests passed.");
    }
}
