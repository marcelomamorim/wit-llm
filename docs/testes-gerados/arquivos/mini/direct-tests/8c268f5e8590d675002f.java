/*
 * @test
 * @summary Test java.time.LocalTime.of(int hour, int minute) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidHourMinuteZeroMinute();
        testValidHourMinuteNonZeroMinute();
        testHourOutOfRangeLow();
        testHourOutOfRangeHigh();
        testMinuteOutOfRangeLow();
        testMinuteOutOfRangeHigh();
    }

    private static void testValidHourMinuteZeroMinute() {
        for (int h = 0; h <= 23; h++) {
            LocalTime t = LocalTime.of(h, 0);
            if (t.getHour() != h || t.getMinute() != 0 || t.getSecond() != 0 || t.getNano() != 0) {
                throw new AssertionError("Failed for hour=" + h + ": " + t);
            }
        }
    }

    private static void testValidHourMinuteNonZeroMinute() {
        int[] testMinutes = {1, 15, 30, 59};
        for (int h = 0; h <= 23; h++) {
            for (int m : testMinutes) {
                LocalTime t = LocalTime.of(h, m);
                if (t.getHour() != h || t.getMinute() != m || t.getSecond() != 0 || t.getNano() != 0) {
                    throw new AssertionError("Failed for hour=" + h + ", minute=" + m + ": " + t);
                }
            }
        }
    }

    private static void testHourOutOfRangeLow() {
        try {
            LocalTime.of(-1, 0);
            throw new AssertionError("Expected exception for hour=-1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testHourOutOfRangeHigh() {
        try {
            LocalTime.of(24, 0);
            throw new AssertionError("Expected exception for hour=24");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testMinuteOutOfRangeLow() {
        try {
            LocalTime.of(0, -1);
            throw new AssertionError("Expected exception for minute=-1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testMinuteOutOfRangeHigh() {
        try {
            LocalTime.of(0, 60);
            throw new AssertionError("Expected exception for minute=60");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
