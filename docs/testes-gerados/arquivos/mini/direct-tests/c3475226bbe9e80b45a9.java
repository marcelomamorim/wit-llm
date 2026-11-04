/*
 * @test
 * @summary Test HijrahDate.of(int,int,int) factory method
 * @run main java.time.chrono.HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahChronology;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testValidDateCreation();
        testInvalidMonth();
        testInvalidDay();
        testNegativeYear();
    }

    private static void testValidDateCreation() {
        // Typical valid date
        HijrahDate date = HijrahDate.of(1440, 9, 1);
        if (date == null) {
            throw new AssertionError("HijrahDate.of returned null for valid date");
        }
        // Check that the chronology is HijrahChronology
        if (!HijrahChronology.INSTANCE.equals(date.getChronology())) {
            throw new AssertionError("Chronology is not HijrahChronology");
        }
    }

    private static void testInvalidMonth() {
        try {
            HijrahDate.of(1440, 0, 1); // month 0 invalid
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException expected) {
        }
        try {
            HijrahDate.of(1440, 13, 1); // month 13 invalid
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException expected) {
        }
    }

    private static void testInvalidDay() {
        try {
            HijrahDate.of(1440, 1, 0); // day 0 invalid
            throw new AssertionError("Expected DateTimeException for day=0");
        } catch (DateTimeException expected) {
        }
        try {
            HijrahDate.of(1440, 1, 30); // day 30 invalid for month 1 in Hijrah calendar
            throw new AssertionError("Expected DateTimeException for day=30");
        } catch (DateTimeException expected) {
        }
    }

    private static void testNegativeYear() {
        // Negative or zero prolepticYear is allowed in HijrahDate (historical years)
        HijrahDate date = HijrahDate.of(-1, 1, 1);
        if (date == null) {
            throw new AssertionError("HijrahDate.of returned null for negative year");
        }
    }
}
