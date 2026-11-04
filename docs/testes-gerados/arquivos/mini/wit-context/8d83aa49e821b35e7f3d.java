/*
 * @test
 * @summary Test LocalTime.ofNanoOfDay(long) for valid and invalid inputs
 * @run main java.time.LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.DateTimeException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidNanoOfDay();
        testInvalidNanoOfDay();
        System.out.println("All tests passed.");
    }

    private static void testValidNanoOfDay() {
        // Test start of day
        LocalTime t = LocalTime.ofNanoOfDay(0L);
        if (t.getHour() != 0 || t.getMinute() != 0 || t.getSecond() != 0 || t.getNano() != 0) {
            throw new AssertionError("Failed at start of day");
        }

        // Test end of day - 1 nanosecond
        long maxNano = 24L * 60 * 60 * 1000000000L - 1;
        t = LocalTime.ofNanoOfDay(maxNano);
        if (t.getHour() != 23 || t.getMinute() != 59 || t.getSecond() != 59 || t.getNano() != 999999999) {
            throw new AssertionError("Failed at end of day");
        }

        // Test a random valid time
        long nanoOfDay = 10L * 60 * 60 * 1000000000L + 15L * 60 * 1000000000L + 30L * 1000000000L + 123456789L;
        t = LocalTime.ofNanoOfDay(nanoOfDay);
        if (t.getHour() != 10 || t.getMinute() != 15 || t.getSecond() != 30 || t.getNano() != 123456789) {
            throw new AssertionError("Failed at random valid time");
        }
    }

    private static void testInvalidNanoOfDay() {
        // Test negative value
        try {
            LocalTime.ofNanoOfDay(-1L);
            throw new AssertionError("Expected DateTimeException for negative nanoOfDay");
        } catch (DateTimeException expected) {
        }

        // Test value equal to 24 hours in nanos (out of range)
        long invalidNano = 24L * 60 * 60 * 1000000000L;
        try {
            LocalTime.ofNanoOfDay(invalidNano);
            throw new AssertionError("Expected DateTimeException for nanoOfDay equal to 24h");
        } catch (DateTimeException expected) {
        }

        // Test value greater than 24 hours in nanos
        try {
            LocalTime.ofNanoOfDay(invalidNano + 1);
            throw new AssertionError("Expected DateTimeException for nanoOfDay greater than 24h");
        } catch (DateTimeException expected) {
        }
    }
}
