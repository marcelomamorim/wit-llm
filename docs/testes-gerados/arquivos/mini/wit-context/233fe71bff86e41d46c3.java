/*
 * @test
 * @summary Test IsoChronology.dateEpochDay(long) for valid and out-of-range epochDay values
 * @run main IsoChronologyWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testValidEpochDay();
        testOutOfRangeEpochDay();
    }

    private static void testValidEpochDay() {
        // Test with epochDay 0 (1970-01-01)
        LocalDate date = IsoChronology.INSTANCE.dateEpochDay(0L);
        if (!date.equals(LocalDate.ofEpochDay(0L))) {
            throw new AssertionError("Expected LocalDate.ofEpochDay(0) but got " + date);
        }

        // Test with a positive epochDay
        long epochDay = 18262L; // corresponds to 2020-01-01
        date = IsoChronology.INSTANCE.dateEpochDay(epochDay);
        if (!date.equals(LocalDate.ofEpochDay(epochDay))) {
            throw new AssertionError("Expected LocalDate.ofEpochDay(" + epochDay + ") but got " + date);
        }

        // Test with a negative epochDay
        epochDay = -365L; // corresponds to 1969-01-01
        date = IsoChronology.INSTANCE.dateEpochDay(epochDay);
        if (!date.equals(LocalDate.ofEpochDay(epochDay))) {
            throw new AssertionError("Expected LocalDate.ofEpochDay(" + epochDay + ") but got " + date);
        }
    }

    private static void testOutOfRangeEpochDay() {
        // According to the WIT expath, out-of-range values throw DateTimeException
        // But IsoChronology.dateEpochDay delegates to LocalDate.ofEpochDay which throws DateTimeException
        // We test values outside LocalDate range

        // LocalDate.MIN.toEpochDay() is -365243219162L
        // LocalDate.MAX.toEpochDay() is 365241780471L

        long tooSmall = -365243219163L; // less than LocalDate.MIN
        long tooLarge = 365241780472L;  // greater than LocalDate.MAX

        try {
            IsoChronology.INSTANCE.dateEpochDay(tooSmall);
            throw new AssertionError("Expected DateTimeException for epochDay " + tooSmall);
        } catch (DateTimeException expected) {
            // expected
        }

        try {
            IsoChronology.INSTANCE.dateEpochDay(tooLarge);
            throw new AssertionError("Expected DateTimeException for epochDay " + tooLarge);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
