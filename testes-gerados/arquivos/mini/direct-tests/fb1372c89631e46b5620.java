/*
 * @test
 * @summary Test java.time.LocalTime.of(int hour, int minute, int second) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ValueRange;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidHourMinuteSecond();
        testZeroMinuteAndSecondReturnsCachedInstance();
        testInvalidHourThrowsException();
        testInvalidMinuteThrowsException();
        testInvalidSecondThrowsException();
    }

    private static void testValidHourMinuteSecond() {
        LocalTime t = LocalTime.of(10, 20, 30);
        if (t.getHour() != 10) throw new AssertionError("Hour mismatch");
        if (t.getMinute() != 20) throw new AssertionError("Minute mismatch");
        if (t.getSecond() != 30) throw new AssertionError("Second mismatch");
        if (t.getNano() != 0) throw new AssertionError("Nano should be zero");
    }

    private static void testZeroMinuteAndSecondReturnsCachedInstance() {
        LocalTime t1 = LocalTime.of(0, 0, 0);
        LocalTime t2 = LocalTime.of(0, 0, 0);
        if (t1 != t2) throw new AssertionError("Expected cached instance for midnight");

        LocalTime t3 = LocalTime.of(23, 0, 0);
        LocalTime t4 = LocalTime.of(23, 0, 0);
        if (t3 != t4) throw new AssertionError("Expected cached instance for 23:00:00");
    }

    private static void testInvalidHourThrowsException() {
        try {
            LocalTime.of(-1, 0, 0);
            throw new AssertionError("Expected exception for invalid hour -1");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalTime.of(24, 0, 0);
            throw new AssertionError("Expected exception for invalid hour 24");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMinuteThrowsException() {
        try {
            LocalTime.of(0, -1, 0);
            throw new AssertionError("Expected exception for invalid minute -1");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalTime.of(0, 60, 0);
            throw new AssertionError("Expected exception for invalid minute 60");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidSecondThrowsException() {
        try {
            LocalTime.of(0, 0, -1);
            throw new AssertionError("Expected exception for invalid second -1");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalTime.of(0, 0, 60);
            throw new AssertionError("Expected exception for invalid second 60");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
