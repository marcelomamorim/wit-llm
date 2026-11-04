/*
 * @test
 * @summary Test LocalTime.of(int hour, int minute, int second) for valid and invalid inputs
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidTimes();
        testInvalidHourLow();
        testInvalidHourHigh();
        testInvalidMinuteLow();
        testInvalidMinuteHigh();
        testInvalidSecondLow();
        testInvalidSecondHigh();
    }

    private static void testValidTimes() {
        // Test boundary valid values
        LocalTime t1 = LocalTime.of(0, 0, 0);
        if (t1.getHour() != 0 || t1.getMinute() != 0 || t1.getSecond() != 0) {
            throw new AssertionError("Failed at LocalTime.of(0,0,0)");
        }
        LocalTime t2 = LocalTime.of(23, 59, 59);
        if (t2.getHour() != 23 || t2.getMinute() != 59 || t2.getSecond() != 59) {
            throw new AssertionError("Failed at LocalTime.of(23,59,59)");
        }
        // Test zero minute and second optimization path
        LocalTime t3 = LocalTime.of(5, 0, 0);
        if (t3.getHour() != 5 || t3.getMinute() != 0 || t3.getSecond() != 0) {
            throw new AssertionError("Failed at LocalTime.of(5,0,0)");
        }
    }

    private static void testInvalidHourLow() {
        try {
            LocalTime.of(-1, 0, 0);
            throw new AssertionError("Expected DateTimeException for hour -1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidHourHigh() {
        try {
            LocalTime.of(24, 0, 0);
            throw new AssertionError("Expected DateTimeException for hour 24");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMinuteLow() {
        try {
            LocalTime.of(0, -1, 0);
            throw new AssertionError("Expected DateTimeException for minute -1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMinuteHigh() {
        try {
            LocalTime.of(0, 60, 0);
            throw new AssertionError("Expected DateTimeException for minute 60");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidSecondLow() {
        try {
            LocalTime.of(0, 0, -1);
            throw new AssertionError("Expected DateTimeException for second -1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidSecondHigh() {
        try {
            LocalTime.of(0, 0, 60);
            throw new AssertionError("Expected DateTimeException for second 60");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
