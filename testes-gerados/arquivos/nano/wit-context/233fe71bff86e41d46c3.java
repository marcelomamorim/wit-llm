/*
 * @test
 * @summary Test for IsoChronology.dateEpochDay(long)
 * @run main IsoChronologyWitupTest
 */

import java.time.chrono.IsoChronology;
import java.time.LocalDate;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testValidEpochDay();
        testEpochDayBelowMinimum();
        testEpochDayAboveMaximum();
    }

    static void testValidEpochDay() {
        long validEpochDay = 0L; // Epoch day zero
        LocalDate date = IsoChronology.INSTANCE.dateEpochDay(validEpochDay);
        if (date == null) {
            throw new AssertionError("Expected non-null LocalDate for valid epoch day");
        }
        // Additional check: date should be equal to LocalDate.ofEpochDay(0)
        LocalDate expectedDate = LocalDate.ofEpochDay(0L);
        if (!date.equals(expectedDate)) {
            throw new AssertionError("Mismatch for epoch day 0: got " + date + ", expected " + expectedDate);
        }
    }

    static void testEpochDayBelowMinimum() {
        long minEpochDay = Long.MIN_VALUE;
        try {
            LocalDate date = IsoChronology.INSTANCE.dateEpochDay(minEpochDay);
            if (date == null) {
                throw new AssertionError("Expected exception for min epoch day");
            }
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Expected DateTimeException for min epoch day", e);
            }
        }
    }

    static void testEpochDayAboveMaximum() {
        long maxEpochDay = Long.MAX_VALUE;
        try {
            LocalDate date = IsoChronology.INSTANCE.dateEpochDay(maxEpochDay);
            if (date == null) {
                throw new AssertionError("Expected exception for max epoch day");
            }
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Expected DateTimeException for max epoch day", e);
            }
        }
    }
}
