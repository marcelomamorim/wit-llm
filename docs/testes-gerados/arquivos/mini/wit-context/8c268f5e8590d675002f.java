/*
 * @test
 * @summary Test java.time.LocalTime.of(int hour, int minute) for valid and invalid inputs
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidHourMinute();
        testInvalidHourBelowRange();
        testInvalidHourAboveRange();
        testInvalidMinuteBelowRange();
        testInvalidMinuteAboveRange();
    }

    private static void testValidHourMinute() {
        // Valid inputs should create LocalTime without exception
        LocalTime t1 = LocalTime.of(0, 0);
        if (t1.getHour() != 0 || t1.getMinute() != 0) {
            throw new AssertionError("LocalTime.of(0,0) returned incorrect time");
        }
        LocalTime t2 = LocalTime.of(23, 59);
        if (t2.getHour() != 23 || t2.getMinute() != 59) {
            throw new AssertionError("LocalTime.of(23,59) returned incorrect time");
        }
        LocalTime t3 = LocalTime.of(12, 30);
        if (t3.getHour() != 12 || t3.getMinute() != 30) {
            throw new AssertionError("LocalTime.of(12,30) returned incorrect time");
        }
    }

    private static void testInvalidHourBelowRange() {
        try {
            LocalTime.of(-1, 0);
            throw new AssertionError("Expected DateTimeException for hour -1");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidHourAboveRange() {
        try {
            LocalTime.of(24, 0);
            throw new AssertionError("Expected DateTimeException for hour 24");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidMinuteBelowRange() {
        try {
            LocalTime.of(0, -1);
            throw new AssertionError("Expected DateTimeException for minute -1");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testInvalidMinuteAboveRange() {
        try {
            LocalTime.of(0, 60);
            throw new AssertionError("Expected DateTimeException for minute 60");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
